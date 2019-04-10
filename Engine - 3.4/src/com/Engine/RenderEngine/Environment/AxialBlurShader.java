package com.Engine.RenderEngine.Environment;

import com.Engine.RenderEngine.Shaders.Render.Renderer;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformMat3;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformTexture;

public class AxialBlurShader extends Shader {
	private static final String VERTEX_SHADER_LOC = "AxialBlurShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "AxialBlurShader.fsh";

	@Uniform UniformTexture texture0;
	
	@Uniform UniformMat3 forwardRot;
	@Uniform UniformMat3 backwardRot;
	
	@Uniform UniformMat4 faceRotation;
	
	public AxialBlurShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, null);
	}
	
	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		
		super.bindFragOutput(0, "blur_L1_out");
		super.bindFragOutput(1, "blur_L2_out");
		super.bindFragOutput(2, "blur_L3_out");
		super.bindFragOutput(3, "blur_L4_out");
	}
	
	public Renderer<?, ?, ?> getRenderer() { 
		throw new IllegalAccessError("AxialBlurShader do not have a Renderers! Use " + AxialCubeMapBlur.class + " instead"); 
	}
}
