package com.Engine.RenderEngine.Font.Render;

import java.util.ArrayList;

import com.Engine.RenderEngine.Font.Font;
import com.Engine.RenderEngine.Font.Render.Shaders.TextBillboardShader;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.IRenderable;
import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector2f;

public class TextMesh implements IRenderable<TextRenderProperties> {

	private ModelData mesh;
	
	private float lineHeight;
	private Shader shader;
	private Renderer<TextMesh, TextRenderProperties, TextBillboardShader> renderer;
	private Vector2f size;

	private Font font;
	private float fontSize;
	private Vector2f limit;
	private String text;
	
	private ModelData selection;
	private ArrayList<Float> charBreaks;
	private ArrayList<Integer> lineBreaks;
	
	public TextMesh(String text, Font font, float fontSize, float lineHeight, Vector2f limit, Vector2f size, ModelData mesh, ModelData selection, ArrayList<Float> charBreaks, ArrayList<Integer> lineBreaks) {
		this.lineHeight = lineHeight;
		
		this.mesh = mesh;
		this.size = size;
		
		this.font = font;
		this.fontSize = fontSize;
		this.limit = limit;
		this.text = text;
		
		this.selection = selection;
		this.charBreaks = charBreaks;
		this.lineBreaks = lineBreaks;
		
		setShader(font.getShader());
	}
	
	public void render(TextRenderProperties properties) {
		renderer.addModel(this, properties);
	}

	@SuppressWarnings("unchecked")
	public void setShader(Shader shader) { 
		this.shader = shader; 
		this.renderer = (Renderer<TextMesh, TextRenderProperties, TextBillboardShader>) shader.getRenderer(); 
	}
	
	public ModelData getSelection() { return selection; }
	public ArrayList<Float> getCharBreaks() { return charBreaks; }
	public ArrayList<Integer> getLineBreaks() { return lineBreaks; }

	public float getLineHeight() { return lineHeight; }
	public Shader getShader() { return shader; }
	public ModelData getModelData() { return mesh; }
	public Vector2f getSize() { return size; }
	
	public Font getFont() { return font; }
	public float getFontSize() { return fontSize; }
	public Vector2f getLimit() { return limit; }
	public String getText() { return text; }
	
	public int getVAO() { return mesh.getVAOId(); }
	public int getIndiceCount() { return mesh.getIndiceCount(); }
	
	public void cleanUp() { mesh.cleanUp(); }
}
