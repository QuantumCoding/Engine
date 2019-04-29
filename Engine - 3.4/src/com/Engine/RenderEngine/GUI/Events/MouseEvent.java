package com.Engine.RenderEngine.GUI.Events;

import org.lwjgl.input.Mouse;

public class MouseEvent {
	private float x, y;
	private float dx, dy;
	
	private int button;
	private boolean pressed;
	
	public MouseEvent(int button, boolean pressed) {
		this.x = Mouse.getX();
		this.y = Mouse.getY();
		
		this.dx = Mouse.getEventDX();
		this.dy = Mouse.getEventDY();
		
		this.button = button;
		this.pressed = pressed;
	}
	
	public float getX() { return x; }
	public float getY() { return y; }
	
	public float getDx() { return dx; }
	public float getDy() { return dy; }
	
	public int getButton() { return button; }
	public boolean isPressed() { return pressed; }
}
