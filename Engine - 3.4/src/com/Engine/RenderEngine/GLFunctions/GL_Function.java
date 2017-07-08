package com.Engine.RenderEngine.GLFunctions;

import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

public abstract class GL_Function {
	protected GL_Function() { pull(); }
	
	public abstract void push();
	public abstract void pull();
	
	protected abstract int getGLCapablity();
	
	private void _enable() { glEnable(getGLCapablity()); }
	public void _disable() { glDisable(getGLCapablity()); }
	
	public abstract GL_Function clone();
	
	public void enable() {
		_enable(); push();
	}
}
