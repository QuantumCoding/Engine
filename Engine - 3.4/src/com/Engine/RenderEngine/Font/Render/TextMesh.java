package com.Engine.RenderEngine.Font.Render;

import com.Engine.RenderEngine.Font.Font;
import com.Engine.RenderEngine.Font.Render.Shaders.TextBillboardShader;
import com.Engine.RenderEngine.Font.Render.Shaders.TextShader;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.IRenderable;
import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.Util.Vectors.Vector2f;

public class TextMesh implements IRenderable<TextRenderProperties> {

	private ModelData mesh;
	
	private Font font;
	private TextShader shader;
	private Renderer<TextMesh, TextRenderProperties, TextBillboardShader> renderer;
	private Vector2f size;
	
	public TextMesh(Font font, Vector2f size, ModelData mesh) {
		this.mesh = mesh;
		this.font = font;
		this.size = size;
		
		setShader(font.getShader());
	}
	
	public void render(TextRenderProperties properties) {
		renderer.addModel(this, properties);
	}

	@SuppressWarnings("unchecked")
	public void setShader(TextShader shader) { 
		this.shader = shader; 
		this.renderer = (Renderer<TextMesh, TextRenderProperties, TextBillboardShader>) shader.getRenderer(); 
	}
	
	public TextShader getShader() { return shader; }
	public ModelData getModelData() { return mesh; }
	public Vector2f getSize() { return size; }
	public Font getFont() { return font; }
	
	public int getVAO() { return mesh.getVAOId(); }
	public int getIndiceCount() { return mesh.getIndiceCount(); }
	
	public void cleanUp() { mesh.cleanUp(); }
}
