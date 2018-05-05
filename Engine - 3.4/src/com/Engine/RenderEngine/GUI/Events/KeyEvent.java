package com.Engine.RenderEngine.GUI.Events;

import org.lwjgl.input.Keyboard;

public class KeyEvent {
	private int keyCode;
	private boolean pressed;
	
	public KeyEvent() {
		this(Keyboard.getEventKey(), Keyboard.getEventKeyState());
	}
	
	public KeyEvent(int keyCode, boolean pressed) {
		this.keyCode = keyCode;
		this.pressed = pressed;
	}

	public int getKeyCode() { return keyCode; }
	public boolean isPressed() { return pressed; }

	public String toString() {
		return "KeyEvent [keyCode=" + keyCode + ", pressed=" + pressed + "]";
	}
}
