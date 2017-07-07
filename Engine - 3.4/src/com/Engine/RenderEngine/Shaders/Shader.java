package com.Engine.RenderEngine.Shaders;

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
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;
import com.Engine.Util.Vectors.Vector4f;

public abstract class Shader {	
	private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;
	
	private Renderer<?, ?> renderer;
	
	public Shader(String vsFilePath, String fsFilePath, Class<? extends Renderer<?, ?>> rendererClass) {
		String classPath = getClass().getName();
		String className = classPath.substring(classPath.lastIndexOf(".") + 1);
		
		try {
			renderer = rendererClass.getConstructor(Shader.class).newInstance(this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.err.println("Failed to load Renderer for " + className + ". Caused By: " + e.toString());
		}
		
		programId = glCreateProgram();
		bind();
		
		vertexShaderId = loadShader(vsFilePath, GL_VERTEX_SHADER);
		fragmentShaderId = loadShader(fsFilePath, GL_FRAGMENT_SHADER);

		glAttachShader(programId, vertexShaderId);
		glAttachShader(programId, fragmentShaderId);
		
		bindAttributies();
		
		glLinkProgram(programId);			
		glValidateProgram(programId);
		
		initUniformLocations();
	}

	protected abstract void initUniformLocations();
	
	protected int getUniformLocation(String uniformName) {
		return glGetUniformLocation(programId, uniformName);
	}
	
	protected abstract void bindAttributies();
	
	protected void bindAttribute(int attribute, String variableName) {
		glBindAttribLocation(programId, attribute, variableName);
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
	
	public void loadInt(int location, int value) {
		glUniform1i(location, value);
	}
	
	public void loadFloat(int location, float value) {
		glUniform1f(location, value);
	}
	
	public void loadVector2f(int location, Vector2f value) {
		glUniform2f(location, value.x, value.y);
	}
	
	public void loadVector3f(int location, Vector3f value) {
		glUniform3f(location, value.x, value.y, value.z);
	}
	
	public void loadVector4f(int location, Vector4f value) {
		glUniform4f(location, value.x, value.y, value.z, value.w);
	}

	public void loadBoolean(int location, boolean value) {
		glUniform1f(location, value ? 1 : 0);
	}

	private static FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);	
	public void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(floatBuffer); floatBuffer.flip();
		glUniformMatrix4(location, false, floatBuffer);
	}
	
	public int getProgramId() {
		return programId;
	}

	public Renderer<?, ?> getRenderer() {
		return renderer;
	}
	
	public int hashCode() {
		return programId;
	}
	
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj == null) return false;
		if(!(obj instanceof Shader)) return false;
		
		Shader other = (Shader) obj;
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
//---------------------------------------------- Static Resources ---------------------------------------------------------------\\	

	private static ClassNode rootNode = new ClassNode() {{ clazz = Object.class; }};
	
	public static final int ATTRIBUTE_LOC_POSITIONS = nextAttribId(Shader.class);
	public static final int ATTRIBUTE_LOC_TEXCOORDS = nextAttribId(Shader.class);
	public static final int ATTRIBUTE_LOC_NORMALS = nextAttribId(Shader.class);
	
	protected static Matrix4f viewMatrix;
	protected static Matrix4f projectionMatrix;
	protected static Vector3f skyColor;

	public static Matrix4f getViewMatrix() { return viewMatrix; }
	public static Matrix4f getProjectionMatrix() { return projectionMatrix; }
	public static Vector3f getSkyColor() { return skyColor; }

	public static void setViewMatrix(Matrix4f viewMatrix) {
		Shader.viewMatrix = viewMatrix;
	}

	public static void setProjectionMatrix(Matrix4f projectionMatrix) {
		Shader.projectionMatrix = projectionMatrix;
	}
	
	public static void setSkyColor(Vector3f skyColor) {
		Shader.skyColor = skyColor;
	}
	
	public static class ShaderCreationException extends Exception {
		private static final long serialVersionUID = 1L;
		
		public ShaderCreationException() { super(); }
		public ShaderCreationException(String message) {super(message);}
	}
	
	private static class ClassNode {
		HashMap<Class<?>, ClassNode> children = new HashMap<>();
		Class<?> clazz;
		int nextId;
	}

	public static int nextAttribId(Class<? extends Shader> clazz) { return nextAttribId(clazz, 1); }
	public static int nextAttribId(Class<? extends Shader> clazz, int reserve) {
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
		try { return node.nextId ++; }
		finally { node.nextId += reserve - 1; }
	} 
}
