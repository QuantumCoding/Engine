package com.Engine.RenderEngine.GLFunctions;

import static org.lwjgl.opengl.GL11.GL_SCISSOR_BOX;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.GL_VIEWPORT;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glIsEnabled;
import static org.lwjgl.opengl.GL11.glScissor;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class ScissorTest extends GL_Function {
	private static final IntBuffer SCISSOR_BOUNDS = BufferUtils.createIntBuffer(16);
	private int x, y, width, height;
	
	private ScissorTest() { super(); }
	
	public void push() {
		glScissor(x, y, width, height);
	}

	public void pull() {
		SCISSOR_BOUNDS.clear();
		glGetInteger(GL_SCISSOR_BOX, SCISSOR_BOUNDS);
		x = SCISSOR_BOUNDS.get(); y = SCISSOR_BOUNDS.get();
		width = SCISSOR_BOUNDS.get(); height = SCISSOR_BOUNDS.get();
	}

	protected int getGLCapablity() { return GL_SCISSOR_TEST; }
	
	public static boolean isEnabled() { return glIsEnabled(GL_SCISSOR_TEST); }
	public static void disable() { glDisable(GL_SCISSOR_TEST); }

	public ScissorTest clone() {
		ScissorTest test = new ScissorTest();	
			test.x = this.x; test.y = this.y;
			test.width = this.width;
			test.height = test.height;
		return test;
	}
	
	public static ScissorTest current() { return new ScissorTest(); }
	public static ScissorTest viewport() { 
		ScissorTest test = new ScissorTest();
		
		SCISSOR_BOUNDS.clear();
		glGetInteger(GL_VIEWPORT, SCISSOR_BOUNDS);
		test.x = SCISSOR_BOUNDS.get(); test.y = SCISSOR_BOUNDS.get();
		test.width = SCISSOR_BOUNDS.get(); test.height = SCISSOR_BOUNDS.get();
		
		return test;
	}
	
	public ScissorTest setBounds(int x, int y, int width, int height) {
		this.x = x; this.y = y;
		this.width = width;
		this.height = height;
		
		return this;
	}
}
