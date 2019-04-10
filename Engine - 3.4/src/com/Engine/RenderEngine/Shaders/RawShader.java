package com.Engine.RenderEngine.Shaders;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Engine.RenderEngine.Models.ModelData.Attribute;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformLinker;

public abstract class RawShader {	
	private int programId;
	private int[] shaderComponents;

	private boolean validated;
	
	public RawShader() {
		shaderComponents = new int[ShaderTypes.values().length];
		programId = glCreateProgram();
	}

	protected abstract void bindAttributies();
	
	protected void link() {
		if(validated) throw new IllegalStateException("Shader has already been linked");
		
		for(int shader : shaderComponents) {
			if(shader <= 0) continue;
			glAttachShader(programId, shader);
		}
		
		bindAttributies(); // Do not move
		
		glLinkProgram(programId);	
		if(glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE)
			System.err.println("Could not compile "+ getClass().getSimpleName() +": \n" + glGetProgramInfoLog(programId, 1024));
		
		glValidateProgram(programId);
		
		bind();
			UniformLinker.initUniformLocations(this);
		unbind();
		
		validated = true;
	}
	
	protected int loadShader(CharSequence shaderSource, ShaderTypes type) {
		if(validated) throw new IllegalStateException("Shader has already been linked; No new Components can be added");
		
		shaderSource = loadGLSLImports(shaderSource);
		
		int shaderId = glCreateShader(type.gl);
		glShaderSource(shaderId, shaderSource);
		glCompileShader(shaderId);
		
		if(glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Could not compile "+ getClass().getSimpleName() +": \n" + glGetShaderInfoLog(shaderId, 1024));
			return -1;
		}
		
		shaderComponents[type.ordinal()] = shaderId;
		return shaderId;
	}
	
	private static final Pattern IMPORT_PATTERN = Pattern.compile("#import (.*)");
	private CharSequence loadGLSLImports(CharSequence shaderSource) {
		Matcher matcher = IMPORT_PATTERN.matcher(shaderSource);
		if(!matcher.find()) return shaderSource;
		
		StringBuilder result = new StringBuilder();
		int lastEndIndex = 0;
		
		do {
			String file = matcher.group(1);
			
			try {
				result.append(shaderSource.subSequence(lastEndIndex, matcher.start()));
				lastEndIndex = matcher.end();
				
				result.append(_loadShaderFile(file));
				
			} catch(IOException e) { System.err.println("Failed to load Import: " + file); }
		} while(matcher.find());
		
		result.append(shaderSource.subSequence(lastEndIndex, shaderSource.length()));
		return loadGLSLImports(result);
	}
	
	protected int getUniformLocation(String uniformName) { return glGetUniformLocation(programId, uniformName); }
	
	protected void bindAttribute(Attribute attribute, String variableName) {
		glBindAttribLocation(programId, attribute.getId(), variableName);
	}
	
	protected void bindFragOutput(int buffer, String variableName) {
		glBindFragDataLocation(programId, buffer, variableName);
	}

	public void bind()  { glUseProgram(programId); }	
	public static void unbind() { glUseProgram(0); }
	
	public int getProgramId() { return programId; }
	
	protected int loadShaderFile(String fileName, ShaderTypes type) {
		try { return loadShader(_loadShaderFile(fileName), type); }
		catch(IOException e) { e.printStackTrace(); return -1; }
	}
	
	private CharSequence _loadShaderFile(String fileName) throws IOException {
		StringBuilder shaderSource = new StringBuilder();
		
		InputStream resourceStream = this.getClass().getResourceAsStream(fileName);
		BufferedReader shaderReader = new BufferedReader(new InputStreamReader(resourceStream));
		String line;

		while((line = shaderReader.readLine()) != null) {
			shaderSource.append(line).append("\n");
		}
		
		shaderReader.close();
		return shaderSource;
	}
	
	public int hashCode() { return programId; }
	
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj == null) return false;
		if(!(obj instanceof RawShader)) return false;
		
		RawShader other = (RawShader) obj;
		if(other.programId != programId)
			return false;
		
		return true;
	}
	
	public void cleanUp() {
		unbind();
		
		for(int shader : shaderComponents) {
			if(shader <= 0) continue;
			
			glDetachShader(programId, shader);
			glDeleteShader(shader);
		}
		
		glDeleteProgram(programId); 
	}
	
	protected static enum ShaderTypes {
		VertextShader(GL_VERTEX_SHADER), 
		FragmentShader(GL_FRAGMENT_SHADER),

		GeometryShader(GL_GEOMETRY_SHADER),

		TessControlShader(GL_TESS_CONTROL_SHADER),
		TessEvaluationShader(GL_TESS_EVALUATION_SHADER),
		
		ComputeShader(GL_COMPUTE_SHADER);
		
		private int gl;
		private ShaderTypes(int gl) { this.gl = gl; }
		
		public int gl() { return gl; }
	}
}
