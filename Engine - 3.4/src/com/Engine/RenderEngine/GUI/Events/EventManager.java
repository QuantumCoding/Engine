package com.Engine.RenderEngine.GUI.Events;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.Engine.RenderEngine.GUI.UIBase;

public class EventManager {
	public EventManager() { 
//		this.motionListener = new LinkedList<>();
		Keyboard.enableRepeatEvents(true);
	}
	
//	private List<IMouseMotionListener> motionListener;
	
	private UIBase focused;
	
	private boolean repeatKeyDown;
	
	public void update() {
		if(Mouse.next()) {
			
		}
		
		boolean keyboardNext = Keyboard.next();
		if(keyboardNext || repeatKeyDown) {
			KeyEvent event = new KeyEvent();
			
			if(focused != null) {
				if(event.isPressed()) focused.keyPressed(event);
				else focused.keyPressed(event);
				
				if(keyboardNext && event.isPressed())
					focused.keyTyped(event);
				
				repeatKeyDown = event.isPressed();
			}
		}
	}
}
