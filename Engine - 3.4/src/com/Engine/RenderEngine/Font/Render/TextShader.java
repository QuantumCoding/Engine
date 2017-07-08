package com.Engine.RenderEngine.Font.Render;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformTexture;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformVec4;

public class TextShader extends Shader {
	private static final String VERTEX_SHADER_LOC = "FontShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "FontShader.fsh";
	
	@Uniform UniformMat4 projectionMatrix;
	@Uniform UniformMat4 modelViewMatrix;

	@Uniform UniformVec4 colour;
	@Uniform UniformTexture texture0;
	
	public TextShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, TextRenderer.class);
	}

	@Override
	protected void bindAttributies() {
		bind();
		
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		super.bindAttribute(ATTRIBUTE_LOC_TEXCOORDS, "texCoord_in");
	}
}
