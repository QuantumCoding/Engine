package com.Engine.RenderEngine.Font;

import java.util.ArrayList;

import com.Engine.RenderEngine.Font.Render.TextRenderProperties;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Models.ModelData.VBO;
import com.Engine.RenderEngine.Models.ModelData.VBOs.CashedVBO;
import com.Engine.RenderEngine.Shaders.Render.IRenderable;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.System.RenderingSystem;
import com.Engine.RenderEngine.System.RenderingSystem.ModelInstance;
import com.Engine.Util.Vectors.Vector2f;

public class DynTextMesh implements IRenderable<TextRenderProperties> {
	private ModelData mesh;
	private ModelData mask;
	private Vector2f textScale;
	
	private Font font;
	private float fontSize;
	private float lineHeight;
	
	private RenderingSystem<DynTextMesh, TextRenderProperties> shader;
	
	private static final Letter EMPTY_LETTER = Letter(new Vector2f(), null, -1);
	private static class Letter { Vector2f cursor; Character symbol; int vboIndex; boolean isWrapPoint, remesh; }
	private ArrayList<Letter> letterMap;
	
	private float lengthLimit;
	
	private String text;
	private Vector2f size;
	
	public DynTextMesh(ModelData mesh, ModelData mask, Font font, float fontSize, float lengthLimit, Vector2f scale) {
		this.mesh = mesh;
		this.mask = mask;
		this.textScale = scale;
		
		this.font = font;
		this.fontSize = fontSize;
		this.lineHeight = font.getLineHeight() * scale.y * 2;
		
		this.lengthLimit = lengthLimit < 0 ? Float.POSITIVE_INFINITY : lengthLimit;
		
		text = "";
		size = new Vector2f();
		letterMap = new ArrayList<>();
	}
	
	void copyFrom(DynTextMesh copy) {
		this.text = copy.text;
		this.size = copy.size;
		
		this.font = copy.font;
		this.fontSize = copy.fontSize;
		this.lineHeight = copy.lineHeight;
		
		this.textScale = copy.textScale;
		this.letterMap = copy.letterMap;
		
		this.lengthLimit = copy.lengthLimit;
		
		CashedVBO vbo = (CashedVBO) copy.getVBO();
		mesh.attachAttribute(Shader.ATTRIBUTE_LOC_POSITIONS, vbo, 3, 0, 7);
		mesh.attachAttribute(Shader.ATTRIBUTE_LOC_TEXCOORDS, vbo, 4, 3, 7);
		
		CashedVBO maskVBO = (CashedVBO) copy.getMaskVBO();
		mask.attachAttribute(Shader.ATTRIBUTE_LOC_POSITIONS, maskVBO, 3, 0, 3);
		
		mesh.loadIndices(copy.getMesh().getIndexVBO());
		mask.loadIndices(copy.getMesh().getIndexVBO());
	}
	
	void addLetter(int strIndex, Vector2f cursor, Character symbol, int vboIndex) {
		letterMap.add(strIndex, Letter(cursor, symbol, vboIndex));
		text = text.substring(0, strIndex) + symbol.asChar() + text.substring(strIndex);

		Letter next = getOrDefault(strIndex + 1, null);
		if(next != null) next.remesh = true;
	}
	
	int removeLetter(int strIndex) {
		Letter next = getOrDefault(strIndex + 1, null);
		if(next != null) next.remesh = true;
		
		text = text.substring(0, strIndex) + text.substring(strIndex + 1);
		return letterMap.remove(strIndex).vboIndex;
	}
	
	protected void recalcSize() {
		Vector2f cursor = EMPTY_LETTER.cursor;
		size = new Vector2f();
		
		for(int i = 0; i < letterMap.size(); i ++) {
			Letter letter = getOrDefault(i, EMPTY_LETTER);
			Character symbol = letter.symbol;
			
			Vector2f max = cursor
					.add(symbol.getOffsetX(), symbol.getOffsetY())
					.add(symbol.getSize().capMin(symbol.getAdvance()))
					.multiply(textScale).add(-1, 1);
	
			size = size.capMin(max);
			cursor = letter.cursor;
		}
	}

	private Letter getOrDefault(int indx, Letter def) { return indx < 0 || indx >= letterMap.size() ? def : letterMap.get(indx); }
	
	public Character getSymbol(int index) { return getOrDefault(index, EMPTY_LETTER).symbol; }
	public Vector2f getCursor(int index) { return getOrDefault(index, EMPTY_LETTER).cursor; }
	public int getVBOIndex(int index) { return getOrDefault(index, EMPTY_LETTER).vboIndex; }
	
	public void insertWrapPoint(int index) { 
		Letter letter = getOrDefault(index, null);
		if(letter == null) throw new IllegalArgumentException("Cannot wrap on non-existant Index: " + index);
		if(letter.isWrapPoint) throw new IllegalArgumentException("Index: " + index + ", is already a wrapping point");
		
		letter.cursor.setX(0).addY(font.getLineHeight());
		letter.isWrapPoint = true;
		
		Letter next = getOrDefault(index + 1, null);
		if(next != null) next.remesh = true;
		
		DynamicTextStitcher.recalculateCursorHorizontal(this, index + 1, -1);
	}
	
	public void removeWrapPoint(int index, int currentOverflow) { 
		Letter letter = getOrDefault(index, null);
		if(letter == null) throw new IllegalArgumentException("Cannot wrap on non-existant Index: " + index);
		if(!letter.isWrapPoint) throw new IllegalArgumentException("Index: " + index + ", is not a wrapping point");
		
		Vector2f preCursor = getCursor(index - 1);
		letter.cursor.set(preCursor).addX(letter.symbol.getAdvanceX());
		letter.isWrapPoint = false;
		
		letter.remesh = true;
		
		Letter next = getOrDefault(index + 1, null);
		if(next != null) next.remesh = true;
		
		DynamicTextStitcher.recalculateCursorHorizontal(this, index + 1, currentOverflow);
	}
	
	public boolean isWrapPoint(int index) { return getOrDefault(index, EMPTY_LETTER).isWrapPoint; }

	public void remeshComplete(int index) { getOrDefault(index, EMPTY_LETTER).remesh = false; }
	public boolean needsRemesh(int index) { return getOrDefault(index, EMPTY_LETTER).remesh; }
	
	public Vector2f shiftCursor(int index, Vector2f amount) {
		Letter letter = letterMap.get(index);
		letter.cursor = letter.cursor.add(amount); 

		Letter next = getOrDefault(index + 1, null);
		if(next != null) next.remesh = true;
		
		return letter.cursor;
	}
	
	public Vector2f setCursor(int index, Vector2f cursor) {
		Letter letter = letterMap.get(index);
		letter.cursor = cursor; 
		
		Letter next = getOrDefault(index + 1, null);
		if(next != null) next.remesh = true;
		
		return letter.cursor;
	}
	
	public VBO getVBO() { return mesh.getAttributeVBO(Shader.ATTRIBUTE_LOC_POSITIONS); }
	public VBO getMaskVBO() { return mask.getAttributeVBO(Shader.ATTRIBUTE_LOC_POSITIONS); }
	public ModelData getMesh() { return mesh; }
	public ModelData getMask() { return mask; }
	
	public Vector2f getSize() { return size; }
	public String getText() { return text; }
	
	public Vector2f getTextScale() { return textScale; }
	public float getLengthLimit() { return lengthLimit; }
	
	private static Letter Letter(Vector2f cursor, Character symbol, int vboIndex) { 
		Letter l = new Letter(); l.cursor = cursor; l.symbol = symbol; l.vboIndex = vboIndex; return l; }

	public ModelInstance<DynTextMesh, TextRenderProperties> render(TextRenderProperties properties) {
		return shader.addModel(this, properties);
	}

	public void setShader(RenderingSystem<DynTextMesh, TextRenderProperties> shader) { this.shader = shader; }
	
	public Font getFont() { return font; }
	public float getFontSize() { return fontSize; }
	public float getLineHeight() { return lineHeight; }
	public float getFontLineHeight() { return font.getLineHeight(); }
	
	public RenderingSystem<DynTextMesh, TextRenderProperties> getShader() { return shader; }
	public ModelData getModelData() { return mesh; }
}
