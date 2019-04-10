package com.Engine.RenderEngine.New_Pipeline.PostProcessing;

import com.Engine.RenderEngine.Shaders.Render.Renderer;
import com.Engine.RenderEngine.Shaders.Render.Shader;

public abstract class ImageShader extends Shader {
	public ImageShader(String vsFilePath, String fsFilePath) {
		super(vsFilePath, fsFilePath, null);
	}

	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
	}
	
	public Renderer<?, ?, ?> getRenderer() { 
		throw new IllegalAccessError("ImageShaders do not have Renderers! Use " + ImageRenderer.class + " instead"); 
	}
}
