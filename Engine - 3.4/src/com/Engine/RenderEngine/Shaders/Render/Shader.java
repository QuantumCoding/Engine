package com.Engine.RenderEngine.Shaders.Render;

import static org.lwjgl.opengl.GL20.glUseProgram;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Models.ModelData.Attribute;
import com.Engine.RenderEngine.Shaders.RawShader;
import com.Engine.Util.Vectors.Vector3f;

public abstract class Shader extends RawShader {	
	private Renderer<?, ?, ?> renderer;
	
	public Shader(String vsFilePath, String fsFilePath, Class<? extends Renderer<?, ?, ?>> rendererClass) {
		String classPath = getClass().getName();
		String className = classPath.substring(classPath.lastIndexOf(".") + 1);
		
		if(rendererClass != null) {
			try {
				renderer = rendererClass.getConstructor(Shader.class).newInstance(this);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				System.err.println("Failed to load Renderer for " + className + ".\nCaused By: " + e.toString());
			}
		}
		
		loadShaderFile(vsFilePath, ShaderTypes.VertextShader);
		loadShaderFile(fsFilePath, ShaderTypes.FragmentShader);
		
		link();
	}
	
	public static void unbind() { glUseProgram(0); }
	
	public Renderer<?, ?, ?> getRenderer() { return renderer; }
	
//-------------------------------------------- Static Resources -----------------------------------------------------\\	

	private static ClassNode rootNode = new ClassNode() {{ clazz = Object.class; }};
	
	public static final Attribute ATTRIBUTE_LOC_POSITIONS = nextAttribId(Shader.class);
	public static final Attribute ATTRIBUTE_LOC_TEXCOORDS = nextAttribId(Shader.class);
	public static final Attribute ATTRIBUTE_LOC_NORMALS   = nextAttribId(Shader.class);
	
	protected static Matrix4f viewMatrix;
	protected static Matrix4f projectionMatrix;
	protected static Matrix4f orthographicMatrix;
	protected static Vector3f skyColor;

	public static Matrix4f getViewMatrix() { return viewMatrix; }
	public static Matrix4f getProjectionMatrix() { return projectionMatrix; }
	public static Matrix4f getOrthographicMatrix() { return orthographicMatrix; }
	public static Vector3f getSkyColor() { return skyColor; }

	public static void setViewMatrix(Matrix4f viewMatrix) { Shader.viewMatrix = viewMatrix; }
	public static void setProjectionMatrix(Matrix4f projectionMatrix) { Shader.projectionMatrix = projectionMatrix; }
	public static void setOrthographicMatrix(Matrix4f orthoMatrix) { Shader.orthographicMatrix = orthoMatrix; }
	public static void setSkyColor(Vector3f skyColor) { Shader.skyColor = skyColor; }
	
	private static class ClassNode {
		HashMap<Class<?>, ClassNode> children = new HashMap<>();
		Class<?> clazz;
		int nextId;
	}

	public static Attribute nextAttribId(Class<? extends Shader> clazz) { return nextAttribId(clazz, 1); }
	public static Attribute nextAttribId(Class<? extends Shader> clazz, int reserve) {
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
