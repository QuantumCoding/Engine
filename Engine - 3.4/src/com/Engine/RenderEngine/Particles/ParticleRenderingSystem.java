package com.Engine.RenderEngine.Particles;

import com.Engine.RenderEngine.Camera.Camera;
import com.Engine.RenderEngine.Particles.Render.ParticleInstanceRender;
import com.Engine.RenderEngine.Particles.Render.ParticleRenderProperties;
import com.Engine.RenderEngine.Particles.Render.ParticleShader;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.System.RenderingSystem;

public class ParticleRenderingSystem extends RenderingSystem<ParticleInstanceRender, ParticleRenderProperties> {
	public static final ParticleShader ParticleShader = new ParticleShader();
	
	public Shader selectShader(RenderLevel level) {		  // TODO: Should also have a different or no render at Medium or High 
		return ParticleShader;
	}

	protected void prepareRender(RenderLevel level) { }
	
	public boolean supportsTransparency() { return true; }
	public boolean usesAdditiveBlending() { return true; } // TODO: Should Split into Two Render Passes For Particles
	public float getZLevel(Camera camera) { return 1000; } // 		One with Additive BLending and the other Without
}														   //		Because this is the only Transparent Render this works
