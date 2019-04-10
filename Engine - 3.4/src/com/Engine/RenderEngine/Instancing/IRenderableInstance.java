package com.Engine.RenderEngine.Instancing;

import com.Engine.RenderEngine.Shaders.Render.IRenderable;
import com.Engine.RenderEngine.Shaders.Render.RenderProperties;

public interface IRenderableInstance<T extends RenderProperties> extends IRenderable<T> {
	public int getInstanceLength();
	public int getInstanceCount();
	
	public void addInstanceAttributes(InstanceVBO vbo);
}
