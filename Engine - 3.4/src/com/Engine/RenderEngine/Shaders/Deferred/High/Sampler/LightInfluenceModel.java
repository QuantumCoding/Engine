package com.Engine.RenderEngine.Shaders.Deferred.High.Sampler;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.Default.DefaultRenderProperties;
import com.Engine.RenderEngine.Shaders.Default.Light;
import com.Engine.RenderEngine.Shaders.Render.IRenderable;
import com.Engine.RenderEngine.System.RenderingSystem;
import com.Engine.RenderEngine.System.RenderingSystem.ModelInstance;

public class LightInfluenceModel implements IRenderable<DefaultRenderProperties> {
	private Light light;
	
	private RenderingSystem<LightInfluenceModel, DefaultRenderProperties> shader;
	
	public LightInfluenceModel(Light light) {
		this.light = light;
	}
	
	public Light getLight() { return light; }
	
	public ModelInstance<LightInfluenceModel, DefaultRenderProperties> render(DefaultRenderProperties properties) {
		return shader.addModel(this, properties);
	}
	
	public void setShader(RenderingSystem<LightInfluenceModel, DefaultRenderProperties> shader) { this.shader = shader; }
	public RenderingSystem<LightInfluenceModel, DefaultRenderProperties> getShader() { return shader; }
	public ModelData getModelData() { return null; } // TODO
}
