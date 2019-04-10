package com.Engine.RenderEngine.Shaders.Uniforms.Types;

import static org.lwjgl.opengl.GL20.glUniform1f;

import com.Engine.RenderEngine.Shaders.UniformType;

public class UniformBoolean extends UniformType<Boolean> {
	public UniformBoolean(String name) { super(name); }
	
	public void load(Boolean value) { 
		if(buffer != null) buffer.loadFloat(offset, value ? 1 : 0);
		else glUniform1f(location, value ? 1 : 0); 
	}
}