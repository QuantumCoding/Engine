package com.Engine.RenderEngine.System.Dependencies;

public interface IRenderDependencyBuilder<T extends IRenderDependency> {
	public T build();
	
	public Class<? extends IRenderDependencyController<T>> getControllerType();
}
