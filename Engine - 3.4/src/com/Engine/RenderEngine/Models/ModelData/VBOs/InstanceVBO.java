package com.Engine.RenderEngine.Models.ModelData.VBOs;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBufferSubData;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.Engine.RenderEngine.Models.ModelData.VBO;

public class InstanceVBO extends VBO {
	protected int instanceSize;
	protected int maxInstnceCount;
	protected FloatBuffer emptyBuffer;
	
	public InstanceVBO(int instanceSize) {
		this.instanceSize = instanceSize;
		
		emptyBuffer = BufferUtils.createFloatBuffer(instanceSize);
		emptyBuffer.flip();
	}
	
	public void put(float... data) { put(BufferUsage.Dynamic_Draw, data); }
	public void put(BufferUsage usage, float... data) {
		if(data.length % instanceSize != 0) 
			throw new IllegalArgumentException("Data must be entered in sizes of " + instanceSize);
		
		super.put(usage, data);
		this.maxInstnceCount = data.length / instanceSize;
	}
	
	public void putEmpty(int count) { putEmpty(BufferUsage.Dynamic_Draw, count); }
	public void putEmpty(BufferUsage usage, int count) {
		super.putEmpty(usage, count * instanceSize); 
		this.maxInstnceCount = count;
	}

	public int expand(float expansionFactor) { 
		int newCount = (int) Math.ceil(expansionFactor * maxInstnceCount);
		expand(newCount);  return newCount;
	}
	
	public void expand(int newCount) { 
		super.expand(instanceSize * newCount); 
		this.maxInstnceCount = newCount;
	}
	
	public void updateInstance(int index, float... data) {
		if(data.length != instanceSize) throw new IllegalArgumentException("Data must be entered in size of " + instanceSize);
		update(index * instanceSize, data);
	} 
	
	public void clearIndex(int index) {
		bind();
			glBufferSubData(GL_ARRAY_BUFFER, index * instanceSize * DATA_SIZE, emptyBuffer);		
		_unbind();
	}
	
	public int getInstanceSize() { return instanceSize; }
	public int getMaxInstnceCount() { return maxInstnceCount; }
}
