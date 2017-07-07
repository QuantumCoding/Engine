package com.Engine.RenderEngine.Textures;

import static org.lwjgl.opengl.GL11.GL_MAX_TEXTURE_SIZE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.GL_MAX_ARRAY_TEXTURE_LAYERS;

public abstract class Texture {
	protected int textureId;

	protected Texture() { this(glGenTextures()); }
	protected Texture(int textureId) {
		this.textureId = textureId;
	}
	
	public abstract int getGLTextureType();
	public int getTextureId() { return textureId; }

	public void bind() { bind(0); }
	public void bind(int texurePos) {
		bind(getGLTextureType(), texurePos, textureId);
	}

	public void unbind() { unbind(0); }
	public void unbind(int texurePos) {
		unbind(getGLTextureType(), texurePos);
	}
	
	public static void unbind(int glTextureType, int texurePos) {
		glActiveTexture(GL_TEXTURE0 + texurePos);
		glBindTexture(glTextureType, 0);
	}
	
	public static void bind(int glTextureType, int texurePos, int texureId) {
		glActiveTexture(GL_TEXTURE0 + texurePos);
		glBindTexture(glTextureType, texureId);
	}
	
	public void cleanUp() { glDeleteBuffers(textureId); }
	
	public static int getGLMaxTextureSize() { return glGetInteger(GL_MAX_TEXTURE_SIZE); }
	public static int getGLMaxArrayTextureSize() { return glGetInteger(GL_MAX_ARRAY_TEXTURE_LAYERS); }
	
	public int hashCode() { return textureId; }

	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof Texture))
			return false;
		
		Texture other = (Texture) obj;
		if(textureId != other.textureId) return false;
		if(getGLTextureType() != other.getGLTextureType()) return false;
		
		return true;
	}
}
