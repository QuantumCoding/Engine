package com.Engine.RenderEngine.Models.ModelData.VBOs;

import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class CashedVBO extends DynVBO {
	private float[] data;
	private FloatBuffer dataBuffer;
	private boolean hasChanges, block;
	
	private final float[] CLEAR_ARRAY;
	
	public CashedVBO(int instanceSize) { super(instanceSize); CLEAR_ARRAY = new float[instanceSize]; }

	public void put(BufferUsage usage, float... data) {
		super.put(usage, data);
		
		this.data = data;
		this.dataBuffer = BufferUtils.createFloatBuffer(data.length);

		hasChanges = true;
	}
	
	public void putEmpty(BufferUsage usage, int count) {
		super.putEmpty(usage, count);

		int size = count * super.getInstanceSize();
		this.data = new float[size];
		this.dataBuffer = BufferUtils.createFloatBuffer(size);

		hasChanges = true;
	}
	
	protected void expand() {
		super.expand();

		float[] newData = new float[super.getSize()];
		this.dataBuffer = BufferUtils.createFloatBuffer(super.getSize());
		
		System.arraycopy(data, 0, newData, 0, data.length);
		this.data = newData;
		
		hasChanges = true;
	}
	
	public void clearIndex(int index) { 
		System.arraycopy(CLEAR_ARRAY, 0, data, index * CLEAR_ARRAY.length, CLEAR_ARRAY.length);
		hasChanges = true;
	}
	
	public void update(int offset, float... data) {
		checkHasDataBuffered();
		System.arraycopy(data, 0, this.data, offset, data.length);
		hasChanges = true;
	}
	
//	public int[] reorder(int index, int newIndex) {
//		
//	}
	
	public void submit() {
		if(!hasChanges || block) return;
		dataBuffer.clear(); dataBuffer.put(data); dataBuffer.flip();
		
		bind();
			glBufferData(glBuffer(), data.length * 4, super.getUsage().gl());
			glBufferSubData(glBuffer(), 0, dataBuffer);
		_unbind();
		
		hasChanges = false;
	}
	
	public float[] pullData() { return data; }
	public float[] rawPullData() { return super.pullData(); }
	public void block(boolean block) { this.block = block; submit(); }
}
