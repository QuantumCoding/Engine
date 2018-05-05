package com.Engine.RenderEngine.GUI.Shader;

import java.util.Collection;

import com.Engine.RenderEngine.GUI.Components.SubComponents.QuadComponent;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.IRenderable;
import com.Engine.RenderEngine.Shaders.Shader;

public class UIGraphics implements IRenderable<QuadComponent> {
	private static final ModelData MODEL_DATA = new ModelData();
	private static final UIShader SHADER = new UIShader();
	public static final UIRenderer RENDERER = (UIRenderer) SHADER.getRenderer();
	
	static {
		MODEL_DATA.storeDataInAttributeList(UIShader.ATTRIBUTE_LOC_POSITIONS, 2, new float[] {
			0,0,	0,1,	1,0,	1,1
		}, false);//UIMaping1f.png
	}

	private static final UIGraphics INSTANCE = new UIGraphics();
	private UIGraphics() { }
	
	public static void drawRect(float x, float y, float width, float height) {
		INSTANCE.render(new QuadComponent(x, y, width, height));
	}
	
	public static void drawRect(QuadComponent properties) { INSTANCE.render(properties); }
	public static void drawRect(QuadComponent... properties) {
		for(QuadComponent property : properties) drawRect(property);
	}
	public static void drawRect(Collection<? extends QuadComponent> properties) {
		for(QuadComponent property : properties) drawRect(property);
	}
	
	public void render(QuadComponent properties) {
		RENDERER.addModel(this, properties);
	}

	public Shader getShader() { return SHADER; }
	public ModelData getModelData() { return MODEL_DATA; }
}
