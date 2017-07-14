package com.Engine.RenderEngine.Shaders;

import com.Engine.RenderEngine.Models.ModelData.ModelData;

public interface IRenderable<T extends RenderProperties> {
	public void render(T properties);
	
	public Shader getShader();
	public ModelData getModelData();

	public boolean equals(Object other);
	public int hashCode();
	
	public default int getVAOId() { return getModelData().getVAOId(); }
	public default int getIndiceCount() { return getModelData().getIndiceCount(); }

	public default void bind() { getModelData().bind(); }
	public default void unbind() { getModelData().unbind(); }
	
	public default void cleanUp() { getModelData().cleanUp(); }
}
