package com.Engine.RenderEngine.New_Pipeline.PostProcessing.Plain;

import com.Engine.RenderEngine.New_Pipeline.PostProcessing.ImageShader;

public class PlainShader extends ImageShader {
	private static final String VERTEX_SHADER_LOC = "PlainShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "PlainShader.fsh";

	public PlainShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC);
	}
}
