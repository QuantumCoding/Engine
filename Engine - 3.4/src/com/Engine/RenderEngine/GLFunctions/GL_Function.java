package com.Engine.RenderEngine.GLFunctions;

import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glIsEnabled;
import static org.lwjgl.opengl.GL30.glDisablei;
import static org.lwjgl.opengl.GL30.glEnablei;
import static org.lwjgl.opengl.GL30.glGetInteger;
import static org.lwjgl.opengl.GL30.glIsEnabledi;
import static org.lwjgl.opengl.GL41.glGetFloat;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class GL_Function {
	protected boolean enabled;
	protected int index;

	protected GL_Function() 				{ this(-1, true); }
	protected GL_Function(int index) 		{ this(index, true); }
	protected GL_Function(boolean enabled) 	{ this(-1, enabled); }
	
	protected GL_Function(int index, boolean enabled) { 
		this.index = index;
		pull(); 
		
		this.enabled = enabled;
	}
	
	protected GL_Function withIndex(int index) { 
		GL_Function function = clone(); 
		function.index = index; 
		return function; 
	}
	
	protected abstract void _push();
	protected abstract void _pull();
	protected abstract GL_Function getLocalCashe();
	
	public GL_Function syncGLState() {
		GL_Function function = getLocalCashe();
		
		try {
			function.enabled = index < 0 ? glIsEnabled(getGLCapablity()) : glIsEnabledi(getGLCapablity(), index);
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
			if(index < 0) glEnable(getGLCapablity());
			else glEnablei(getGLCapablity(), index);
			
			_push();
			
		} else {
			if(index < 0) glDisable(getGLCapablity());
			else glDisablei(getGLCapablity(), index);
		}
	}
	
	protected int getInt(int glParam) { return index < 0 ? glGetInteger(glParam) : glGetInteger(glParam, index); }
	
	protected void get(int glParam, IntBuffer buffer) { 
		if(index < 0) glGetInteger(glParam, buffer); 
		else glGetInteger(glParam, index, buffer);
	}
	
	protected void get(int glParam, FloatBuffer buffer) { 
		if(index < 0) glGetFloat(glParam, buffer); 
		else glGetFloat(glParam, index, buffer);
	}
	
	public GL_Function copyTo(GL_Function function) {
			function.enabled = this.enabled;
			function.index = this.index;
		return enabled ? _copyTo(function) : function;
	}
	
	protected abstract int getGLCapablity();
	protected abstract GL_Function _copyTo(GL_Function function);
	public abstract GL_Function clone();
	
	protected void _enable() { glEnable(getGLCapablity()); }
	protected void _disable() { glDisable(getGLCapablity()); }
}
