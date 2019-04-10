package com.Engine.RenderEngine.Util;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glReadPixels;
import static org.lwjgl.opengl.GL15.GL_READ_ONLY;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glMapBuffer;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;
import static org.lwjgl.opengl.GL21.GL_PIXEL_PACK_BUFFER;

import java.nio.ByteBuffer;

import com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage;
import com.Engine.Util.Vectors.Vector3f;

public class PixelSampler {
	private int pboID;
	
	private int width, height;
	private int x, y;
	
	private int delay;
	private int step;
	
	public PixelSampler(int width, int height) { this(width, height, 2); }
	public PixelSampler(int width, int height, int delay) {
		if(delay < 1) throw new IllegalArgumentException("Delat must be > 0");
		
		pboID = glGenBuffers();
		
		this.width = width;
		this.height = height;
		
		this.delay =  delay;
		
		bind();
			glBufferData(GL_PIXEL_PACK_BUFFER, width * height * 4, BufferUsage.Stream_Read.gl());
		unbind();
	}
	
	public PixelSampler sample(int x, int y) { this.x = x; this.y = y; this.step = 0; return this; }
	
	public Vector3f get() {
		bind(); try {
			switch(step ++) {
				case 0:
					glReadPixels(x, y, width, height, GL_RGBA, GL_UNSIGNED_BYTE, 0); 
				break;

				default: 
					if(step < delay) break;
					ByteBuffer readBuffer = glMapBuffer(GL_PIXEL_PACK_BUFFER, GL_READ_ONLY, null);
					
					if(readBuffer != null) { 
						glUnmapBuffer(GL_PIXEL_PACK_BUFFER);
						return new Vector3f(readBuffer.get() & 0xFF, readBuffer.get() & 0xFF, readBuffer.get() & 0xFF);
					}
				break;
			}
				
			return null;
		} finally { unbind(); }
	}
	
	public void bind() { glBindBuffer(GL_PIXEL_PACK_BUFFER, pboID); }
	public static void unbind() { glBindBuffer(GL_PIXEL_PACK_BUFFER, 0); }
	
	public void cleanUp() {
		glDeleteBuffers(pboID);
	}
}
