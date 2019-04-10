package com.Engine.RenderEngine.Textures;

import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGetTexImage;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;
import static org.lwjgl.opengl.GL21.GL_SRGB_ALPHA;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import com.Engine.RenderEngine.Textures.TextureUtil.TextureData;
import com.Engine.RenderEngine.Util.Color;
import com.Engine.RenderEngine.Util.Color.ColorFormat;
import com.Engine.Util.Vectors.Vector2f;

public class Texture2D extends Texture {
	/**
	 *  For use with already created textures
	 */
	public Texture2D(int textureId) { super(textureId); }
	
	/**
	 *  Load Texture From BufferedImage
	 */
	public Texture2D(BufferedImage image) { this(false, TextureUtil.from(image)); }
	
	/**
	 *  Loads PNG from Input Stream
	 */
	public Texture2D(boolean sRGB, InputStream in) { this(sRGB, TextureUtil.from(true, in)); }
	
	/**
	 *  Loads texture from Pre-Stored ByteBuffer
	 */
	public Texture2D(boolean sRGB, ByteBuffer buffer, Vector2f size) {
		super(); 
		initFromBuffer(sRGB, new TextureData(buffer, size)); 
	}
	
	public Texture2D(boolean sRGB, TextureData data) {
		super(); 
		initFromBuffer(sRGB, data); 
	}

	public void rebuffer(boolean sRGB, ByteBuffer data, Vector2f size) { initFromBuffer(sRGB, new TextureData(data, size)); }
	private void initFromBuffer(boolean sRGB, TextureData data) {
		bind();
			glTexImage2D(GL_TEXTURE_2D, 0, 
					sRGB ? GL_SRGB_ALPHA : GL_RGBA, 
					(int) data.size.getX(), (int) data.size.getY(), 0, 
					GL_RGBA, GL_UNSIGNED_BYTE, data.buffer
				);
			
			setDefaultProperties();
			setupMipMaping();
		unbind();
	}

	public BufferedImage pullTexture(Vector2f size) { return pullTexture(textureId, size); }
	
	public static BufferedImage pullTexture(int textureId, Vector2f size) {
		BufferedImage image = new BufferedImage((int)size.x, (int)size.y, BufferedImage.TYPE_INT_ARGB);
		ByteBuffer buffer = BufferUtils.createByteBuffer((int) (size.x * size.y) * 4);
		
		glBindTexture(GL_TEXTURE_2D, textureId);
		glGetTexImage(GL_TEXTURE_2D, 0, GL_UNSIGNED_BYTE, GL_RGBA, buffer);
		
		for(int x = 0; x < image.getWidth(); x ++) {
		for(int y = 0; y < image.getHeight(); y ++) {
			image.setRGB(x, y, Color.convert(Color.flipByteOrder(buffer.getInt(x + (y * image.getWidth()))), 
					ColorFormat.OPENGL_FORMAT, ColorFormat.AWT_FORMAT));
		}}
		
		return image;
	}
	
	public void setDefaultProperties() {
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	}
	
	protected void setupMipMaping() {
		glGenerateMipmap(GL_TEXTURE_2D);		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, -0.6f);
	}
	
	public int getGLTextureType() { return GL_TEXTURE_2D; }
	public static void unbind_2D(int texurePos) { Texture.unbind(GL_TEXTURE_2D, texurePos); }
}
