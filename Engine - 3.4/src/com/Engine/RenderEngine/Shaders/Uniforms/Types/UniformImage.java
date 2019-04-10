package com.Engine.RenderEngine.Shaders.Uniforms.Types;

import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL15.GL_READ_ONLY;
import static org.lwjgl.opengl.GL15.GL_READ_WRITE;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL42.glBindImageTexture;

import com.Engine.RenderEngine.Shaders.RawShader;
import com.Engine.RenderEngine.Shaders.UniformType;
import com.Engine.RenderEngine.Textures.Texture;

public class UniformImage extends UniformType<Texture> {
	public static enum AccessQualifier {
		READ_WRITE(GL_READ_WRITE),
		READ_ONLY (GL_READ_ONLY),
		WRITE_ONLY(GL_WRITE_ONLY);
		
		private int gl;
		private AccessQualifier(int gl) { this.gl = gl; }
		
		public int gl() { return gl; }
	}
	
	private AccessQualifier access;
	private int index;
	
	public UniformImage(String name, int index, AccessQualifier access) { 
		super(name); 
		
		this.index = index;
		this.access = access;
	}
	
	protected void postAttach(RawShader shader) { glUniform1i(location, index); } 

	public void load(Texture value) {
		glBindImageTexture(index, value.getTextureId(), 0, false, 0, access.gl, GL_RGBA8);
	}
}
