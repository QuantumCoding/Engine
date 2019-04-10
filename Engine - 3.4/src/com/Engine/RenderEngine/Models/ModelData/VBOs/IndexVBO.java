package com.Engine.RenderEngine.Models.ModelData.VBOs;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGetBufferSubData;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.Engine.RenderEngine.Models.ModelData.VBO;

public class IndexVBO extends VBO {
	protected int glBuffer() { return GL_ELEMENT_ARRAY_BUFFER; }
	
	public void put(int... data) { put(BufferUsage.Static_Draw, data); }
	public void put(BufferUsage usage, int... data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data); buffer.flip();
		
		super.independentlyBufferedData(usage, data.length);
		
		bind();
			glBufferData(glBuffer(), buffer, usage.gl());		
		_unbind();
	}
	
	public void update(int offset, int... data) {
		checkHasDataBuffered();
		
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data); buffer.flip();
		
		bind();
			glBufferSubData(glBuffer(), offset * DATA_SIZE, buffer);		
		_unbind();
	}
	
	public float[] pullData() {
		ByteBuffer buffer = BufferUtils.createByteBuffer(getSize() * 4);
		bind();
			glGetBufferSubData(glBuffer(), 0, buffer);
		unbind();
		
		IntBuffer fBuffer = buffer.asIntBuffer();
		float[] data = new float[fBuffer.capacity()];
		
		for(int i = 0; i < fBuffer.capacity(); i ++)
			data[i] = fBuffer.get(i);
		
		return data;
	}
}
