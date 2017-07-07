package com.Engine.RenderEngine.New_Pipeline.PostProcessing;

import com.Engine.RenderEngine.NewShaders.NewRenderer;
import com.Engine.RenderEngine.NewShaders.NewShader;

public abstract class ImageShader extends NewShader {
	public ImageShader(String vsFilePath, String fsFilePath) {
		super(vsFilePath, fsFilePath, null);
	}

	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
	}
	
	public NewRenderer<?, ?, ?> getRenderer() { 
		throw new IllegalAccessError("ImageShaders do not have Renderers! Use " + ImageRenderer.class + " instead"); 
	}
}
