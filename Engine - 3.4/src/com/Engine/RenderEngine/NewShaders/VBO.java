package com.Engine.RenderEngine.NewShaders;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class VBO {
	private int vboId;
	private boolean hold;
	
	public VBO() { vboId = glGenBuffers(); }
	
	public void put(float... data) { put(false, data); }
	public void put(boolean dynamic, float... data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data); buffer.flip();
		
		bind();
			glBufferData(GL_ARRAY_BUFFER, buffer, dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);		
		_unbind();
	}
	
	public void bind() 	 { glBindBuffer(GL_ARRAY_BUFFER, vboId); }
	public void unbind() { glBindBuffer(GL_ARRAY_BUFFER, 0); hold = false; }
	
	public void hold(boolean hold) { this.hold = hold; }
	private void _unbind() { if(!hold) unbind(); }
	
	public void cleanUp() { glDeleteBuffers(vboId); }
}
