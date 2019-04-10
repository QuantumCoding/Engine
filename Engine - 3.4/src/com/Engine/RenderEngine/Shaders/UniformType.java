package com.Engine.RenderEngine.Shaders;

import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformBuffer;

public abstract class UniformType<E> {
	protected final String name;
	protected int location;
	
	protected UniformBuffer buffer;
	protected int offset;

	public UniformType(String name) { this.name = name; }

	protected void attach(RawShader shader) {
		location = shader.getUniformLocation(name);
		
		if(location == -1) { 
			System.err.println("No uniform named \"" + name + "\" exits for " + shader.getClass().getSimpleName()
				+ "; Shader may not work as intended");
		
		} else postAttach(shader);
	}
	
	protected void assignBuffer(UniformBuffer buffer, int offset) {
		this.buffer = buffer;
		this.offset = offset; 
	}
	
	protected void postAttach(RawShader shader) { }
	
	public abstract void load(E value);
}
