package com.Engine.RenderEngine.Textures;

import static org.lwjgl.opengl.GL11.GL_MAX_TEXTURE_SIZE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_HEIGHT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WIDTH;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glGetTexLevelParameteri;
import static org.lwjgl.opengl.GL11.glGetTexParameteri;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_DEPTH;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MAX_LEVEL;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.GL_MAX_ARRAY_TEXTURE_LAYERS;

import com.Engine.Util.Math.MathUtil;

public abstract class Texture {
	public static final int MAX_TEXTURE_SIZE = glGetInteger(GL_MAX_TEXTURE_SIZE);
	public static final int MAX_ARRAY_TEXTURE_LAYERS = glGetInteger(GL_MAX_ARRAY_TEXTURE_LAYERS);
	
	protected int textureId;
	protected int width, height, depth, maxMipLayers;

	protected Texture() { this(glGenTextures()); }
	protected Texture(int textureId) {
		this.textureId = textureId;
	}
	
	public abstract int getGLTextureType();
	public int getTextureId() { return textureId; }

	protected void pullTextureSize(int target) {
		this.width = glGetTexLevelParameteri(target,  0, GL_TEXTURE_WIDTH);
		this.height = glGetTexLevelParameteri(target, 0, GL_TEXTURE_HEIGHT);
		this.depth = glGetTexLevelParameteri(target,  0, GL_TEXTURE_DEPTH);
		
		this.maxMipLayers = calculateMaxMipmapCount(width, height, depth);
		int maxMipmap = glGetTexParameteri(getGLTextureType(), GL_TEXTURE_MAX_LEVEL);
		if(maxMipLayers > maxMipmap) this.maxMipLayers = maxMipmap;
	}
	
	public static int calculateMaxMipmapCount(int width, int height, int depth) {
		return 1 + (int) Math.floor(MathUtil.log2(Math.max(Math.max(width, height), depth)));
	}
	
	public void bind() { bind(0); }
	public void bind(int texurePos) {
		bind(getGLTextureType(), texurePos, textureId);
	}

	public void unbind() { unbind(0); }
	public void unbind(int texurePos) {
		unbind(getGLTextureType(), texurePos);
	}
	
	public static void unbind(int glTextureType, int texurePos) {
		bind(glTextureType, texurePos, 0);
	}
	
	public static void bind(int glTextureType, int texurePos, int texureId) {
		glActiveTexture(GL_TEXTURE0 + texurePos);
		glBindTexture(glTextureType, texureId);
	}
	
	public void cleanUp() { glDeleteBuffers(textureId); }
	
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
