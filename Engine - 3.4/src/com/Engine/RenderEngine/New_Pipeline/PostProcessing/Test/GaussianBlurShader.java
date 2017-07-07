package com.Engine.RenderEngine.New_Pipeline.PostProcessing.Test;

import com.Engine.RenderEngine.NewShaders.Uniforms.Uniform;
import com.Engine.RenderEngine.NewShaders.Uniforms.UniformFloat;
import com.Engine.RenderEngine.NewShaders.Uniforms.UniformVec2;
import com.Engine.RenderEngine.New_Pipeline.PostProcessing.ImageShader;

public class GaussianBlurShader extends ImageShader {
	private static final String VERTEX_SHADER_LOC = "GaussianBlurShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "GaussianBlurShader.fsh";

	@Uniform UniformVec2 axis;
	@Uniform UniformFloat size;
	
	public GaussianBlurShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC);
	}
}
