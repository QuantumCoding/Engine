package com.Engine.Demo.MultiRenderNew;

import com.Engine.RenderEngine.NewShaders.NewShader;
import com.Engine.RenderEngine.NewShaders.Uniforms.Uniform;
import com.Engine.RenderEngine.NewShaders.Uniforms.UniformMat4;
import com.Engine.RenderEngine.NewShaders.Uniforms.UniformTexture;

public class MultiShader extends NewShader {
	
	private static final String VERTEX_SHADER_LOC = "MultiShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "MultiShader.fsh";

    @Uniform protected UniformMat4 projectionMatrix;
    @Uniform protected UniformMat4 modelViewMatrix;
    @Uniform(index=1) protected UniformTexture texture0;
    @Uniform(index=2) protected UniformTexture texture1;
	
	public MultiShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, MultiRenderer.class);
	}

	@Override
	protected void bindAttributies() {
		bind();
		
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		super.bindAttribute(ATTRIBUTE_LOC_TEXCOORDS, "texCoord");
	}
}
