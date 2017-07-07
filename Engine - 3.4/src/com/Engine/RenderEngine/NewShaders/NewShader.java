package com.Engine.RenderEngine.NewShaders;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.AnnotationFormatError;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Models.ModelData.Attribute;
import com.Engine.RenderEngine.NewShaders.Uniforms.Uniform;
import com.Engine.RenderEngine.NewShaders.Uniforms.UniformTexture;
import com.Engine.Util.Vectors.Vector3f;

public abstract class NewShader {	
	private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;
	
	private NewRenderer<?, ?, ?> renderer;
	
	public NewShader(String vsFilePath, String fsFilePath, Class<? extends NewRenderer<?, ?, ?>> rendererClass) {
		String classPath = getClass().getName();
		String className = classPath.substring(classPath.lastIndexOf(".") + 1);
		
		if(rendererClass != null) {
			try {
				renderer = rendererClass.getConstructor(NewShader.class).newInstance(this);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				System.err.println("Failed to load Renderer for " + className + ".\n Caused By: " + e.toString());
			}
		}
		
		programId = glCreateProgram();
		bind();
		
		vertexShaderId = loadShader(vsFilePath, GL_VERTEX_SHADER);
		fragmentShaderId = loadShader(fsFilePath, GL_FRAGMENT_SHADER);

		glAttachShader(programId, vertexShaderId);
		glAttachShader(programId, fragmentShaderId);
		
		glLinkProgram(programId);			
		glValidateProgram(programId);
		
		bind();
			bindAttributies();
			initUniformLocations();
		unbind();	
	}
	
	@SuppressWarnings("unchecked")
	protected void initUniformLocations() {
		for(Field field : getClass().getDeclaredFields()) {
			if(!field.isAnnotationPresent(Uniform.class)) continue;
			
			Class<?> type = field.getType();
			boolean isArray = type.isArray();
			if(isArray) type = type.getComponentType();

			if(!UniformType.class.isAssignableFrom(type) || type.equals(UniformType.class)) 
				throw new AnnotationFormatError(Uniform.ERROR_MESSAGE + "; not of type " + type.getSimpleName());
			
			Uniform annotation = field.getAnnotation(Uniform.class);
			String name = annotation.value().isEmpty() ? field.getName() : annotation.value();
			int index = annotation.index();
			
			Object value;
			
			if(isArray) {
				value = Array.newInstance(type, annotation.size());
				for(int i = 0; i < annotation.size(); i ++)
					Array.set(value, i, createUniform(name + "["+i+"]", index, (Class<? extends UniformType<?>>) type));
			} else {
				value = createUniform(name, index, (Class<? extends UniformType<?>>) type);
			}
			
			try { 
				if((field.getModifiers() & Modifier.FINAL) != 0) {
					Field modifiersField = Field.class.getDeclaredField("modifiers");
					modifiersField.setAccessible(true);
					modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
				}
				
				field.setAccessible(true); field.set(this, value);
			} catch(IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
	private <E> UniformType<E> createUniform(String name, int index, Class<? extends UniformType<E>> type) {
		try {
			UniformType<E> value;
			if(type.equals(UniformTexture.class)) {
				Constructor<? extends UniformType<E>> constructor = 
						(Constructor<? extends UniformType<E>>) type.getConstructor(String.class, Integer.class);
				value = constructor.newInstance(name, index);
			} else {
				Constructor<? extends UniformType<E>> constructor = 
						(Constructor<? extends UniformType<E>>) type.getConstructor(String.class);
				value = constructor.newInstance(name);
			}
			
			Method method = type.getMethod("attach", NewShader.class);
			method.invoke(value, this);
			
			return value;
		} catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected int getUniformLocation(String uniformName) {
		return glGetUniformLocation(programId, uniformName);
	}
	
	protected abstract void bindAttributies();
	
	protected void bindAttribute(Attribute attribute, String variableName) {
		glBindAttribLocation(programId, attribute.getId(), variableName);
	}
	
	private int loadShader(String fileName, int type) {
		StringBuilder shaderSource = new StringBuilder();
		
		try {
			InputStream resourceStream = this.getClass().getResourceAsStream(fileName);
			BufferedReader shaderReader = new BufferedReader(new InputStreamReader(resourceStream));
			String line;

			while((line = shaderReader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			
			shaderReader.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}		
		
		int shaderId = glCreateShader(type);
		glShaderSource(shaderId, shaderSource);
		glCompileShader(shaderId);
		
		if(glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Could not compile shader: \n" + glGetShaderInfoLog(shaderId, 1024));
		}
		
		return shaderId;
	}
	
	public void bind() { glUseProgram(programId); }	
	public static void unbind() { glUseProgram(0); }
	
	public int getProgramId() { return programId; }
	public NewRenderer<?, ?, ?> getRenderer() { return renderer; }
	
	public int hashCode() { return programId; }
	
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj == null) return false;
		if(!(obj instanceof NewShader)) return false;
		
		NewShader other = (NewShader) obj;
		if(other.programId != programId)
			return false;
		
		return true;
	}
	
	public void cleanUp() {
		unbind();
		
		glDetachShader(programId, vertexShaderId);
		glDetachShader(programId, fragmentShaderId);
		
		glDeleteShader(vertexShaderId);
		glDeleteShader(fragmentShaderId);
		
		glDeleteProgram(programId);
	}
	
//-------------------------------------------- Static Resources -----------------------------------------------------\\	

	private static ClassNode rootNode = new ClassNode() {{ clazz = Object.class; }};
	
	public static final Attribute ATTRIBUTE_LOC_POSITIONS = nextAttribId(NewShader.class);
	public static final Attribute ATTRIBUTE_LOC_TEXCOORDS = nextAttribId(NewShader.class);
	public static final Attribute ATTRIBUTE_LOC_NORMALS   = nextAttribId(NewShader.class);
	
	protected static Matrix4f viewMatrix;
	protected static Matrix4f projectionMatrix;
	protected static Vector3f skyColor;

	public static Matrix4f getViewMatrix() { return viewMatrix; }
	public static Matrix4f getProjectionMatrix() { return projectionMatrix; }
	public static Vector3f getSkyColor() { return skyColor; }

	public static void setViewMatrix(Matrix4f viewMatrix) { NewShader.viewMatrix = viewMatrix; }
	public static void setProjectionMatrix(Matrix4f projectionMatrix) { NewShader.projectionMatrix = projectionMatrix; }
	public static void setSkyColor(Vector3f skyColor) { NewShader.skyColor = skyColor; }
	
	private static class ClassNode {
		HashMap<Class<?>, ClassNode> children = new HashMap<>();
		Class<?> clazz;
		int nextId;
	}

	public static Attribute nextAttribId(Class<? extends NewShader> clazz) { return nextAttribId(clazz, 1); }
	public static Attribute nextAttribId(Class<? extends NewShader> clazz, int reserve) {
		Class<?> current = clazz; int level = 0;
		while((current = current.getSuperclass()) != Object.class) level ++;

		current = clazz; 
		Class<?>[] superTypes = new Class[level + 1]; level --;
		while((current = current.getSuperclass()) != Object.class) superTypes[level --] = current;
		superTypes[superTypes.length - 1] = clazz;
		
		level = 0;
		ClassNode node = rootNode; 
		while(level < superTypes.length) {
			ClassNode next = node.children.get(superTypes[level]);
			if(next == null) {
				node.children.put(superTypes[level], next = new ClassNode());
				next.clazz = superTypes[level];
				next.nextId = node.nextId;
			}
			
			node = next;
			level ++;
		}
		
		if(node.children.size() > 0)
			throw new IllegalStateException("Cannot generate new IDs once a subclass has generated IDs");
		try { return new Attribute(node.nextId ++, reserve); }
		finally { node.nextId += reserve - 1; }
	} 
}
