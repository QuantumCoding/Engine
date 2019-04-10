package com.Engine.RenderEngine.Shaders.Uniforms.Types;

import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL30.glBindBufferBase;
import static org.lwjgl.opengl.GL31.GL_INVALID_INDEX;
import static org.lwjgl.opengl.GL31.GL_MAX_UNIFORM_BUFFER_BINDINGS;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BLOCK_DATA_SIZE;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_OFFSET;
import static org.lwjgl.opengl.GL31.glGetActiveUniformBlocki;
import static org.lwjgl.opengl.GL31.glGetActiveUniforms;
import static org.lwjgl.opengl.GL31.glGetUniformBlockIndex;
import static org.lwjgl.opengl.GL31.glGetUniformIndices;
import static org.lwjgl.opengl.GL31.glUniformBlockBinding;

import java.lang.annotation.AnnotationFormatError;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.BufferUtils;

import com.Engine.RenderEngine.Shaders.RawShader;
import com.Engine.RenderEngine.Shaders.UniformType;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformLinker;
import com.Engine.Util.Log;

public abstract class UniformBuffer extends UniformType<Void> {
	private static final HashMap<Class<?>, UniformBuffer> UNIFORM_BUFFERS = new HashMap<>();

	public static UniformBuffer createBuffer(Class<?> type, RawShader shader, String name) {
		UniformBuffer buffer = UNIFORM_BUFFERS.get(type);
		
		if(buffer == null) {
			try {
				buffer = (UniformBuffer) type.getConstructor().newInstance();
				UNIFORM_BUFFERS.put(type, buffer);
				
			} catch(ReflectiveOperationException e) {
				Log.sneekyThrow(e);
				return null;
			}
		}
		
		buffer.addShaderLink(shader, name);
		return buffer;
	}
	
	private static class Link {
		String uniformName;
		UniformType<?> uniform;
		
		int index;
		int offset;

		public Link(String uniformName, UniformType<?> uniform) {
			this.uniformName = uniformName;
			this.uniform = uniform;
		}
	}
	
	private static class ShaderLink {
		String name;
		int location;
		
		@SuppressWarnings("unused") // TODO: Maybe needed in Dynamic Shenanigans_; if not, Delete
		RawShader shader;

		public ShaderLink(String name, RawShader shader) {
			this.name = name;
			this.shader = shader;
		}
	}
	
//	-------------------------------- --------------------- --------------------------------
//	-------------------------------- Instance Linking Code --------------------------------
//	-------------------------------- --------------------- --------------------------------
	
	private static final int MAX_AVAILABLE_BINDING_POINTS = glGetInteger(GL_MAX_UNIFORM_BUFFER_BINDINGS);
	private static int nextAvailableBindPoint = 0;
	private int blockBindingPoint;
	
	private int bufferID;
	private ByteBuffer buffer;
	private boolean hasChanged;
	
	private HashMap<RawShader, ShaderLink> nameMap;
	
	public UniformBuffer() {
		super(null);
		nameMap = new HashMap<>();
		
		bufferID = glGenBuffers();
		glBindBufferBase(GL_UNIFORM_BUFFER, blockBindingPoint, bufferID);
		
		blockBindingPoint = nextAvailableBindPoint ++;
		if(blockBindingPoint >= MAX_AVAILABLE_BINDING_POINTS) // TODO: Create Resolution / Dynamic Re-binding System
			throw new IllegalStateException("Out of Buffer Binding Points! Cannot create any more Uniform Buffers");
	}
	
	private void addShaderLink(RawShader shader, String name) { nameMap.put(shader, new ShaderLink(name, shader)); }
	
	public void attach(RawShader shader) { 
		// Attach to THIS Shader
		ShaderLink sLink = nameMap.get(shader);
		sLink.location = glGetUniformBlockIndex(shader.getProgramId(), sLink.name); 	 
		
		if(sLink.location == -1) { 
			System.err.println("No uniform named \"" + sLink.name + "\" exits for " + shader.getClass().getSimpleName()
				+ "; Shader may not work as intended");
			
			return;
		}
		
		glUniformBlockBinding(shader.getProgramId(), sLink.location, blockBindingPoint); 
		
		if(buffer == null) {
			// General Block Setup Stuffs
			ArrayList<Link> uniformLinks = new ArrayList<>(); collectNames(this, "", uniformLinks); 	 // <-- Instantiates  
			String[] names = uniformLinks.stream().map(link -> link.uniformName).toArray(String[]::new); //  Uniform Objects
			IntBuffer indexs = BufferUtils.createIntBuffer(names.length);
	
			glGetUniformIndices(shader.getProgramId(), names, indexs); 		// Gets Uniform Indices from first Attached Shader
			
			IntBuffer offsets = BufferUtils.createIntBuffer(names.length);  // Gets Uniform Offsets from first Attached Shader
			glGetActiveUniforms(shader.getProgramId(), indexs, GL_UNIFORM_OFFSET, offsets); 
			
			for(Link link : uniformLinks) {
				link.index = indexs.get();
				link.offset = offsets.get();
				
				if(link.index == GL_INVALID_INDEX) {
					System.err.println("No uniform named \"" + link.uniformName + "\" found in Uniform Buffer "
						+ "\"" + sLink.name + "\"" + " in shader " + shader.getClass().getSimpleName() + "; "
						+ "Shader may not work as intended");
					
					continue;
				}
				
				assignUniformBuffer(link.uniform, this, link.offset);
			}
			
			// Standard Buffer Allocation; See VBO's
			int size = glGetActiveUniformBlocki(shader.getProgramId(), sLink.location, GL_UNIFORM_BLOCK_DATA_SIZE);
			buffer = BufferUtils.createByteBuffer(size);
			glBufferData(GL_UNIFORM_BUFFER, size, GL_STATIC_DRAW);
		}
		
		postAttach(shader);
	} 
	
	public void load() { load(null); }
	
	public void load(Void e) {
		if(!hasChanged) return;
		buffer.flip(); buffer.limit(buffer.capacity());
		
		bind();
			glBufferData(GL_UNIFORM_BUFFER, buffer.capacity() * 4, GL_STATIC_DRAW);
			glBufferSubData(GL_UNIFORM_BUFFER, 0, buffer);
		unbind();
		
		hasChanged = false;
	}
	
	private boolean collectNames(Object source, String sourceName, ArrayList<Link> names) {
		boolean hasChildren = false;
		
		for(Field field : source.getClass().getDeclaredFields()) {
			if(!field.isAnnotationPresent(Uniform.class)) continue;
			
			Class<?> type = field.getType();
			boolean isArray = type.isArray();
			if(isArray) type = type.getComponentType();

			if(!UniformType.class.isAssignableFrom(type) || type.equals(UniformType.class)) 
				throw new AnnotationFormatError(Uniform.ERROR_MESSAGE + "; not of type " + type.getSimpleName());

			Uniform annotation = field.getAnnotation(Uniform.class);
			
			if(isArray) {
				Object array = Array.newInstance(type, annotation.size());
				UniformLinker.assignField(field, source, array);
				
				for(int i = 0; i < annotation.size(); i ++) {
					UniformType<?> uniform = UniformLinker.createUniform(sourceName, annotation, source, type);
					Array.set(array, i, uniform);
					
					String childName = sourceName + field.getName() + "[" + i + "]";
					boolean isParentType = collectNames(uniform, childName + ".", names);
					if(!isParentType) names.add(new Link(childName, uniform));
				}
			} else {
				UniformType<?> uniform = UniformLinker.createUniform(sourceName, annotation, source, type);
				UniformLinker.assignField(field, source, uniform);
				
				String childName = sourceName + field.getName();
				boolean isParentType = collectNames(uniform, childName + ".", names);
				if(!isParentType) names.add(new Link(childName, uniform));
			}
			
			hasChildren = true;
		}
		
		return hasChildren;
	}
	
	private static final Method UNIFORM_ASSIGN_BUFFER; static {
		Method method = null; try {
			method = UniformType.class.getDeclaredMethod("assignBuffer", UniformBuffer.class, int.class);
			method.setAccessible(true);
		} catch(ReflectiveOperationException e) { e.printStackTrace(); }
		
		UNIFORM_ASSIGN_BUFFER = method;
	}
	
	private static void assignUniformBuffer(UniformType<?> uniform, UniformBuffer buffer, int offset) {
		try { UNIFORM_ASSIGN_BUFFER.invoke(uniform, buffer, offset); }
		catch(ReflectiveOperationException | IllegalArgumentException e) { Log.sneekyThrow(e); }
	}

	protected void loadFloat(int offset, float x) 							 { put(offset + 0, x); }
	protected void loadFloat(int offset, float x, float y) 					 { put(offset + 0, x); put(offset + 4, y); }
	protected void loadFloat(int offset, float x, float y, float z) 		 { put(offset + 0, x); put(offset + 4, y); put(offset + 8, z); }
	protected void loadFloat(int offset, float x, float y, float z, float w) { put(offset + 0, x); put(offset + 4, y); put(offset + 8, z); put(offset + 12, w); }

	protected void loadDouble(int offset, double x) 							  { put(offset + 0, x); }                                                             
	protected void loadDouble(int offset, double x, double y) 					  { put(offset + 0, x); put(offset + 4, y); }                                         
	protected void loadDouble(int offset, double x, double y, double z) 		  { put(offset + 0, x); put(offset + 4, y); put(offset + 8, z); }                     
	protected void loadDouble(int offset, double x, double y, double z, double w) { put(offset + 0, x); put(offset + 4, y); put(offset + 8, z); put(offset + 12, w); }
	
	protected void loadUnsignedInt(int offset, long x) 							{ put(offset + 0, x); }                                                                
	protected void loadUnsignedInt(int offset, long x, long y) 					{ put(offset + 0, x); put(offset + 4, y); }                                           
	protected void loadUnsignedInt(int offset, long x, long y, long z) 			{ put(offset + 0, x); put(offset + 4, y); put(offset + 8, z); }                      
	protected void loadUnsignedInt(int offset, long x, long y, long z, long w)  { put(offset + 0, x); put(offset + 4, y); put(offset + 8, z); put(offset + 12, w); }
	
	protected void loadInt(int offset, int x) 						{ put(offset + 0, x); }                                                                  
	protected void loadInt(int offset, int x, int y) 				{ put(offset + 0, x); put(offset + 4, y); }                                            
	protected void loadInt(int offset, int x, int y, int z)			{ put(offset + 0, x); put(offset + 4, y); put(offset + 8, z); }                      
	protected void loadInt(int offset, int x, int y, int z, int w) 	{ put(offset + 0, x); put(offset + 4, y); put(offset + 8, z); put(offset + 12, w); }
	
	protected void loadMatrix2(int offset, FloatBuffer buffer) { for(int i = 0, r = buffer.remaining(); i < r; i ++) put(offset + i, buffer.get()); }                  
	protected void loadMatrix3(int offset, FloatBuffer buffer) { for(int i = 0, r = buffer.remaining(); i < r; i ++) put(offset + i, buffer.get()); }         
	protected void loadMatrix4(int offset, FloatBuffer buffer) { for(int i = 0, r = buffer.remaining(); i < r; i ++) put(offset + i, buffer.get()); }
	
	protected void loadMatrix2(int offset, DoubleBuffer buffer) { for(int i = 0, r = buffer.remaining(); i < r; i ++) put(offset + i, buffer.get()); }                   
	protected void loadMatrix3(int offset, DoubleBuffer buffer) { for(int i = 0, r = buffer.remaining(); i < r; i ++) put(offset + i, buffer.get()); }          
	protected void loadMatrix4(int offset, DoubleBuffer buffer) { for(int i = 0, r = buffer.remaining(); i < r; i ++) put(offset + i, buffer.get()); } 
	
	private void put(int index, float f) 	{ hasChanged = true; buffer.putFloat(index, f); }
	private void put(int index, double d) 	{ hasChanged = true; buffer.putDouble(index, d); }
	private void put(int index, int i) 		{ hasChanged = true; buffer.putInt(index, i); }
	private void put(int index, long u) 	{ hasChanged = true; buffer.putInt(index, (int) (u & 0xFFFF_FFFF)); }

	public void bind() { glBindBuffer(GL_UNIFORM_BUFFER, bufferID); }
	public static void unbind() { glBindBuffer(GL_UNIFORM_BUFFER, 0); }
	
	public void cleanUp() {
		glDeleteBuffers(bufferID);
	}
	
//	public static void cleanUp() {
//		for(GLBuffer buffer : UNIFORM_BUFFERS.values())
//			buffer.cleanUp();
//	}
}