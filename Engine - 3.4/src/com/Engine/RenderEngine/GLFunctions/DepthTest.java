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
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetBoolean;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.Engine.RenderEngine.GLFunctions.enums.Condition;

public class DepthTest extends GL_Function {
	private static final FloatBuffer DEPTH_VALUES = BufferUtils.createFloatBuffer(16);
	private static final DepthTest CURRENT = new DepthTest(); static { CURRENT.syncGLState(); }
	
	private static final DepthTest NORMAL = new DepthTest()
			.setCondition(Condition.LessThen)
			.setRange(0, 1)
			.setClearValue(1)
		.shouldWriting(true);
	private static final DepthTest CHECK_ONLY = NORMAL.clone().shouldWriting(false);

	private Condition condition;
	private boolean enableWrite;
	private float nearVal, farValue;
	private float clearDepth;

	private DepthTest() { }
	private DepthTest(boolean enable) { super(enable); } 
	
	protected void _push() {
		glDepthMask(enableWrite);
		glDepthFunc(condition.getValue());
		glDepthRange(nearVal, farValue);
		glClearDepth(clearDepth);
		
		CURRENT.enableWrite = enableWrite;
		CURRENT.condition = condition;
		CURRENT.nearVal = nearVal;
		CURRENT.farValue = farValue;
		CURRENT.clearDepth = clearDepth;
	}

	protected void _pull() {
		if(DEPTH_VALUES == null) return;
		
		condition = Condition.lookUp(getInt(GL_DEPTH_FUNC));
		enableWrite = glGetBoolean(GL_DEPTH_WRITEMASK);
		
		DEPTH_VALUES.clear();
		get(GL_DEPTH_RANGE, DEPTH_VALUES);
		nearVal = DEPTH_VALUES.get(); farValue = DEPTH_VALUES.get();

		DEPTH_VALUES.clear();
		get(GL_DEPTH_CLEAR_VALUE, DEPTH_VALUES);
		clearDepth = DEPTH_VALUES.get();
	}

	protected DepthTest getLocalCashe() { return CURRENT; }
	
	protected int getGLCapablity() { return GL_DEPTH_TEST; }
	public DepthTest clone() { return (DepthTest) copyTo(new DepthTest()); }
	
	protected DepthTest _copyTo(GL_Function function) {
		DepthTest depthTest = (DepthTest) function;
			depthTest.enableWrite = this.enableWrite;
			depthTest.condition = this.condition;    
			depthTest.nearVal = this.nearVal;        
			depthTest.farValue = this.farValue;      
			depthTest.clearDepth = this.clearDepth;  
		return depthTest;
	}
	
	public static boolean isEnabled() { return CURRENT.enabled; } //glIsEnabled(GL_DEPTH_TEST); }
	public static void enable() { glEnable(GL_DEPTH_TEST); CURRENT.enabled = true; }
	public static void disable() { glDisable(GL_DEPTH_TEST); CURRENT.enabled = false; }

	public static DepthTest blankDiabled() { return new DepthTest(false); }
	public static DepthTest blankEnabled() { return new DepthTest(true); }
	
	public static DepthTest current() { return new DepthTest(isEnabled()); }
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
