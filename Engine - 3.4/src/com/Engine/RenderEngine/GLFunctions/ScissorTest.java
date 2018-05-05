package com.Engine.RenderEngine.GLFunctions;

import static org.lwjgl.opengl.GL11.GL_SCISSOR_BOX;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.GL_VIEWPORT;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glScissor;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;

public class ScissorTest extends GL_Function {
	private static final IntBuffer SCISSOR_BOUNDS = BufferUtils.createIntBuffer(16);
	private static final ScissorTest CURRENT = new ScissorTest(); static { CURRENT.syncGLState(); }
	
	private int x, y, width, height;
	
	private ScissorTest() { }
	private ScissorTest(boolean enable) { super(enable); } 
	
	public void _push() {
		glScissor(x, FBO.current().getHeight() - y - height, width, height);
		
		CURRENT.x = x;
		CURRENT.y = y;
		CURRENT.width = width;
		CURRENT.height = height;
	}

	public void _pull() {
		SCISSOR_BOUNDS.clear();
		get(GL_SCISSOR_BOX, SCISSOR_BOUNDS);
		x = SCISSOR_BOUNDS.get(); y = SCISSOR_BOUNDS.get();
		width = SCISSOR_BOUNDS.get(); height = SCISSOR_BOUNDS.get();
	}

	protected ScissorTest getLocalCashe() { return CURRENT; }
	
	protected int getGLCapablity() { return GL_SCISSOR_TEST; }
	public ScissorTest clone() { return (ScissorTest) copyTo(new ScissorTest()); }
	
	protected ScissorTest _copyTo(GL_Function function) {
		ScissorTest scissorTest = (ScissorTest) function;
			scissorTest.x = this.x;
			scissorTest.y = this.y;    
			scissorTest.width = this.width;        
			scissorTest.height = this.height;      
		return scissorTest;
	}
	
	public static boolean isEnabled() { return CURRENT.enabled; } //glIsEnabled(GL_SCISSOR_TEST); }
	public static void disable() { glDisable(GL_SCISSOR_TEST); }

	public static ScissorTest diabled() { return new ScissorTest(false); }
	public static ScissorTest enabled() { return new ScissorTest(true); }
	
	public static ScissorTest current() { return new ScissorTest(isEnabled()); }
	
	public static ScissorTest viewport() { 
		ScissorTest test = new ScissorTest(true);
		
		SCISSOR_BOUNDS.clear();
		test.get(GL_VIEWPORT, SCISSOR_BOUNDS);
		test.x = SCISSOR_BOUNDS.get(); test.y = SCISSOR_BOUNDS.get();
		test.width = SCISSOR_BOUNDS.get(); test.height = SCISSOR_BOUNDS.get();
		
		return test;
	}
	
	public static ScissorTest screenFBO() { 
		ScissorTest test = new ScissorTest(true);
		
		test.x = 0; test.y = 0;
		test.width = FBO.SCREEN_FBO.getWidth(); 
		test.height = FBO.SCREEN_FBO.getHeight();
		
		return test;
	}
	
	public static ScissorTest crop(int x, int y, int width, int height) {
		ScissorTest test = new ScissorTest(true);
		
		test.x = x; test.y = y;
		test.width = width;
		test.height = height;
		
		return test;
	}
	
	public ScissorTest setBounds(int x, int y, int width, int height) {
		this.x = x; this.y = y;
		this.width = width;
		this.height = height;
		
		return this;
	}
}
