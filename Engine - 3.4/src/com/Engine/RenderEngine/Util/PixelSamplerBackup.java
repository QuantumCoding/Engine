package com.Engine.RenderEngine.Util;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glReadPixels;
import static org.lwjgl.opengl.GL15.GL_READ_ONLY;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glMapBuffer;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;
import static org.lwjgl.opengl.GL21.GL_PIXEL_PACK_BUFFER;
import static org.lwjgl.opengl.GL44.glBufferStorage;

import java.nio.ByteBuffer;

import com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage;

public class PixelSamplerBackup {
	private int pboID;
	private int width, height;
	
	public PixelSamplerBackup(int width, int height) {
		pboID = glGenBuffers();
		
		this.width = width;
		this.height = height;
		
		bind();
			glBufferStorage(GL_PIXEL_PACK_BUFFER, width * height * 4, BufferUsage.Stream_Read.gl());
		unbind();
	}
	
	public void sample(int x, int y) { 
		bind();
			glReadPixels(x, y, width, height, GL_RGBA, GL_UNSIGNED_BYTE, 0); 
		unbind();
	}
	
	public ByteBuffer get(ByteBuffer readBuffer) {
		bind();
		return glMapBuffer(GL_PIXEL_PACK_BUFFER, GL_READ_ONLY, readBuffer);
	}
	
	public void clear() {
		glUnmapBuffer(GL_PIXEL_PACK_BUFFER);
		unbind();
	}
	
	public static int[] evaluate(ByteBuffer buffer) {
		int[] rgba = new int[4];
		for(int i = 0; i < 4; i ++)
			rgba[i] = buffer.get() & 0xFF;
		return rgba;
	}
	
	public void bind() { glBindBuffer(GL_PIXEL_PACK_BUFFER, pboID); }
	public static void unbind() { glBindBuffer(GL_PIXEL_PACK_BUFFER, 0); }
	
	public void cleanUp() {
		glDeleteBuffers(pboID);
	}
}
