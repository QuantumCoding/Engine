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
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import com.Engine.RenderEngine.Util.Color;
import com.Engine.RenderEngine.Util.Color.ColorFormat;
import com.Engine.Util.Vectors.Vector2f;

public class Texture2D extends Texture {
	private Vector2f size;
	private int numberOfRows;
	
	public Texture2D(int textureId, Vector2f size) {
		super(textureId);
		this.size = size;
		numberOfRows = 1;
	}
	
	public Texture2D(BufferedImage image) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); // 4 Because of RGBA

		for(int y = 0; y < image.getHeight(); y ++) {
	    for(int x = 0; x < image.getWidth(); x ++) {
	        buffer.putInt(Color.flipByteOrder(Color.convert(image.getRGB(x, y), ColorFormat.AWT_FORMAT, ColorFormat.OPENGL_FORMAT)));
	    }}

		buffer.flip();		
		initFromBuffer(buffer, new Vector2f(image.getWidth(), image.getHeight()));
	}
	
	public Texture2D(ByteBuffer buffer, Vector2f size) { super(); initFromBuffer(buffer, size); }
	
	private void initFromBuffer(ByteBuffer buffer, Vector2f size) {
		bind();
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, (int) size.getX(), (int) size.getY(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			setDefaultProperties();
			setupMipMaping();
		unbind();
		
		numberOfRows = 1;
	}

	public BufferedImage pullTexture() { return pullTexture(textureId, size); }
	
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
	
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
	
	public Vector2f getOffset(int index) {
		float col = index % numberOfRows, row = index / numberOfRows;		
		return new Vector2f(col / (float) numberOfRows, row / (float) numberOfRows);
	}
	
	public int getNumberOfRows() { return numberOfRows; }

	public int getGLTextureType() { return GL_TEXTURE_2D; }
	public static void unbind_2D(int texurePos) { Texture.unbind(GL_TEXTURE_2D, texurePos); }
}
