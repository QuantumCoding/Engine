package com.Engine.RenderEngine.Shaders.Default;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.IRenderable;
import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Textures.Texture2D;

public class Model implements IRenderable<RenderProperties> {
	protected ModelData modelData;
	
	private Shader shader;
	private Texture2D texture;
	private Renderer<Model, RenderProperties, ?> renderer;
	
	public Model(ModelData modelData) {
		this.modelData = modelData;
	}
	
	public Shader getShader() { return shader; }
	public Renderer<Model, RenderProperties, ?> getRenderer() { return renderer; }
	public Texture2D getTexture() { return texture; }
	
	@SuppressWarnings("unchecked")
	public void setShader(Shader shader) {
		this.shader = shader;
		this.renderer = (Renderer<Model, RenderProperties, ?>) shader.getRenderer();
	}
	
	public void setTexture(Texture2D texture) { this.texture = texture; }
	
	public void render(RenderProperties property) {
		renderer.addModel(this, property);
	}
	
// --------------------------------------- Delegation Methods ------------------------------------------------------ \\
	
	public ModelData getModelData() { return modelData; }
	
// ------------------------------------------- Clean-Up ---------------------------------------------------------- \\	
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((modelData == null) ? 0 : modelData.hashCode());
		result = prime * result + ((renderer == null) ? 0 : renderer.hashCode());
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
		
		if(renderer == null) {
			if(other.renderer != null) return false;
		} else if(!renderer.equals(other.renderer)) return false;
		
		if(shader == null) {
			if(other.shader != null) return false;
		} else if(!shader.equals(other.shader)) return false;
		
		if(texture == null) {
			if(other.texture != null) return false;
		} else if(!texture.equals(other.texture)) return false;
		
		return true;
	}
}
