package com.Engine.RenderEngine.Shaders.Uniforms.Types;

import static org.lwjgl.opengl.GL20.glUniform2f;

import com.Engine.RenderEngine.Shaders.UniformType;
import com.Engine.Util.Vectors.Vector2f;

public class UniformVec2 extends UniformType<Vector2f> {
	public UniformVec2(String name) { super(name); }
	
	public void load(Vector2f value) { load(value.x, value.y); }
	public void load(float x, float y) {  
		if(buffer != null) buffer.loadFloat(offset, x, y);
		else glUniform2f(location, x, y); 
	}
}