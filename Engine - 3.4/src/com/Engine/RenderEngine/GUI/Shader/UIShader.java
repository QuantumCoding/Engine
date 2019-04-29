package com.Engine.RenderEngine.GUI.Shader;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformTexture;

public class UIShader extends Shader {
	private static final String VERTEX_SHADER_LOC = "UIShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "UIShader.fsh";

	@Uniform UniformMat4 textureMatrix;
	@Uniform UniformMat4 transformationMatrix;
    @Uniform UniformMat4 othographicMatrix;
    
    @Uniform UniformTexture texture0;
	
	public UIShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, UIRenderer.class);
	}

	@Override
	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		super.bindAttribute(ATTRIBUTE_LOC_TEXCOORDS, "texCoord_in");
	}
}
