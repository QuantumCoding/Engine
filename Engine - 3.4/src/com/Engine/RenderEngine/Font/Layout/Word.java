package com.Engine.RenderEngine.Font.Layout;

import java.util.LinkedList;
import java.util.ListIterator;

import com.Engine.RenderEngine.Font.Character;

public class Word {
	private LinkedList<Character> letters;
	
	private float textScale;
	private float width;
	
	public Word(float textScale) {
		this.textScale = textScale;
		this.letters = new LinkedList<>();
	}

	public void addCharacter(Character character) {
		width += character.getAdvanceX() * textScale;
		letters.add(character);
	}

	public float approxSize(Character character) {
		return width + character.getAdvanceX() * textScale;
	}

	public ListIterator<Character> iterate() { return letters.listIterator(); }
	public float getWidth() { return width; }
}
