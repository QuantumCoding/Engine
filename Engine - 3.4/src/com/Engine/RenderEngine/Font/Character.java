package com.Engine.RenderEngine.Font;

import com.Engine.Demo.XML.XMLTag;

public class Character {
	private static final int PADDING = 8;//3;
	
	private int charCode;

	// Render Info
	private float xAdvance, yAdvance;
	private float xOffset, yOffset;

	// Sheet Data
	private float sheetX, sheetY;
	private float width, height;
	private float texWidth, texHeight;
	private int sheetId, colorChanel;
	
	protected Character(XMLTag tag, float textureWidth, float textureHeight, float padding) {
		this.charCode 	 = (int) tag.getProperty("id");
		
		this.xAdvance 	 = (float) tag.getProperty("xAdvance") - padding + PADDING; // + padding / 2
		this.yAdvance 	 = (float) tag.getProperty("yAdvance") - padding + PADDING;
		this.xOffset 	 = (float) tag.getProperty("xOffset")  + padding / 2 - PADDING;
		this.yOffset 	 = (float) tag.getProperty("yOffset")  + padding / 2 - PADDING;
		
		this.sheetX 	 = (float) tag.getProperty("sheetX") + padding / 2 - PADDING;
		this.sheetY 	 = (float) tag.getProperty("sheetY") + padding / 2 - PADDING;
		this.width 		 = (float) tag.getProperty("width")  - padding + PADDING * 2;
		this.height 	 = (float) tag.getProperty("height") - padding + PADDING * 2;
		
		this.sheetId 	 = (int) tag.getProperty("sheetId");
		this.colorChanel = (int) tag.getProperty("colorChanel");
		
		this.texWidth = width / textureWidth;
		this.texHeight = height / textureHeight;
		
		this.sheetX /= textureWidth;
		this.sheetY /= textureHeight;
	}
	
	Character(int id, int xAdvance) { this.xAdvance = xAdvance; this.charCode = id; }
	
	public static class CharacterSpace extends Character {
		public static final int ASCII_SPACE = (int) ' ';
		protected CharacterSpace(int width) { super(ASCII_SPACE, width); }
	}

	public int getCharCode() { return charCode; }

	public float getAdvanceX() { return xAdvance; }
	public float getAdvanceY() { return yAdvance; }
	public float getOffsetX() { return xOffset; }
	public float getOffsetY() { return yOffset; }

	public float getSheetX() { return sheetX; }
	public float getSheetY() { return sheetY; }

	public float getWidth() { return width; }
	public float getHeight() { return height; }

	public float getTexWidth() { return texWidth; }
	public float getTexHeight() { return texHeight; }

	public int getSheetId() { return sheetId; }
	public int getColorChanel() { return colorChanel; }
}
