package com.Engine.RenderEngine.System;

import com.Engine.RenderEngine.Camera.Camera;
import com.Engine.RenderEngine.System.RenderingSystem.RenderLevel;

public class RenderSceneInfo {
	
	private RenderOrderController renderController;
	private RenderLevel level;
	private Camera camera;
	
	private int depth;
	
	protected RenderSceneInfo(RenderOrderController renderController, RenderLevel level, Camera camera) {
		this(renderController, level, 0, camera);
	}
	
	protected RenderSceneInfo(RenderOrderController renderController, RenderLevel level, int depth, Camera camera) {
		this.renderController = renderController;
		this.level = level;
		this.camera = camera;
		
		this.depth = depth;
	}

	public void render() { renderController.render(this); }
	
	public RenderOrderController getRenderController() { return renderController; }
	public RenderLevel getLevel() { return level; }
	public Camera getCamera() { return camera; }
	
	public int getCycleDepth() { return depth; }
	
	protected int nextCycle() { return ++ depth; }
	protected int reduceCycle() { return -- depth; }
}
