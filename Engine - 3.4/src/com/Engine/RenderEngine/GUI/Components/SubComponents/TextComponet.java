package com.Engine.RenderEngine.GUI.Components.SubComponents;

import com.Engine.RenderEngine.Font.Font;
import com.Engine.RenderEngine.Font.TextMeshStitcher;
import com.Engine.RenderEngine.Font.Render.TextMesh;
import com.Engine.RenderEngine.Font.Render.TextRenderProperties;
import com.Engine.RenderEngine.GUI.UIBase;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;
import com.Engine.Util.Vectors.Vector4f;

public class TextComponet implements ISubUIComponent {
	private static final Font DEFAULT_FONT = Font.loadFont(UIBase.class.getResourceAsStream("Serif.qFnt"));
	private static final float FONT_SCALE = 1 / TextMeshStitcher.DEFUALT_LINE_HEIGHT;
	
	private static final float MAX_DEF_BASE = 5f;
	private static final float MAX_DEF_BIUS = .5f;
	
	private String text;
	private TextMesh mesh;
	
	private Font font;
	private float fontSize, scale;
	private Vector2f bounds;
	
	private TextRenderProperties properties;
	
	public TextComponet(String text, Font font, float fontSize, Vector2f bounds) {
		this.text = text;
		this.font = font == null ? DEFAULT_FONT : font;
		this.fontSize = fontSize;
		this.bounds = bounds;
		this.scale = 1;
		
		this.properties = new TextRenderProperties(new Transform(), new Vector4f(1,0,0, 1));
		rebuild();
	}

	public void draw(float x, float y) {
		properties.getTransform().setTranslation(new Vector3f(x, y, 0));
		render();
	}

	public void render() { mesh.render(properties); }

	public void translate(Vector3f amount) { properties.translate(amount); }
	public TextComponet updateBounds(float x, float y, float width, float height) {
		properties.getTransform().setTranslation(new Vector3f(x, y, 0)).setScale(new Vector3f(width, height, 1));
		return this;
	}
	
	public Vector2f getTextBounds() { return mesh.getSize(); }
	
	public void setText(String text) {
		if(text.equals(this.text)) return;
		this.text = text; rebuild();
	}
	
	public void setBounds(Vector2f bounds) {
		if(bounds.equals(this.bounds)) return;
		this.bounds = bounds; rebuild();
	}
	
	public void setFont(Font font) { this.setFont(font, fontSize); }
	public void setFontSize(float size) { this.setFont(font, size); }
	public void setFont(Font font, float size) { 
		if(font.equals(this.font) && size == this.fontSize) return;
		
		if(font.equals(this.font)) {
			float deformationFactor = Math.abs(size / this.fontSize - 1);
			float maxDeformation = (float) (MAX_DEF_BIUS * Math.log(MAX_DEF_BASE) / Math.log(size));
			
			if(deformationFactor < maxDeformation) {
				scale = size / this.fontSize;
				properties.getTransform().setScale(new Vector3f(scale, scale, 1));
				return;
			} 
		}
		
		this.font = font;
		this.fontSize = size;
		this.scale = 1;
		
		rebuild();
	}

	public void rebuild() {
		if(mesh != null) mesh.cleanUp();
		
		mesh = TextMeshStitcher.createMesh(text, font, fontSize * FONT_SCALE, 1, bounds);
		mesh.setShader(Font.Text2DShader);
	}

	public String getText() { return text; }

	public Font getFont() { return font; }
	public float getFontSize() { return fontSize; }
	public Vector2f getBounds() { return bounds; }

	public float getLineHeight() { return mesh.getLineHeight(); }
	public Vector2f getSize() { return mesh.getSize().multiply(scale); }

}
