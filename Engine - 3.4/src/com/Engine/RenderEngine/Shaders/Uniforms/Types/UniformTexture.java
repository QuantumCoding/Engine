package com.Engine.RenderEngine.Shaders.Uniforms.Types;

import static org.lwjgl.opengl.GL20.glUniform1i;

import com.Engine.RenderEngine.Shaders.RawShader;
import com.Engine.RenderEngine.Shaders.UniformType;
import com.Engine.RenderEngine.Textures.Texture;

public class UniformTexture extends UniformType<Texture> {
	private int index;
	
	public UniformTexture(String name, int index) { super(name); this.index = index; }
	public void load(Texture value) { value.bind(index); }

	protected void postAttach(RawShader shader) { glUniform1i(location, index); } 
}
