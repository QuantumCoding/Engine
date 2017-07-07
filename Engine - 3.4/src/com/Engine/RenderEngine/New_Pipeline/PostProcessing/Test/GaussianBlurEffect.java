package com.Engine.RenderEngine.New_Pipeline.PostProcessing.Test;

import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.Attachment;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderTexture;
import com.Engine.RenderEngine.New_Pipeline.PostProcessing.ImageRenderer;
import com.Engine.RenderEngine.New_Pipeline.PostProcessing.ImageShader;
import com.Engine.RenderEngine.New_Pipeline.PostProcessing.PostProcessingEffect;
import com.Engine.RenderEngine.Textures.Texture;

public class GaussianBlurEffect extends PostProcessingEffect {
	private static final GaussianBlurShader BLUR_SHADER = new GaussianBlurShader();
	private FBO fbo;
	
	public GaussianBlurEffect(int width, int height) {
//		if(steps < 1) throw new IllegalArgumentException("GaussianBlur must have at least 1 iteration, not " + steps);
		
		fbo = new FBO(width, height);
		fbo.attach(new RenderTexture(fbo), Attachment.ColourBuffer);
	}
	
	public Texture apply(Texture texture) {
		BLUR_SHADER.bind(); fbo.bindDraw();
			BLUR_SHADER.axis.load(1, 0);
			BLUR_SHADER.size.load((float) fbo.getWidth());
			ImageRenderer.drawImage(texture);
			
			BLUR_SHADER.axis.load(0, 1);
			BLUR_SHADER.size.load((float) fbo.getHeight());
			ImageRenderer.drawImage((RenderTexture) fbo.getAttachment(Attachment.ColourBuffer));
		ImageShader.unbind(); FBO.unbindDraw();
		
		return (RenderTexture) fbo.getAttachment(Attachment.ColourBuffer);
	}
	
	public FBO getFBO() { return fbo; }
}
