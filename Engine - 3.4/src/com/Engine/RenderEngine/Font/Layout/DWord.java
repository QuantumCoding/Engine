package com.Engine.RenderEngine.Font.Layout;

import java.util.ArrayList;
import java.util.ListIterator;

import com.Engine.RenderEngine.Font.Character;

public class DWord {
	private ArrayList<Character> letters;
	private Line line;
	
	private float textScale;
	private float width;
	
	public DWord(float textScale) {
		this.textScale = textScale;
		this.letters = new ArrayList<>();
	}

	public void addCharacter(Character character) {
		width += character.getAdvanceX() * textScale;
		letters.add(character);
	}
	
	public void removeCharacter(int index) {
		width += letters.get(index).getAdvanceX() * textScale;
		letters.remove(index);
	}
	
	public void insertCharacter(int index, Character character) {
		width += character.getAdvanceX() * textScale;
		letters.add(index, character);
	}

	public float approxSize(Character character) {
		return width + character.getAdvanceX() * textScale;
	}
	
	public float approxSizeRemove(int index) {
		return width + letters.get(index).getAdvanceX() * textScale;
	}

	public ListIterator<Character> iterate() { return letters.listIterator(); }
	public float getWidth() { return width; }
	
	public void setLine(Line line) { this.line = line; }
	public Line getLine() { return line; }
	
	public int getLength() { return letters.size(); }
}
