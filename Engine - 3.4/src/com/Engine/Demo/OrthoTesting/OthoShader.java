package com.Engine.Demo.OrthoTesting;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformMat4;

public class OthoShader extends Shader {

	private static final String VERTEX_SHADER_LOC = "OrthoShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "OrthoShader.fsh";

	@Uniform UniformMat4 transformationMatrix;
    @Uniform UniformMat4 othographicMatrix;
	
	public OthoShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, OrthoRenderer.class);
	}

	@Override
	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
	}

}
