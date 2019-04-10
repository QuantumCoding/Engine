package com.Engine.RenderEngine.Shaders.Deferred;

import com.Engine.RenderEngine.Shaders.Render.RenderProperties;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;

public class DeferredRenderProperties extends RenderProperties {
	private int environmentLayer;
	
	public DeferredRenderProperties(Transform transform) { this(transform, 0); }
	public DeferredRenderProperties(Transform transform, int environmentLayer) {
		super(transform);
		
		if(environmentLayer < 0) throw new IllegalArgumentException("Enviorment Layer must be >= 0; not " + environmentLayer);
		this.environmentLayer = environmentLayer;
	}
	
	public int getEnvironmentLayer() { return environmentLayer; }
	
	public RenderProperties clone() { return new DeferredRenderProperties(getTransform(), environmentLayer); }
}
