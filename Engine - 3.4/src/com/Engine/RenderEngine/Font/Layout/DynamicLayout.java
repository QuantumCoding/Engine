package com.Engine.RenderEngine.Font.Layout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import com.Engine.RenderEngine.Font.Character;
import com.Engine.RenderEngine.Font.Font;
import com.Engine.Util.Vectors.Vector2f;

public class DynamicLayout {
	private LinkedList<Line> lines;
	private ArrayList<DWord> words; 
	
	private TextBlock blockStart;
	
	private Font font;
	private Vector2f scale;
	private Vector2f limit;
	
	private String message;
	
	public DynamicLayout replaceText(String text, int index, int span) {
		blockStart.replace(text, index, index + span);
		return this;
	}
	
	public DynamicLayout removeText(int index, int span) {
		blockStart.replace("", index, index + span);
		return this;
	}
	
	public DynamicLayout insertText(String text, int index) {
		blockStart.replace(text, index, index);
		return this;
	}
	
	public void correctLayout(TextBlock start) {
		ListIterator<Line> newLines = Line.create(message.substring(start.first), font, scale, limit);
		start.restructure();
	}
	
	private class TextBlock {
		private int first, last;
		private TextBlock next;
		
		public void restructure() {
			if(last > message.length()) throw new IllegalStateException("Last Index > Message Length"); //last = message.length();
			
			int index = 0;
			while(index < last && message.charAt(index) != ' ') index ++;
			if(index == last) return;
			
			TextBlock split = new TextBlock();
			split.last = last; split.first = index;
			split.next = next;
			
			next = split;
			last = index;
			
			split.restructure();
		}
		
		public TextBlock replace(String text, int start, int end) {
			if(end < first || start > last) {
				if(next == null) throw new IndexOutOfBoundsException();
				return next.replace(text, start, end);
			}

			TextBlock block = this;
			do if(block.last >= end) break; 
			while((block = block.next) != null);
			
			if(block == null) last = end;
			else last = block.last;
			next = block;
			
			message = message.substring(0, start) + text + message.substring(end);
			
			int shift = text.length() - (end - start);
			if(next != null) next.shift(shift);
			
			return this;
		}
		
		private void shift(int amount) {
			first += amount; last += amount;
			if(next != null) next.shift(amount);
		}
	}
	
}
