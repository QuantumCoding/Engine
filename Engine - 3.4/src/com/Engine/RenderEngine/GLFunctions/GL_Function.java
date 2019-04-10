package com.Engine.RenderEngine.GLFunctions;

import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glIsEnabled;
import static org.lwjgl.opengl.GL30.glGetInteger;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class GL_Function {
	protected boolean enabled;

	protected GL_Function() 				{ this(true); }
	protected GL_Function(boolean enabled) 	{ 	
		pull(); 
		this.enabled = enabled;
	}
	
	protected GL_Function withIndex(int index) { 
		GL_Function function = clone(); 
		return function; 
	}
	
	protected abstract void _push();
	protected abstract void _pull();
	protected abstract GL_Function getLocalCashe();
	
	public GL_Function syncGLState() {
		GL_Function function = getLocalCashe();
		
		try {
			function.enabled = glIsEnabled(getGLCapablity());
			function._pull();
		} catch(Exception e) {
			System.err.println("Failed to Pull " + getClass().getSimpleName() + " details from OpenGL\t|\t" + e);
		}
		
		return function.clone();
	}

	public void pull() { 
		GL_Function function = getLocalCashe();
		if(function == null) return;
		function.copyTo(this); 
	}
	
	public void push() {
		getLocalCashe().enabled = this.enabled;
		
		if(enabled) {
			glEnable(getGLCapablity());
			_push();
			
		} else {
			glDisable(getGLCapablity());
		}
	}
	
	protected int getInt(int glParam) { return glGetInteger(glParam); }
	protected int getInt(int glParam, int layer) { return glGetInteger(glParam, layer); }
	
	protected void get(int glParam, IntBuffer buffer) { glGetInteger(glParam, buffer); }
	protected void get(int glParam, FloatBuffer buffer) { glGetFloat(glParam, buffer); }
	
	public GL_Function copyTo(GL_Function function) {
			function.enabled = this.enabled;
		return enabled ? _copyTo(function) : function;
	}
	
	protected abstract int getGLCapablity();
	protected abstract GL_Function _copyTo(GL_Function function);
	public abstract GL_Function clone();
	
	protected void _enable() { glEnable(getGLCapablity()); }
	protected void _disable() { glDisable(getGLCapablity()); }
}
