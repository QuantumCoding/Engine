package com.Engine.RenderEngine.Shaders.Uniforms.Types;

import static org.lwjgl.opengl.GL20.glUniform3f;

import com.Engine.RenderEngine.Shaders.UniformType;
import com.Engine.Util.Vectors.Vector3f;

public class UniformVec3 extends UniformType<Vector3f> {
	public UniformVec3(String name) { super(name); }
	
	public void load(Vector3f value) { load(value.x, value.y, value.z); }
	public void load(float x, float y, float z) {  
		if(buffer != null) buffer.loadFloat(offset, x, y, z);
		else glUniform3f(location, x, y, z);
	}
}