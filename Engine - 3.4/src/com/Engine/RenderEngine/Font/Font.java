package com.Engine.RenderEngine.Font;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import com.Engine.Demo.XML.XMLParser;
import com.Engine.Demo.XML.XMLTag;
import com.Engine.RenderEngine.Font.Render.FontTexture;
import com.Engine.RenderEngine.Font.Render.TextShader;

public class Font {
	public static final TextShader DefaultShader = new TextShader();
	
	private FontTexture texture;
	private Character[] characters;
	
	private int offset;
	private boolean multiChaneled;
	private int lineHeight;
	
	private TextShader shader;
	
	private Font(XMLTag data, boolean usingMultiChanel, int lineHeight, FontTexture texture, int offset, Character... characters) {
		this.texture = texture;
		
		this.offset = offset;
		this.characters = characters;
		
		this.lineHeight = lineHeight;
		this.multiChaneled = usingMultiChanel;
		this.shader = DefaultShader;
	}
	
	public static Font loadFont(InputStream font) {
		ArrayList<BufferedImage> images = new ArrayList<>(); 
		XMLTag[] tags = null;
		
		try(ZipInputStream in = new ZipInputStream(font)) {
			ZipEntry entry;
			while((entry = in.getNextEntry()) != null) {
				if(entry.getName().equals("data.xml")) {
					tags = XMLParser.read(in);
				
				} else if(entry.getName().toLowerCase().endsWith(".png")) {
					int index = Integer.parseInt(entry.getName().substring(0, entry.getName().indexOf('.')));
					if(index <= images.size()) for(int i = images.size(); i < index + 1; i ++) images.add(null);
					images.set(index, ImageIO.read(in));
				}
				
				in.closeEntry();
			}
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		
		FontTexture texture = new FontTexture(images.toArray(new BufferedImage[0]));
		
		int lineHeight = 0;
		boolean multiChanel = false;
		XMLTag fontData = null;
		ArrayList<Character> characters = new ArrayList<>();
		int minId = -1, maxId = 0;
		
		for(XMLTag root : tags) {
			if(root.getName().equals("symbol_sheet")) {
				multiChanel = (boolean) root.getProperty("usingMultiChanel");
				fontData = root.getChild("font");
				
				int width = (int) root.getProperty("width");
				int height = (int) root.getProperty("height");
				float padding = (float) root.getProperty("padding");
				
				XMLTag lineData = root.getChild("lineData");
				lineHeight = (int) lineData.getProperty("lineHeight");
				
				XMLTag symbolRoot = root.getChild("symbol_set");
				for(XMLTag symbol : symbolRoot.getAllChildren("symbol")) {
					Character character = new Character(symbol, width, height, padding);
					characters.add(character);
					
					if(character.getCharCode() == 0xFFFD) continue;
					if(minId == -1 || character.getCharCode() < minId) minId = character.getCharCode();
					if(maxId < character.getCharCode()) maxId = character.getCharCode();
				}
				
				characters.add(new Character.CharacterSpace((int) lineData.getProperty("spaceWidth")));
				if(minId == -1 || Character.CharacterSpace.ASCII_SPACE < minId) minId = Character.CharacterSpace.ASCII_SPACE;
				if(maxId < Character.CharacterSpace.ASCII_SPACE) maxId = Character.CharacterSpace.ASCII_SPACE;
				break;
			}
		}
		
		int range = maxId - minId;
		Character[] charArray = new Character[range + 1 + 1];
		for(Character character :  characters) {
			if(character.getCharCode() == 0xFFFD) charArray[range + 1] = character;
			else charArray[character.getCharCode() - minId] = character;
		}
		
		return new Font(fontData, multiChanel, lineHeight, texture, minId, charArray);
	}
	
	public Character getCharacter(int charCode) {
		Character character;
		if(charCode - offset < 0 || charCode - offset > characters.length - 1 || 
				(character = characters[charCode - offset]) == null) {
			// return Blank Char
			
			return characters[characters.length - 1];
		}
		
		return character;
	}
	
	public int getLineHeight() { return lineHeight; }
	public boolean usingMultiChanel() { return multiChaneled; }
	public FontTexture getTexture() { return texture; }
	public TextShader getShader() { return shader; }

	public void setTexture(FontTexture texture) { this.texture = texture; }
	public void setShader(TextShader shader) { this.shader = shader; }
	
	public void cleanUp() { texture.cleanUp(); }
}
