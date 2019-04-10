package com.Engine.RenderEngine.Shaders.Uniforms.Types;

import static org.lwjgl.opengl.GL20.glUniform4f;

import com.Engine.RenderEngine.Shaders.UniformType;
import com.Engine.Util.Vectors.Vector3f;
import com.Engine.Util.Vectors.Vector4f;

public class UniformVec4 extends UniformType<Vector4f> {
	public UniformVec4(String name) { super(name); }

	public void load(Vector3f value, float w) { load(value.x, value.y, value.z, w); }
	public void load(Vector4f value) { load(value.x, value.y, value.z, value.w); }
	
	public void load(float x, float y, float z, float w) {  
		if(buffer != null) buffer.loadFloat(offset, x, y, z, w);
		else glUniform4f(location, x, y, z, w); 
	}
}