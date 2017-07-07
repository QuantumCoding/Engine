package com.Engine.RenderEngine.NewShaders.Uniforms;

import static org.lwjgl.opengl.GL20.glUniform1f;

import com.Engine.RenderEngine.NewShaders.UniformType;

public class UniformFloat extends UniformType<Float> {
	public UniformFloat(String name) { super(name); }
	public void load(Float value) { glUniform1f(location, value); }
}