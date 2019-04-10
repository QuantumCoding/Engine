package com.Engine.Demo.Skybox;

import com.Engine.RenderEngine.Shaders.Render.RenderProperties;

public class SkyboxRenderProperties extends RenderProperties {
	public SkyboxRenderProperties() {
		
	}
	
	public RenderProperties clone() { return new SkyboxRenderProperties(); }
}
