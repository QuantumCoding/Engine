package com.Engine.RenderEngine.Shaders.Uniforms.Types;

import com.Engine.RenderEngine.Shaders.RawShader;
import com.Engine.RenderEngine.Shaders.UniformType;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformLinker;

public abstract class UniformStruct<E> extends UniformType<E> {
	public UniformStruct(String name) { super(name); }

	public void attach(RawShader shader) {
		UniformLinker.initUniformLocations(getClass().getDeclaredFields(), name + ".", this, shader);
		super.postAttach(shader);
	}
}
