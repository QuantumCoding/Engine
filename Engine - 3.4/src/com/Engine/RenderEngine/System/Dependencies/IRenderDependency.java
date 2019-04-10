package com.Engine.RenderEngine.System.Dependencies;

import com.Engine.RenderEngine.System.RenderSceneInfo;

public interface IRenderDependency {
	public boolean render(RenderSceneInfo sceneInfo);
	public int getMaxCycleDepth();
}
