package com.Engine.RenderEngine.GLFunctions;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;

public class CullFace extends GL_Function {

//	private 
	
	public void activate() {
		glEnable(GL_CULL_FACE);
		glFrontFace(GL_CCW);
		glCullFace(GL_BACK);
	}

	public void pull() {
	}

	public GL_Function clone() {
		return null;
	}

	public void push() {
		
	}

	public void enable() { glEnable(GL_CULL_FACE); }

	public void disable() {
		
	}

	protected int getGLCapablity() { return GL_CULL_FACE; }
}
