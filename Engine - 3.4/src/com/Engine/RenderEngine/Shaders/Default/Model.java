package com.Engine.RenderEngine.Shaders.Default;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.Render.IRenderable;
import com.Engine.RenderEngine.Shaders.Render.RenderProperties;
import com.Engine.RenderEngine.System.RenderingSystem;
import com.Engine.RenderEngine.System.RenderingSystem.ModelInstance;
import com.Engine.RenderEngine.Textures.Texture2D;

public class Model implements IRenderable<RenderProperties> {
	protected ModelData modelData;
	
	private RenderingSystem<Model, RenderProperties> shader;
	private Texture2D texture;
	
	public Model(ModelData modelData) {
		this.modelData = modelData;
	}
	
	public RenderingSystem<Model, RenderProperties> getShader() { return shader; }
	public Texture2D getTexture() { return texture; }
	
	public void setShader(RenderingSystem<Model, RenderProperties> shader) { this.shader = shader; }
	public void setTexture(Texture2D texture) { this.texture = texture; }
	
	public <T extends RenderProperties> ModelInstance<Model, T> render(T property) {
		return shader.addModel(this, property);
	}
	
// --------------------------------------- Delegation Methods ------------------------------------------------------ \\
	
	public ModelData getModelData() { return modelData; }
	
// ------------------------------------------- Clean-Up ---------------------------------------------------------- \\	
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((modelData == null) ? 0 : modelData.hashCode());
		result = prime * result + ((shader == null) ? 0 : shader.hashCode());
		result = prime * result + ((texture == null) ? 0 : texture.hashCode());
		
		return result;
	}

	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof Model)) return false;
		
		Model other = (Model) obj;
		if(modelData == null) {
			if(other.modelData != null) return false;
		} else if(!modelData.equals(other.modelData)) return false;
		
		if(shader == null) {
			if(other.shader != null) return false;
		} else if(!shader.equals(other.shader)) return false;
		
		if(texture == null) {
			if(other.texture != null) return false;
		} else if(!texture.equals(other.texture)) return false;
		
		return true;
	}
}
