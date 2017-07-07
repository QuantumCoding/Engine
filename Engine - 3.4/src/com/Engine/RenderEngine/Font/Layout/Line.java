package com.Engine.RenderEngine.Font.Layout;

import java.util.LinkedList;
import java.util.ListIterator;

import com.Engine.RenderEngine.Font.Character;
import com.Engine.RenderEngine.Font.Font;
import com.Engine.Util.Vectors.Vector2f;

public class Line {
	private float limit;
	private float textScale;

	private float width;
	private Word current;
	private LinkedList<Word> words;
	
	protected Line(float textScale, float limit) {
		this.textScale = textScale;
		this.limit = limit;
		
		this.words = new LinkedList<>();
	}
	
	public boolean addCharacter(Character character) {
		if(current == null) current = new Word(textScale);
		
		if(character.getCharCode() == Character.CharacterSpace.ASCII_SPACE) {
			current.addCharacter(character);
			words.add(current);
			
			width += character.getAdvanceX() * textScale + current.getWidth();
			current = width > limit ? null : new Word(textScale);
			return current != null;
		}
		
		float approxSize = current.approxSize(character);
		if(limit > 0 && approxSize + width > limit) {
			if(words.size() == 0) {
				words.add(current);
				width += approxSize;
				current = null;
			}

			return false;
		}

		current.addCharacter(character);
		return true;
	}

	private void forceClosed() { if(current != null) words.add(current); }
	public ListIterator<Word> iterate() { return words.listIterator(); }
	
	public static ListIterator<Line> create(String message, Font font, Vector2f scale, Vector2f limit) {
		LinkedList<Line> lines = new LinkedList<>();
		Line current = new Line(scale.x, limit.x);
		lines.add(current);
				
		for(int i = 0; i < message.length(); i ++) {
			Character character = font.getCharacter(message.charAt(i));
			
			if(!current.addCharacter(character)) {
				if(limit.y > 0 && (lines.size() + 1) * font.getLineHeight() * scale.y > limit.y) {
					Word eclipse = new Word(scale.x);
					Character dot = font.getCharacter('.');
					eclipse.addCharacter(dot); eclipse.addCharacter(dot); eclipse.addCharacter(dot);
					
					if(eclipse.getWidth() + current.width > limit.x) {
						current.words.removeLast();
					}
					
					current.current = null;
					current.words.add(eclipse);
					break;
				}
				
				Word prevWord = current.current;
				lines.add(current = new Line(scale.x, limit.x));
				current.current = prevWord;
				
				if(!(character instanceof Character.CharacterSpace))
					i --;
			}
		}
		
		current.forceClosed();
		return lines.listIterator();
	}
}
