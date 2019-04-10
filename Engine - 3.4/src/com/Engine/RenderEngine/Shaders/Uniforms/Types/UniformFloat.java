package com.Engine.RenderEngine.Shaders.Uniforms.Types;

import static org.lwjgl.opengl.GL20.glUniform1f;

import com.Engine.RenderEngine.Shaders.UniformType;

public class UniformFloat extends UniformType<Float> {
	public UniformFloat(String name) { super(name); }
	
	public void load(Float value) { 
		if(buffer != null) buffer.loadFloat(offset, value);
		else glUniform1f(location, value); 
	}
}