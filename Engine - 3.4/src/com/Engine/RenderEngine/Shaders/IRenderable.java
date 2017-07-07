package com.Engine.RenderEngine.Shaders;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Util.Camera;

public interface IRenderable<T extends RenderProperties> {
	public void render(T properties, Camera camera);
	
	public Shader getShader();
	public ModelData getModelData();

	public boolean equals(Object other);
	public int hashCode();
	
	public default int getVAOId() { return getModelData().getVAOId(); }
	public default int getIndiceCount() { return getModelData().getIndiceCount(); }
}
