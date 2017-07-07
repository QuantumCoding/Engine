package com.Engine.RenderEngine.NewShaders;

import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.Camera;

public class NewTestModel implements INewRenderable<RenderProperties> {
	protected NewModelData modelData;
	
	private NewShader shader;
	private Texture2D texture;
	private NewRenderer<NewTestModel, RenderProperties, ?> renderer;
	
	public NewTestModel(NewModelData modelData) {
		this.modelData = modelData;
	}
	
	public NewShader getShader() { return shader; }
	public NewRenderer<NewTestModel, RenderProperties, ?> getRenderer() { return renderer; }
	public Texture2D getTexture() { return texture; }
	
	@SuppressWarnings("unchecked")
	public void setShader(NewShader shader) {
		this.shader = shader;
		this.renderer = (NewRenderer<NewTestModel, RenderProperties, ?>) shader.getRenderer();
	}
	
	public void setTexture(Texture2D texture) { this.texture = texture; }
	
	public void render(RenderProperties property, Camera camera) {
		renderer.addModel(this, property, camera);
	}
	
// --------------------------------------- Delegation Methods ------------------------------------------------------ \\
	
	public NewModelData getModelData() { return modelData; }
	
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
		if(!(obj instanceof NewTestModel)) return false;
		
		NewTestModel other = (NewTestModel) obj;
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
