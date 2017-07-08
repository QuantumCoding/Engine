package com.Engine.RenderEngine.Shaders.Uniforms;

import static org.lwjgl.opengl.GL20.glUniformMatrix4;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Shaders.UniformType;

public class UniformMat4 extends UniformType<Matrix4f> {
	private static final FloatBuffer BUFFER = BufferUtils.createFloatBuffer(16);	
	
	public UniformMat4(String name) { super(name); }
	
	public void load(Matrix4f value) { 
		value.store(BUFFER); BUFFER.flip();
		glUniformMatrix4(location, false, BUFFER);
	}
}