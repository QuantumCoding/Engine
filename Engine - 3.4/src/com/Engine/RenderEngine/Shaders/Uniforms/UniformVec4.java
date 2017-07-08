package com.Engine.RenderEngine.Shaders.Uniforms;

import static org.lwjgl.opengl.GL20.glUniform4f;

import com.Engine.RenderEngine.Shaders.UniformType;
import com.Engine.Util.Vectors.Vector4f;

public class UniformVec4 extends UniformType<Vector4f> {
	public UniformVec4(String name) { super(name); }
	public void load(Vector4f value) { glUniform4f(location, value.x, value.y, value.z, value.w); }
	public void load(float x, float y, float z, float w) { glUniform4f(location, x, y, z, w); }
}