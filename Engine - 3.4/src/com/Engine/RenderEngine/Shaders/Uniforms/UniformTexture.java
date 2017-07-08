package com.Engine.RenderEngine.Shaders.Uniforms;

import static org.lwjgl.opengl.GL20.glUniform1i;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.UniformType;
import com.Engine.RenderEngine.Textures.Texture;

public class UniformTexture extends UniformType<Texture> {
	private int index;
	
	public UniformTexture(String name, Integer index) { super(name); this.index = index; }
	public void load(Texture value) { value.bind(index); }
	
	public void attach(Shader shader) { super.attach(shader); glUniform1i(location, index); } 
}