package com.Engine.RenderEngine.NewShaders.Uniforms;

import static org.lwjgl.opengl.GL20.glUniform1i;

import com.Engine.RenderEngine.NewShaders.UniformType;

public class UniformInt extends UniformType<Integer> {
	public UniformInt(String name) { super(name); }
	public void load(Integer value) { glUniform1i(location, value); }
}