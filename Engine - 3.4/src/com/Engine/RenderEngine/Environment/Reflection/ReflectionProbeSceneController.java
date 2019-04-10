package com.Engine.RenderEngine.Environment.Reflection;

import com.Engine.RenderEngine.System.RenderingSystem.RenderLevel;
import com.Engine.RenderEngine.System.Dependencies.IRenderDependencyBuilder;
import com.Engine.RenderEngine.System.Dependencies.IRenderDependencyController;

public class ReflectionProbeSceneController implements IRenderDependencyController<ReflectionProbe> {
	private static final int MAX_PROBE_COUNT = 1;

	public ReflectionProbe provideDependency(RenderLevel level, IRenderDependencyBuilder<ReflectionProbe> builderGeneric) {
//		ReflectionProbeBuilder builder = (ReflectionProbeBuilder) builderGeneric;
//		
//		builder.get
		
		return null;
	}

	public void removeDependency(ReflectionProbe dependency) {
		
	}

	public void removeAllDependencies() {
		
	}
}
