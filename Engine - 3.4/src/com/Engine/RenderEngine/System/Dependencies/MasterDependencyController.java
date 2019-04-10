package com.Engine.RenderEngine.System.Dependencies;

import java.util.HashMap;
import java.util.NoSuchElementException;

import com.Engine.RenderEngine.System.RenderingSystem;
import com.Engine.RenderEngine.System.RenderingSystem.RenderLevel;

public class MasterDependencyController {
	private HashMap<Class<?>, IRenderDependencyController<?>> controllers;
//	private HashMap<Class<?>, IRenderDependencyController<?>> controllersByDependency;
	
	public MasterDependencyController() {
		controllers = new HashMap<>();
//		controllersByDependency = new HashMap<>();
	}
	
	public void redgisterController(IRenderDependencyController<?> controller) {
		controllers.put(controller.getClass(), controller);
//		controllersByDependency.put(controller.get, controller);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IRenderDependency> T provideDependency(RenderLevel level, IRenderDependencyBuilder<T> builder) {
		IRenderDependencyController<T> control = (IRenderDependencyController<T>) controllers.get(builder.getControllerType());
		if(control == null) throw new NoSuchElementException(
				"There is no Controller Registared for: " + builder.getControllerType().getSimpleName());
		
		return control.provideDependency(level, builder);
	}
}
