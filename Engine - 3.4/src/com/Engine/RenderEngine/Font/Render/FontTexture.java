package com.Engine.RenderEngine.Font.Render;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.glTexImage3D;
import static org.lwjgl.opengl.GL12.glTexSubImage3D;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import com.Engine.RenderEngine.Textures.Texture;
import com.Engine.RenderEngine.Util.Color;
import com.Engine.RenderEngine.Util.Color.ColorFormat;

public class FontTexture extends Texture {

	public FontTexture(BufferedImage... textures) {
		if(textures.length < 1) throw new IllegalArgumentException("Must provide at lease on Image");
		
		int width = textures[0].getWidth();
		int height = textures[0].getWidth();
		int layerCount = textures.length;
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * layerCount * 4); // 4 Because of RGBA

		for(BufferedImage image : textures) {
			for(int y = 0; y < image.getHeight(); y ++) {
		    for(int x = 0; x < image.getWidth(); x ++) {
		        buffer.putInt(Color.flipByteOrder(Color.convert(image.getRGB(x, y), ColorFormat.AWT_FORMAT, ColorFormat.OPENGL_FORMAT)));
		    }}
		}

		buffer.flip();	
		
		bind();
			glTexImage3D(GL_TEXTURE_2D_ARRAY, 0, GL_RGBA, width, height, textures.length, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
			setDefaultProperties();
			
			glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, 0, width, height, layerCount, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		unbind();
	}
	
	protected void setDefaultProperties() {
		glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	}
	
	public int getGLTextureType() { return GL_TEXTURE_2D_ARRAY; }
}
