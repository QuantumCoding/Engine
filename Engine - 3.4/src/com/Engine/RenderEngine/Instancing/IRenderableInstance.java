package com.Engine.RenderEngine.Instancing;

import com.Engine.RenderEngine.Shaders.IRenderable;
import com.Engine.RenderEngine.Shaders.RenderProperties;

public interface IRenderableInstance<T extends RenderProperties> extends IRenderable<T> {
	public int getInstanceLength();
	public int getInstanceCount();
	
	public void addInstanceAttributes(InstanceVBO vbo);
}
