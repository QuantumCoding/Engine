package com.Engine.RenderEngine.Shaders.Uniforms;

import static org.lwjgl.opengl.GL20.glUniform2f;

import com.Engine.RenderEngine.Shaders.UniformType;
import com.Engine.Util.Vectors.Vector2f;

public class UniformVec2 extends UniformType<Vector2f> {
	public UniformVec2(String name) { super(name); }
	public void load(Vector2f value) { glUniform2f(location, value.x, value.y); }
	public void load(float x, float y) { glUniform2f(location, x, y); }
}