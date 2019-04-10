package com.Engine.RenderEngine.Shaders.Uniforms.Types;

import static org.lwjgl.opengl.GL20.glUniformMatrix3;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix3f;

import com.Engine.RenderEngine.Shaders.UniformType;

public class UniformMat3 extends UniformType<Matrix3f> {
	private static final FloatBuffer BUFFER = BufferUtils.createFloatBuffer(9);	
	
	public UniformMat3(String name) { super(name); }
	
	public void load(Matrix3f value) { 
		value.store(BUFFER); BUFFER.flip();
		 
		if(buffer != null) buffer.loadMatrix3(offset, BUFFER);
		else glUniformMatrix3(location, false, BUFFER);
	}
}