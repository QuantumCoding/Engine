package com.Engine.RenderEngine.GLFunctions;

import static org.lwjgl.opengl.GL11.GL_DEPTH_CLEAR_VALUE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_FUNC;
import static org.lwjgl.opengl.GL11.GL_DEPTH_RANGE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DEPTH_WRITEMASK;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDepthRange;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glGetBoolean;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glIsEnabled;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class DepthTest extends GL_Function {
	private static final DepthTest NORMAL = new DepthTest(true).setCondition(Condition.LessThen)
			.setRange(0, 1).setClearValue(1).shouldWriting(true);
	
	private static final DepthTest CHECK_ONLY = NORMAL.clone().shouldWriting(false);

	private static final FloatBuffer DEPTH_VALUES = BufferUtils.createFloatBuffer(16);
	
	private Condition condition;
	private boolean enableWrite;
	private float nearVal, farValue;
	private float clearDepth;

	private DepthTest() { super(); }
	private DepthTest(boolean skip) { super(skip); }
	
	public void push() {
		glDepthMask(enableWrite);
		glDepthFunc(condition.getValue());
		glDepthRange(nearVal, farValue);
		glClearDepth(clearDepth);
	}

	public void pull() {
		if(DEPTH_VALUES == null) return;
		
		condition = Condition.lookUp(glGetInteger(GL_DEPTH_FUNC));
		enableWrite = glGetBoolean(GL_DEPTH_WRITEMASK);
		
		DEPTH_VALUES.clear();
		glGetFloat(GL_DEPTH_RANGE, DEPTH_VALUES);
		nearVal = DEPTH_VALUES.get(); farValue = DEPTH_VALUES.get();

		DEPTH_VALUES.clear();
		glGetFloat(GL_DEPTH_CLEAR_VALUE, DEPTH_VALUES);
		clearDepth = DEPTH_VALUES.get();
	}

	protected int getGLCapablity() { return GL_DEPTH_TEST; }

	public DepthTest clone() {
		DepthTest test = new DepthTest(true);
			test.condition = this.condition;
			test.enableWrite = this.enableWrite;
			test.nearVal = this.nearVal;
			test.farValue = this.farValue;
			test.clearDepth = this.clearDepth;
		return test;
	}

	public static boolean isEnabled() { return glIsEnabled(GL_DEPTH_TEST); }
	public static void disable() { glDisable(GL_DEPTH_TEST); }
	
	public static DepthTest current() { return new DepthTest(); }
	public static DepthTest normal() { return NORMAL.clone(); }
	public static DepthTest checkOnly() { return CHECK_ONLY.clone(); }

	public DepthTest setCondition(Condition pass) { this.condition = pass ; return this; }
	public DepthTest setClearValue(float clearDepth) { this.clearDepth = clearDepth; return this; }
	public DepthTest shouldWriting(boolean write) { this.enableWrite = write; return this; }
	
	public DepthTest setRange(float nearValue, float farValue) { 
		this.nearVal = nearValue; 
		this.farValue = farValue;
		
		return this; 
	}
}
