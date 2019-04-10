package com.Engine.RenderEngine.Shaders.Deferred.High;

import com.Engine.RenderEngine.Models.ModelData.Attribute;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformInt;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformTexture;

public class DeferredShader extends Shader { 
	private static final String VERTEX_SHADER_LOC = "DeferredShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "DeferredShader.fsh";
	
	public static final Attribute ATTRIBUTE_LOC_TANGENT = nextAttribId(DeferredShader.class);

//	@Uniform UniformVec3 skyColor;		// TODO: Research Advanced Fog テクニクス
//	@Uniform UniformFloat fogDensity;
//	@Uniform UniformFloat fogGradient;

//	@Uniform UniformFloat numberOfRows; // TODO: Maybe...
//	@Uniform UniformVec2 offset;
	
	@Uniform UniformMat4 transformationMatrix;
	@Uniform UniformMat4 projectionMatrix;
	@Uniform UniformMat4 viewMatrix;
	
	@Uniform UniformInt environmentLayer;
	
	@Uniform(index = 0) UniformTexture albedoMap;
	@Uniform(index = 1) UniformTexture normalMap;
	@Uniform(index = 2) UniformTexture occlusionMap;
	@Uniform(index = 3) UniformTexture metalnessMap;
	@Uniform(index = 4) UniformTexture roughnessMap;
	
	public DeferredShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, DeferredRenderer.class);
	}

	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		super.bindAttribute(ATTRIBUTE_LOC_TEXCOORDS, "texCoordIN");
		super.bindAttribute(ATTRIBUTE_LOC_NORMALS,   "normal");
		super.bindAttribute(ATTRIBUTE_LOC_TANGENT,   "tangent");
		
		super.bindFragOutput(0, "position");
		super.bindFragOutput(1, "normal");
		super.bindFragOutput(2, "albedo");
		super.bindFragOutput(3, "pbrData");
	}
}
