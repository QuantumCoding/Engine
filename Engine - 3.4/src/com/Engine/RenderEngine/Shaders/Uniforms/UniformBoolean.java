package com.Engine.RenderEngine.Shaders.Uniforms;

import static org.lwjgl.opengl.GL20.glUniform1f;

import com.Engine.RenderEngine.Shaders.UniformType;

public class UniformBoolean extends UniformType<Boolean> {
	public UniformBoolean(String name) { super(name); }
	public void load(Boolean value) { glUniform1f(location, value ? 1 : 0); }
}