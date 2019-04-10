package com.Engine.Util;

import java.util.Arrays;

public  class Tracker<T> {
	private T value;
	private boolean[] changed;
	private final boolean[] BLANK_RESET;
	
	public Tracker(int levelCount) {
		changed = new boolean[levelCount];
		
		BLANK_RESET = new boolean[levelCount];
		Arrays.fill(BLANK_RESET, true);
	}
	
	public void set(T val) { this.value = val; System.arraycopy(BLANK_RESET, 0, changed, 0, changed.length); }
	public T get(int level) { changed[level] = false; return value; }
	
	public boolean changed(int level) { return changed[level]; }
}
