package com.Engine.RenderEngine.New_Pipeline.PostProcessing;

import com.Engine.RenderEngine.Textures.Texture;

public abstract class PostProcessingEffect { 
	protected abstract Texture apply(Texture texture);
}
