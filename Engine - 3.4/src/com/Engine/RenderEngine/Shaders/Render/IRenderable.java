package com.Engine.RenderEngine.Shaders.Render;

import java.util.Collection;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.System.RenderingSystem;
import com.Engine.RenderEngine.System.RenderingSystem.ModelInstance;
import com.Engine.RenderEngine.System.Dependencies.IRenderDependencyBuilder;

public interface IRenderable<T extends RenderProperties> {
	public <P extends T> ModelInstance<? extends IRenderable<T>, ? extends P> render(P properties);
	
	public RenderingSystem<? extends IRenderable<T>, T> getShader();
	public ModelData getModelData();

	public boolean equals(Object other);
	public int hashCode();
	
	public default int getVAOId() { return getModelData().getVAOId(); }
	public default int getIndiceCount() { return getModelData().getIndiceCount(); }

	public default void bind() { getModelData().bind(); }
	public default void unbind() { getModelData().unbind(); }
	
	public default void cleanUp() { getModelData().cleanUp(); }
	
	public default Collection<IRenderDependencyBuilder<?>> getDependencies() { return null; }
}
