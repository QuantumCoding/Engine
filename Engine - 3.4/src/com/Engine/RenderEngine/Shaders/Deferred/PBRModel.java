package com.Engine.RenderEngine.Shaders.Deferred;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.Render.IRenderable;
import com.Engine.RenderEngine.Shaders.Render.RenderProperties;
import com.Engine.RenderEngine.System.RenderingSystem;
import com.Engine.RenderEngine.System.RenderingSystem.ModelInstance;
import com.Engine.RenderEngine.Textures.Texture2D;

public class PBRModel implements IRenderable<RenderProperties> {
	private Texture2D albedo, normal;
	private Texture2D occlusion, metalness, roughness;
	
	private ModelData modelData;
	private RenderingSystem<PBRModel, RenderProperties> shader;
	
	public PBRModel(
			ModelData modelData, 
			Texture2D albedo, Texture2D normal, 
			Texture2D occlusion, Texture2D metalness, Texture2D roughness
	) {
		this.albedo = albedo;
		this.normal = normal;
		
		this.occlusion = occlusion;
		this.metalness = metalness;
		this.roughness = roughness;
		
		this.modelData = modelData;
	}

	public <T extends RenderProperties> ModelInstance<PBRModel, T> render(T properties) {
		return shader.addModel(this, properties);
	}

	public Texture2D getAlbedoMap() { return albedo; }
	public Texture2D getNormalMap() { return normal; }

	public Texture2D getOcclusionMap() { return occlusion; }
	public Texture2D getMetalnessMap() { return metalness; }
	public Texture2D getRoughnessMap() { return roughness; }

	@SuppressWarnings("unchecked")
	public void setShader(RenderingSystem<PBRModel, ? extends RenderProperties> shader) { 
		this.shader = (RenderingSystem<PBRModel, RenderProperties>) shader; 
	}
	
	public RenderingSystem<PBRModel, RenderProperties> getShader() { return shader; }
	public ModelData getModelData() { return modelData; }
}
