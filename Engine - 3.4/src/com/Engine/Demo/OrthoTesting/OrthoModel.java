package com.Engine.Demo.OrthoTesting;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.IRenderable;
import com.Engine.RenderEngine.Shaders.Shader;

public class OrthoModel implements IRenderable<OrthoRenderProperties> {
	public static final OthoShader OTHO_SHADER = new OthoShader();
	public static final OrthoRenderer RENDERER = (OrthoRenderer) OTHO_SHADER.getRenderer();
	private static final ModelData MODEL_DATA = new ModelData();
	
	static {
		MODEL_DATA.storeDataInAttributeList(OthoShader.ATTRIBUTE_LOC_POSITIONS, 2, new float[] {
				-0.5f,  0.5f,	-0.5f, -0.5f,
				 0.5f, -0.5f,	 0.5f,  0.5f,
		}, false);
		
		MODEL_DATA.loadIndicies(new int[] {
				0, 1, 2,	0, 2, 3 
		});
	}
	
	public void render(OrthoRenderProperties properties) {
		RENDERER.addModel(this, properties);
	}

	public Shader getShader() { return OTHO_SHADER; }
	public ModelData getModelData() { return MODEL_DATA; }
}
