package com.Engine.RenderEngine.Shaders.Deferred.High.Compute;

import static com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformImage.AccessQualifier.WRITE_ONLY;

import com.Engine.RenderEngine.Shaders.ComputeShader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformFloat;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformImage;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformInt;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformTexture;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformVec2;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformVec3;

public class DeferredComputeShader extends ComputeShader {
	private static final String COMPUTE_SHADER_LOC = "DeferredComputeShader.csh";
	

	public @Uniform UniformBufferLights Lights;
	public @Uniform UniformVec3 cameraPos;
	public @Uniform UniformFloat exposure;

	public @Uniform(index = 0) UniformTexture positionMap;
	public @Uniform(index = 1) UniformTexture normalMap;
	public @Uniform(index = 2) UniformTexture albedoMap;
	public @Uniform(index = 3) UniformTexture pbrDataMap;

	public @Uniform(index = 4) UniformTexture environmentMap;
	public @Uniform	UniformInt environmentMapSudoLayerCap;
	
	public @Uniform UniformMat4 viewMatrix;
	public @Uniform UniformMat4 projectionMatrix;
	public @Uniform UniformVec2 framebufferDim;

	public @Uniform(index = 0, access = WRITE_ONLY) UniformImage outTexture;
	
	public DeferredComputeShader() {
		super(COMPUTE_SHADER_LOC);
	}

	public void execute(int sizeX, int sizeY, int sizeZ) {
		disbatch(sizeX, sizeY, sizeZ);
	}
}
