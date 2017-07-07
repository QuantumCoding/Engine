package com.Engine.RenderEngine.NewShaders;

import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.RenderEngine.Util.Camera;

public interface INewRenderable<T extends RenderProperties> {
	public void render(T properties, Camera camera);
	
	public NewShader getShader();
	public NewModelData getModelData();

	public boolean equals(Object other);
	public int hashCode();
	
	public default int getVAOId() { return getModelData().getVAOId(); }
	public default int getIndiceCount() { return getModelData().getIndiceCount(); }

	public default void bind() { getModelData().bind(); }
	public default void unbind() { getModelData().unbind(); }
	
	public default void cleanUp() { getModelData().cleanUp(); }
}
