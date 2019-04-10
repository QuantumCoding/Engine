package com.Engine.RenderEngine.Models.ModelData.VBOs;

import static org.lwjgl.opengl.GL31.glCopyBufferSubData;

import java.util.ArrayDeque;
import java.util.Queue;

public class DynVBO extends InstanceVBO {
	private static final float DEFAULT_EXPAND_FACTOR = 1.25f;
	
	private Queue<Integer> openIndexs;
	private float expandFactor;
	private int instanceCount;
	
	public DynVBO(int instanceSize) {
		super(instanceSize);
		this.openIndexs = new ArrayDeque<>();
		this.expandFactor = DEFAULT_EXPAND_FACTOR;
	}
	
	public void putEmpty(BufferUsage usage, int count) {
		for(int i = 0; i < count; i ++) 
			openIndexs.add(i);
		super.putEmpty(usage, count); 
	}
	
	protected void expand() {
		int oldCount = getMaxInstnceCount();
		int newCount = super.expand(expandFactor);
		
		for(int i = oldCount; i < newCount; i ++)
			if(!openIndexs.contains(i)) openIndexs.add(i);
	}
	
	public int addInstance(float... data) {
		if(data.length != instanceSize) throw new IllegalArgumentException("Data must be entered in size of " + instanceSize);
		
		instanceCount ++;
		if(openIndexs.isEmpty()) expand();
		
		int index = openIndexs.poll();
		updateInstance(index, data);
		return index;
	} 
	
	public void updateInstance(int index, float... data) {
		if(openIndexs.contains(index)) throw new IllegalArgumentException("No Entry at " + index);
		super.updateInstance(index, data);
	} 
	
	public int[] reorder(int index, int newIndex) { // TODO: Overwrite in CachedVBO
		if(index < newIndex) throw new IllegalArgumentException("Destination index must be less the Source index");
		if(index == newIndex) throw new IllegalArgumentException("Source and Destination index cannot be the same");
		if(openIndexs.contains(index)) throw new IllegalArgumentException("No Entry at " + index);
	
		for(int i = newIndex, limit = index; i < limit; i ++) {
			if(openIndexs.contains(i)) {
				bind();
					if(newIndex != i)
						glCopyBufferSubData(glBuffer(), glBuffer(), $(newIndex), $(newIndex + 1), $(i - newIndex));
					glCopyBufferSubData(glBuffer(), glBuffer(), $(index), $(newIndex), instanceSize);

					clearIndex(index);
					openIndexs.remove(i);
					openIndexs.add(index);
				_unbind();
				
				return newIndex == i ? null : new int[] { newIndex, i - 1 };
			}
		}
		
		if(openIndexs.isEmpty()) expand();
		int holdIndex = openIndexs.poll();
		
		bind();
			glCopyBufferSubData(glBuffer(), glBuffer(), $(index), $(holdIndex), instanceSize);
			glCopyBufferSubData(glBuffer(), glBuffer(), $(newIndex), $(newIndex + 1), $(index - newIndex));
			glCopyBufferSubData(glBuffer(), glBuffer(), $(index), $(newIndex), instanceSize);

			clearIndex(holdIndex);
			openIndexs.add(holdIndex);
		_unbind();
		
		return new int[] { newIndex, index - 1 };
	}
	
	private int $(int index) { return index * instanceSize; }
	
	public void removeIndex(int index) {
		if(openIndexs.contains(index)) throw new IllegalArgumentException("No Entry at " + index);
		openIndexs.add(index);
		instanceCount --;
		
		clearIndex(index);
	}
		
	public DynVBO setExpandFactor(float expandFactor) {
		if(expandFactor < 1) throw new IllegalArgumentException("Expansion Factor cannot be < 1");
		this.expandFactor = expandFactor;
		return this;
	}
	
	public int getInstanceCount() { return instanceCount; }
}
