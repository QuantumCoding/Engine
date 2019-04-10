package com.Engine.RenderEngine.System.Dependencies;

import com.Engine.RenderEngine.System.RenderingSystem.RenderLevel;

public interface IRenderDependencyController<T extends IRenderDependency> {
	public T provideDependency(RenderLevel level, IRenderDependencyBuilder<T> builder);
	
	public void removeDependency(T dependency);
	public void removeAllDependencies();
}
