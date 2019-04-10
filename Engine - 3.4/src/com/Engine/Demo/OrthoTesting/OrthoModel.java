package com.Engine.Demo.OrthoTesting;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage;
import com.Engine.RenderEngine.Shaders.Render.IRenderable;
import com.Engine.RenderEngine.System.RenderingSystem;
import com.Engine.RenderEngine.System.RenderingSystem.ModelInstance;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.BasicRenderingSystem;

public class OrthoModel implements IRenderable<OrthoRenderProperties> {
	public static final OthoShader OTHO_SHADER = new OthoShader();
	public static final OrthoRenderer RENDERER = (OrthoRenderer) OTHO_SHADER.getRenderer();
	
	private static final RenderingSystem<OrthoModel, OrthoRenderProperties> SYSTEM = new BasicRenderingSystem<>(OTHO_SHADER);
	private static final ModelData MODEL_DATA = new ModelData();
	
	static {
		MODEL_DATA.storeDataInAttributeList(OthoShader.ATTRIBUTE_LOC_POSITIONS, 2, new float[] {
				0, 0,	0, 1,
				1, 0, 	1, 1,
		}, BufferUsage.Static_Draw);
	}
	
	private Texture2D texture;
	
	public OrthoModel(Texture2D texture) {
		this.texture = texture;
	}
	
	
	public void setTexture(Texture2D texture) { this.texture = texture; }
	public Texture2D getTexture() { return texture; }

	public ModelInstance<OrthoModel, OrthoRenderProperties> render(OrthoRenderProperties properties) {
		return OTHO_SHADER.addModel(this, properties);
	}

	public RenderingSystem<OrthoModel, OrthoRenderProperties> getShader() { return SYSTEM; }
	public ModelData getModelData() { return MODEL_DATA; }
}
