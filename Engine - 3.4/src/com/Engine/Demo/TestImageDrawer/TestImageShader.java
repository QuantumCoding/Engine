package com.Engine.Demo.TestImageDrawer;

import com.Engine.RenderEngine.New_Pipeline.PostProcessing.ImageShader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformTexture;

public class TestImageShader extends ImageShader {
	private static final String VERTEX_SHADER_LOC = "TestImageShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "TestImageShader.fsh";

	@Uniform UniformTexture texture0;
	
	public TestImageShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC);
	}
}
