package com.Engine.Demo.MultiRender;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformTexture;

public class MultiShader extends Shader {
	
	private static final String VERTEX_SHADER_LOC = "MultiShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "MultiShader.fsh";

    @Uniform protected UniformMat4 projectionMatrix;
    @Uniform protected UniformMat4 modelViewMatrix;
    @Uniform(index=0) protected UniformTexture texture0;
    @Uniform(index=1) protected UniformTexture texture1;
	
	public MultiShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, MultiRenderer.class);
	}

	@Override
	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		super.bindAttribute(ATTRIBUTE_LOC_TEXCOORDS, "texCoord");
	}
}
