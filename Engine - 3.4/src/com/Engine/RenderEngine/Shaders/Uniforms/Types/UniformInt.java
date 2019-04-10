package com.Engine.RenderEngine.Shaders.Uniforms.Types;

import static org.lwjgl.opengl.GL20.glUniform1i;

import com.Engine.RenderEngine.Shaders.UniformType;

public class UniformInt extends UniformType<Integer> {
	public UniformInt(String name) { super(name); }
	
	public void load(Integer value) { 
		if(buffer != null) buffer.loadInt(offset, value);
		else glUniform1i(location, value); 
	}
}