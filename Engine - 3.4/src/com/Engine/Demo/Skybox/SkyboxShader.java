package com.Engine.Demo.Skybox;

import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformTexture;

public class SkyboxShader extends Shader {
	private static final String VERTEX_SHADER_LOC = "SkyboxShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "SkyboxShader.fsh";

	@Uniform UniformMat4 projectionMatrix;
	@Uniform UniformMat4 viewMatrix;
	
	@Uniform UniformTexture skyTexture;
	
	public SkyboxShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, SkyboxRenderer.class);
	}

	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		
		super.bindFragOutput(0, "skyColour");
	}
}
