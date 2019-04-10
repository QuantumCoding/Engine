package com.Engine.RenderEngine.Shaders.Deferred.Low;

import java.util.Collection;
import java.util.Iterator;

import com.Engine.RenderEngine.Shaders.Deferred.Lights.Light;
import com.Engine.RenderEngine.Shaders.Deferred.Lights.UniformDeferredLight;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformFloat;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformInt;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformTexture;

public class LowForwardShader extends Shader { 
	private static final String VERTEX_SHADER_LOC = "LowForwardShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "LowForwardShader.fsh";

	private static final int MAX_LIGHTS = 1;
	
	@Uniform UniformMat4 transformationMatrix;
	@Uniform UniformMat4 projectionMatrix;
	@Uniform UniformMat4 viewMatrix;
	
	@Uniform UniformFloat exposure;
	@Uniform UniformInt lightCount;
	@Uniform(size = MAX_LIGHTS) UniformDeferredLight[] lights;
	
	@Uniform(index = 0) UniformTexture albedoMap;
	
	public LowForwardShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, LowForwardRenderer.class);
	}

	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		super.bindAttribute(ATTRIBUTE_LOC_TEXCOORDS, "texCoordIN");
		super.bindAttribute(ATTRIBUTE_LOC_NORMALS,   "normal");
	}
	
	protected void loadLights(Collection<Light> lights) {
		int count = Math.min(MAX_LIGHTS, lights.size());
		lightCount.load(count);
		
		Iterator<Light> iter = lights.iterator();
		for(int i = 0; i < count; i ++) {
			this.lights[i].load(iter.next());
		}
	}
}
