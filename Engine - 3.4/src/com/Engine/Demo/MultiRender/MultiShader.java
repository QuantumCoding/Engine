package com.Engine.Demo.MultiRender;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformTexture;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformVec2;

public class MultiShader extends Shader {
	
	private static final String VERTEX_SHADER_LOC = "MultiShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "MultiShader.fsh";

    @Uniform UniformVec2 scale;
    @Uniform UniformMat4 projectionMatrix;
    @Uniform UniformMat4 modelViewMatrix;
    @Uniform(index=0) UniformTexture texture0;
    @Uniform(index=1) UniformTexture texture1;
	
	public MultiShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, MultiRenderer.class);
	}

	@Override
	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		super.bindAttribute(ATTRIBUTE_LOC_TEXCOORDS, "texCoord");
	}
	
	public void scale(float x, float y) { bind(); scale.load(x, y); }
}
