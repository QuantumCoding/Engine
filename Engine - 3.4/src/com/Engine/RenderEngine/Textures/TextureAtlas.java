package com.Engine.RenderEngine.Textures;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.Engine.Util.Vectors.Vector2f;

public class TextureAtlas extends Texture2D {
	private int numberOfRows;
	
	public TextureAtlas(int textureId, int numberOfRows) {
		super(textureId);
		this.numberOfRows = numberOfRows;
	}
	
	public TextureAtlas(BufferedImage image, int numberOfRows) {
		super(image);
		this.numberOfRows = numberOfRows;
	}
	
	public TextureAtlas(boolean sRGB, InputStream in, int numberOfRows) {
		super(sRGB, in);
		this.numberOfRows = numberOfRows;
	}
	
	public TextureAtlas(boolean sRGB, ByteBuffer buffer, Vector2f size, int numberOfRows) {
		super(sRGB, buffer, size); 
		this.numberOfRows = numberOfRows;
	}
	
//	Atlas Specific Code ----------------------------------------------------------
	
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
	
	public Vector2f getOffset(int index) {
		float col = index % numberOfRows, row = index / numberOfRows;		
		return new Vector2f(col / (float) numberOfRows, row / (float) numberOfRows);
	}
	
	public int getNumberOfRows() { return numberOfRows; }

	public static void unbind_2D(int texurePos) { Texture.unbind(GL_TEXTURE_2D, texurePos); }
}
