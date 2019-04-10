package com.Engine.RenderEngine.Shaders.Deferred.High.Sampler;

import com.Engine.RenderEngine.Shaders.Default.Light;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformStruct;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformTexture;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformVec3;
import com.Engine.Util.Vectors.Vector3f;

public class DeferredSamplerShader extends Shader {
	private static final String VERTEX_SHADER_LOC = "DeferredSamplerShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "DeferredSamplerShader.fsh";

	public static class UniformLight extends UniformStruct<Light> {
		public UniformLight(String name) { super(name); }

		@Uniform UniformVec3 color;
		@Uniform UniformVec3 position;
		@Uniform UniformVec3 attenuation;
		
		public void load(Light light) {
			if(light  == null) {
				color.load(new Vector3f());
				position.load(new Vector3f());
				attenuation.load(new Vector3f(1, 0, 0));
			} else {
				color.load(light.getColor());
				position.load(light.getPosition());
				attenuation.load(light.getAttenuation());
			}
		}
	}
	
	@Uniform UniformVec3  cameraPos;
	@Uniform UniformLight lightIN;
	
	@Uniform(index = 0) UniformTexture positionMap;
	@Uniform(index = 1) UniformTexture normalMap;
	@Uniform(index = 2) UniformTexture albedoMap;
	@Uniform(index = 3) UniformTexture pbrDataMap;
	
	public DeferredSamplerShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, DeferredSamplerRenderer.class);
	}

	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
	}
}
