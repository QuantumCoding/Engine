package com.Engine.RenderEngine.New_Pipeline.PostProcessing;

import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.Textures.Texture;

public class PostProcessingPipeline {
	private PostProcessingEffect[] effects;
	
	public PostProcessingPipeline(PostProcessingEffect... effects) {
		this.effects = effects;
	}
	
	public void process(Texture source, FBO dest) {
		ImageRenderer.prepOpenGL();
		
		for(PostProcessingEffect effect : effects)
			source = effect.apply(source);
		
		
		
		ImageRenderer.revertOpenGL();
	}
}
