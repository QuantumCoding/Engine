package com.Engine.RenderEngine.GLFunctions;

import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;

public abstract class GL_Function {
	public abstract void push();
	public abstract void pull();
	
	protected abstract int getGLCapablity();
	
	public void enable() { glEnable(getGLCapablity()); }
	public void disable() { glDisable(getGLCapablity()); }
	
	public abstract GL_Function clone();
	
	public void activate() {
		enable(); push();
	}
}
