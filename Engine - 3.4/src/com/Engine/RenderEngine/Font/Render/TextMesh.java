package com.Engine.RenderEngine.Font.Render;

import com.Engine.RenderEngine.Font.Font;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.IRenderable;
import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Util.Camera;

public class TextMesh implements IRenderable<TextRenderProperties> {

	private ModelData mesh;
	
	private Font font;
	private TextShader shader;
	private Renderer<TextMesh, TextRenderProperties, TextShader> renderer;
	
	@SuppressWarnings("unchecked")
	public TextMesh(Font font, ModelData mesh) {
		this.mesh = mesh;
		this.font = font;
		
		shader = font.getShader();
		renderer = (Renderer<TextMesh, TextRenderProperties, TextShader>) shader.getRenderer();
	}
	
	public void render(TextRenderProperties properties, Camera camera) {
		renderer.addModel(this, properties, camera);
	}

	public Font getFont() { return font; }
	
	public TextShader getShader() { return shader; }
	public ModelData getModelData() { return mesh; }
	
	public int getVAO() { return mesh.getVAOId(); }
	public int getIndiceCount() { return mesh.getIndiceCount(); }
	
	public void cleanUp() { mesh.cleanUp(); }
}
