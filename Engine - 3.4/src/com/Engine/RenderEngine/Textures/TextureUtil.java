package com.Engine.RenderEngine.Textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import com.Engine.RenderEngine.Util.Color;
import com.Engine.RenderEngine.Util.Color.ColorFormat;
import com.Engine.Util.Vectors.Vector2f;

class TextureUtil {
	private TextureUtil() { }
	
	public static class TextureData {
		public ByteBuffer buffer;
		public Vector2f size;
		
		public TextureData(ByteBuffer buffer, Vector2f size) {
			this.buffer = buffer;
			this.size = size;
		}
	}
	
	public static TextureData from(boolean hasAlpha, InputStream stream) {
		try {
			PNGDecoder decoder = new PNGDecoder(stream);
			ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buffer, decoder.getWidth() * 4, hasAlpha ? PNGDecoder.Format.RGBA : PNGDecoder.Format.RGB);
			buffer.flip();
			
			return new TextureData(buffer, new Vector2f(decoder.getWidth(), decoder.getHeight()));
		} catch(IOException e) { e.printStackTrace(); return null; }
	}
	
	public static TextureData from(BufferedImage image) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); // 4 Because of RGBA

		for(int y = 0; y < image.getHeight(); y ++) {
	    for(int x = 0; x < image.getWidth(); x ++) {
	        buffer.putInt(Color.flipByteOrder(Color.convert(image.getRGB(x, y), ColorFormat.AWT_FORMAT, ColorFormat.OPENGL_FORMAT)));
	    }}

		buffer.flip();
		return new TextureData(buffer, new Vector2f(image.getWidth(), image.getHeight()));
	}
}
