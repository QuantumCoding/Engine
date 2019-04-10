package com.Engine.RenderEngine.Util;

import com.Engine.RenderEngine.Shaders.Render.IRenderable;
import com.Engine.RenderEngine.Shaders.Render.RenderProperties;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.System.RenderingSystem;

public class BasicRenderingSystem<M extends IRenderable<? super P>, P extends RenderProperties> extends RenderingSystem<M, P> {
	private boolean supportsTransparance;
	private boolean useAdditiveBlending;
	
	private Shader shader;

	public BasicRenderingSystem(Shader shader) { this(shader, false, false); }
	public BasicRenderingSystem(Shader shader, boolean transparent, boolean additive) { 
		this.shader = shader;
		
		this.supportsTransparance = transparent;
		this.useAdditiveBlending = additive;
	}
	
	public Shader selectShader(RenderLevel level) { return shader; }
	protected void prepareRender(RenderLevel level) { }
	
	public boolean supportsTransparency() { return supportsTransparance; }
	public boolean usesAdditiveBlending() { return useAdditiveBlending; }
}
