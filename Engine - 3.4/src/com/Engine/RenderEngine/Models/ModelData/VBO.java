package com.Engine.RenderEngine.Models.ModelData;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_COPY;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_READ;
import static org.lwjgl.opengl.GL15.GL_STATIC_COPY;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_READ;
import static org.lwjgl.opengl.GL15.GL_STREAM_COPY;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL15.GL_STREAM_READ;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glGetBufferSubData;
import static org.lwjgl.opengl.GL31.GL_COPY_READ_BUFFER;
import static org.lwjgl.opengl.GL31.GL_COPY_WRITE_BUFFER;
import static org.lwjgl.opengl.GL31.glCopyBufferSubData;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.function.Consumer;

import org.lwjgl.BufferUtils;

public class VBO {
	protected static final int DATA_SIZE = Float.BYTES;
	
	private int vboId;
	private boolean hold;
	
	private BufferUsage usage;
	private int size;
	
	private Consumer<VBO> onExpand;
	
	public VBO() { vboId = glGenBuffers(); }
	
	public void put(float... data) { put(BufferUsage.Static_Draw, data); }
	public void put(BufferUsage usage, float... data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data); buffer.flip();
		
		this.usage = usage;
		this.size = data.length;
		
		bind();
			glBufferData(glBuffer(), buffer, usage.glConstant);		
		_unbind();
	}

	public void putEmpty(int size) { putEmpty(BufferUsage.Static_Draw, size); }
	public void putEmpty(BufferUsage usage, int size) {
		this.usage = usage;
		this.size = size;
		
		bind();
			glBufferData(glBuffer(), size * 4, usage.glConstant);
		_unbind();
	}
	
	public void update(int offset, float... data) {
		checkHasDataBuffered();
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data); buffer.flip();
		
		bind();
			glBufferSubData(glBuffer(), offset * DATA_SIZE, buffer);		
		_unbind();
	}
	
	public void fullUpdate(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(size);
		buffer.put(data); buffer.flip(); fullUpdate(buffer);
	}
	
	public void fullUpdate(FloatBuffer buffer) {
		checkHasDataBuffered();
		
		bind();
			glBufferData(glBuffer(), buffer.capacity() * 4, usage.glConstant);
			glBufferSubData(glBuffer(), 0, buffer);		
		_unbind();
	}
	
	public void expand(int newSize) {
		checkHasDataBuffered();
		if(newSize < size) throw new IllegalArgumentException("New Size: " + newSize + ", must be > Size: " + size);
		
		int newVBO = glGenBuffers();
		glBindBuffer(GL_COPY_WRITE_BUFFER, newVBO);
		glBufferData(GL_COPY_WRITE_BUFFER, newSize * 4, usage.glConstant);

		glBindBuffer(GL_COPY_READ_BUFFER, vboId);
		glCopyBufferSubData(GL_COPY_READ_BUFFER, GL_COPY_WRITE_BUFFER, 0, 0, size * 4);
		
		glBindBuffer(GL_COPY_READ_BUFFER, 0);
		glBindBuffer(GL_COPY_WRITE_BUFFER, 0);
		
		glDeleteBuffers(vboId);
		vboId = newVBO;
		
		this.size = newSize;
		
		if(onExpand != null)
			onExpand.accept(this);
	}
	
	public float[] pullData() {
		ByteBuffer buffer = BufferUtils.createByteBuffer(size * 4);
		bind();
			glGetBufferSubData(glBuffer(), 0, buffer);
		unbind();
		
		FloatBuffer fBuffer = buffer.asFloatBuffer();
		float[] data = new float[fBuffer.capacity()];
		
		for(int i = 0; i < fBuffer.capacity(); i ++)
			data[i] = fBuffer.get(i);
		
		return data;
	}
	
	public void bind() 	 { glBindBuffer(glBuffer(), vboId); }
	public void unbind() { glBindBuffer(glBuffer(), 0); hold = false; }
	
	protected int glBuffer() { return GL_ARRAY_BUFFER; }
	
	public void hold(boolean hold) { this.hold = hold; }
	protected void _unbind() { if(!hold) unbind(); }
	
	public BufferUsage getUsage() { return usage; }
	public int getSize() { return size; }
	public int getId() { return vboId; }
	
	public void setExpansionAction(Consumer<VBO> onExpand) { this.onExpand = onExpand; }
	
	protected void independentlyBufferedData(BufferUsage usage, int size) { this.usage = usage; this.size = size; }
	protected void checkHasDataBuffered() {
		if(usage == null) throw new IllegalStateException("VBO has no Data! Call \"put\" or \"putEmpty\" first");
	}
	
	public void cleanUp() { glDeleteBuffers(vboId); }
	
	public static enum BufferUsage {
		Static_Read	(GL_STATIC_READ), 	Static_Draw	(GL_STATIC_DRAW), 	Static_Copy	(GL_STATIC_COPY),
		Dynamic_Read(GL_DYNAMIC_READ), 	Dynamic_Draw(GL_DYNAMIC_DRAW), 	Dynamic_Copy(GL_DYNAMIC_COPY),
		Stream_Read	(GL_STREAM_READ), 	Stream_Draw	(GL_STREAM_DRAW), 	Stream_Copy	(GL_STREAM_COPY);
		
		private int glConstant;
		private BufferUsage(int gl) { this.glConstant = gl; }
		
		public int gl() { return glConstant; }
	}
}
