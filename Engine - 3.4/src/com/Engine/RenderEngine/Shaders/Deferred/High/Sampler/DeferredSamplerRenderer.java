package com.Engine.RenderEngine.Shaders.Deferred.High.Sampler;

import com.Engine.RenderEngine.New_Pipeline.PostProcessing.ImageRenderer;
import com.Engine.RenderEngine.Shaders.Default.DefaultRenderProperties;
import com.Engine.RenderEngine.Shaders.Render.Renderer;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Textures.Texture;
import com.Engine.RenderEngine.Textures.Texture2D;

public class DeferredSamplerRenderer extends Renderer<LightInfluenceModel, DefaultRenderProperties, DeferredSamplerShader> {
	public DeferredSamplerRenderer(Shader shader) {
		super(shader);
		usingFrustumCulling(false);
	}
	
	private Texture positionTextue, normalTexture, albedoTexture, pbrDataTexture;
	
	public void assignDataMaps(Texture positionMap, Texture normalMap, Texture albedoMap, Texture pbrDataMap) {
		positionTextue = positionMap;
		normalTexture  = normalMap;
		albedoTexture  = albedoMap;
		pbrDataTexture = pbrDataMap;
	}

	protected void prepareOpenGL() {
		shader.bind();
		
		shader.cameraPos.load(camera.getPosition());
		
		shader.positionMap.load(positionTextue);
		shader.normalMap.load(normalTexture);
		shader.albedoMap.load(albedoTexture);
		shader.pbrDataMap.load(pbrDataTexture);
		
		ImageRenderer.prepOpenGL();
	}

	protected void bindModel(LightInfluenceModel model) {
//		model.bind();
		shader.lightIN.load(model.getLight());
	}

	protected void renderModel(LightInfluenceModel model, DefaultRenderProperties properties) {
		// RenderLight
		
		ImageRenderer.drawImage(null, true);
	}

	protected void unbindModel(LightInfluenceModel model) {
//		model.unbind();
	}

	protected void revertOpenGL() {
		ImageRenderer.revertOpenGL();
		
		Texture2D.unbind_2D(0);
		Texture2D.unbind_2D(1);
		Texture2D.unbind_2D(2);
		Texture2D.unbind_2D(3);
		
		Shader.unbind();
	}
}
