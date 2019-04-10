package com.Engine.RenderEngine.GLFunctions;

import static org.lwjgl.opengl.GL11.GL_DECR;
import static org.lwjgl.opengl.GL11.GL_INCR;
import static org.lwjgl.opengl.GL11.GL_INVERT;
import static org.lwjgl.opengl.GL11.GL_KEEP;
import static org.lwjgl.opengl.GL11.GL_REPLACE;
import static org.lwjgl.opengl.GL11.GL_STENCIL_CLEAR_VALUE;
import static org.lwjgl.opengl.GL11.GL_STENCIL_FAIL;
import static org.lwjgl.opengl.GL11.GL_STENCIL_FUNC;
import static org.lwjgl.opengl.GL11.GL_STENCIL_PASS_DEPTH_FAIL;
import static org.lwjgl.opengl.GL11.GL_STENCIL_PASS_DEPTH_PASS;
import static org.lwjgl.opengl.GL11.GL_STENCIL_REF;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.GL_STENCIL_VALUE_MASK;
import static org.lwjgl.opengl.GL11.GL_STENCIL_WRITEMASK;
import static org.lwjgl.opengl.GL11.GL_ZERO;
import static org.lwjgl.opengl.GL11.glClearStencil;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL14.GL_DECR_WRAP;
import static org.lwjgl.opengl.GL14.GL_INCR_WRAP;
import static org.lwjgl.opengl.GL20.GL_STENCIL_BACK_FAIL;
import static org.lwjgl.opengl.GL20.GL_STENCIL_BACK_FUNC;
import static org.lwjgl.opengl.GL20.GL_STENCIL_BACK_PASS_DEPTH_FAIL;
import static org.lwjgl.opengl.GL20.GL_STENCIL_BACK_PASS_DEPTH_PASS;
import static org.lwjgl.opengl.GL20.GL_STENCIL_BACK_REF;
import static org.lwjgl.opengl.GL20.GL_STENCIL_BACK_VALUE_MASK;
import static org.lwjgl.opengl.GL20.GL_STENCIL_BACK_WRITEMASK;
import static org.lwjgl.opengl.GL20.glStencilFuncSeparate;
import static org.lwjgl.opengl.GL20.glStencilMaskSeparate;
import static org.lwjgl.opengl.GL20.glStencilOpSeparate;

import com.Engine.RenderEngine.GLFunctions.enums.Condition;
import com.Engine.RenderEngine.GLFunctions.enums.GLFace;

public class StencilTest extends GL_Function {
	private static final StencilTest CURRENT = new StencilTest(); static { CURRENT.syncGLState(); }
	
	private StencilSet back = new StencilSet(ValueModifier.Current, ValueModifier.Current, ValueModifier.Current, Condition.Never, 0xFF, 0, 0, GLFace.Back);
	private StencilSet front = new StencilSet(ValueModifier.Current, ValueModifier.Current, ValueModifier.Current, Condition.Never, 0xFF, 0, 0, GLFace.Front);
//	private FaceWinding winding;
	private int clearValue;

	private StencilTest() { }
	private StencilTest(boolean enabled) { super(enabled); }
	
	protected void _push() {
		back.push();
		front.push();
		
		glClearStencil(clearValue);
		
		front.copyTo(CURRENT.front);
		back.copyTo(CURRENT.back);
		
		CURRENT.clearValue = clearValue;
	}

	protected void _pull() {
		this.back = new StencilSet(GLFace.Back);
		this.front = new StencilSet(GLFace.Front);
		
		clearValue = getInt(GL_STENCIL_CLEAR_VALUE);
	}

	protected StencilTest getLocalCashe() { return CURRENT; }

	protected int getGLCapablity() { return GL_STENCIL_TEST; }
	public StencilTest clone() { return (StencilTest) copyTo(new StencilTest()); }
	
	protected StencilTest _copyTo(GL_Function function) {
		StencilTest stencilTest = (StencilTest) function;
			this.back.copyTo(stencilTest.back);
			this.front.copyTo(stencilTest.front);
			stencilTest.clearValue = this.clearValue;
		return stencilTest;
	}
	
	public static boolean isEnabled() { return CURRENT.enabled; } //glIsEnabled(GL_STENCIL_TEST); }
	public static void disable() { glDisable(GL_STENCIL_TEST); }

	public static StencilTest blankDisable() { return new StencilTest(false); }
	public static StencilTest blankEnabled() { return new StencilTest(true); }
	
	public static StencilTest current() { return new StencilTest(isEnabled()); }
	
	public StencilTest setFailOp(ValueModifier fail) 	  { return set(GLFace.Both, fail, null, null, null, -1, -1, -1); }
	public StencilTest setDepthFailOp(ValueModifier fail) { return set(GLFace.Both, null, fail, null, null, -1, -1, -1); }
	public StencilTest setDepthPassOp(ValueModifier pass) { return set(GLFace.Both, null, null, pass, null, -1, -1, -1); }
	public StencilTest setOp(ValueModifier fail, ValueModifier depth, ValueModifier pass) { 
		return set(GLFace.Both, fail, depth, pass, null, -1, -1, -1); }
	
	public StencilTest setFailOp(GLFace face, ValueModifier fail) 	   { return set(face, fail, null, null, null, -1, -1, -1); }
	public StencilTest setDepthFailOp(GLFace face, ValueModifier fail) { return set(face, null, fail, null, null, -1, -1, -1); }
	public StencilTest setDepthPassOp(GLFace face, ValueModifier pass) { return set(face, null, null, pass, null, -1, -1, -1); }
	public StencilTest setOp(GLFace face, ValueModifier fail, ValueModifier depth, ValueModifier pass) { 
		return set(face, fail, depth, pass, null, -1, -1, -1); }
	
	public StencilTest setTestCondition(Condition condition) { return set(GLFace.Both, null, null, null, condition, -1, -1, -1); }
	public StencilTest setTestCondition(Condition condition, int ref, int mask) { 
		return set(GLFace.Both, null, null, null, condition, mask, -1, ref); }
	
	public StencilTest setTestCondition(GLFace face, Condition condition) { return set(face, null, null, null, condition, -1, -1, -1); }
	public StencilTest setTestCondition(GLFace face, Condition condition, int ref, int mask) { 
		return set(face, null, null, null, condition, mask, -1, ref); }
	
	public StencilTest setWriteMask(int mask) { return set(GLFace.Both, null, null, null, null, -1, mask, -1); }
	public StencilTest enableWriteMask(boolean enable) { return set(GLFace.Both, null, null, null, null, -1, enable ? 0xFF : 0x00, -1); }

	public StencilTest setWriteMask(GLFace face, int mask) { return set(face, null, null, null, null, -1, mask, -1); }
	public StencilTest enableWriteMask(GLFace face, boolean enable) { return set(face, null, null, null, null, -1, enable ? 0xFF : 0x00, -1); }
	
	public StencilTest setClearValue(int value) { this.clearValue = value; return this; }
	
	public StencilTest set(GLFace face, ValueModifier fail, ValueModifier depthFail, ValueModifier depthPass, Condition condition, int mask, int writeMask, int refValue ) {
		switch(face) {
			case Both:
			case Back:
				if(fail != null) back.fail = fail;
				if(depthFail != null) back.depthFail = depthFail;
				if(depthPass != null) back.depthPass = depthPass;

				if(condition != null) back.condition = condition;

				if(mask >= 0) back.mask = mask;
				if(writeMask >= 0) back.writeMask = writeMask;

				if(refValue >= 0) back.refValue = refValue;
				
			if(face == GLFace.Back) break;
			case Front:
				if(fail != null) front.fail = fail;
				if(depthFail != null) front.depthFail = depthFail;
				if(depthPass != null) front.depthPass = depthPass;

				if(condition != null) front.condition = condition;

				if(mask >= 0) front.mask = mask;
				if(writeMask >= 0) front.writeMask = writeMask;

				if(refValue >= 0) front.refValue = refValue;
			break;
			
			default: throw new IllegalArgumentException(face + " is not a valid face for a StencilTest");
		}
		
		return this;
	}
	
	private class StencilSet {
		public StencilSet(GLFace face) { this.face = face; pull(); }
		
		public StencilSet(ValueModifier fail, ValueModifier depthFail, ValueModifier depthPass, 
				Condition condition, int mask, int writeMask, int refValue, GLFace face
		) {
			this.fail = fail;
			this.depthFail = depthFail;
			this.depthPass = depthPass;
			
			this.condition = condition;
			this.mask = mask; this.writeMask = writeMask;
			this.refValue = refValue;
			
			this.face = face;
		}

		ValueModifier fail, depthFail, depthPass;
		Condition condition; int mask, writeMask, refValue;
		final GLFace face;
		
		public void push() {
			glStencilFuncSeparate(face.getValue(), condition.getValue(), refValue, mask);
			glStencilOpSeparate(face.getValue(), fail.getValue(), depthFail.getValue(), depthPass.getValue());
			glStencilMaskSeparate(face.getValue(), writeMask);
		}
		
		public StencilSet pull() {
			return face == GLFace.Back ?
				new StencilSet(
					ValueModifier.lookUp(getInt(GL_STENCIL_BACK_FAIL)),
					ValueModifier.lookUp(getInt(GL_STENCIL_BACK_PASS_DEPTH_FAIL)),
					ValueModifier.lookUp(getInt(GL_STENCIL_BACK_PASS_DEPTH_PASS)),
					
					Condition.lookUp(getInt(GL_STENCIL_BACK_FUNC)),
					
					getInt(GL_STENCIL_BACK_VALUE_MASK),
					getInt(GL_STENCIL_BACK_WRITEMASK),
					getInt(GL_STENCIL_BACK_REF),
					
					GLFace.Back
				) :
				
				new StencilSet(
					ValueModifier.lookUp(getInt(GL_STENCIL_FAIL)),
					ValueModifier.lookUp(getInt(GL_STENCIL_PASS_DEPTH_FAIL)),
					ValueModifier.lookUp(getInt(GL_STENCIL_PASS_DEPTH_PASS)),
					
					Condition.lookUp(getInt(GL_STENCIL_FUNC)),
					
					getInt(GL_STENCIL_VALUE_MASK),
					getInt(GL_STENCIL_WRITEMASK),
					getInt(GL_STENCIL_REF),
					
					GLFace.Front
				);
		}
		
		public void copyTo(StencilSet set) {
			set.fail = this.fail;
			set.depthFail = this.depthFail;
			set.depthPass = this.depthPass;
			set.condition = this.condition;
			set.mask = this.mask;
			set.writeMask = this.writeMask;
			set.refValue = this.refValue;
		}
		
		public StencilSet clone() {
			return new StencilSet(fail, depthFail, depthPass, condition, mask, writeMask, refValue, face);
		}
	}

	public static enum ValueModifier {
		Current(GL_KEEP),
		Zero(GL_ZERO),
		
		Replace(GL_REPLACE),
		
		Incress(GL_INCR),
		IncressOrWrap(GL_INCR_WRAP),
		Decress(GL_DECR),
		DecressOrWrap(GL_DECR_WRAP),
		
		BitInvert(GL_INVERT);
		
		private int glValue;
		private ValueModifier(int glValue) { this.glValue = glValue; }
		public int getValue() { return glValue; }
		
		public static ValueModifier lookUp(int glValue) {
			for(ValueModifier modifier : values()) if(modifier.glValue == glValue) return modifier;
			throw new IllegalArgumentException(glValue + " is not a valid ValueModifier");
		}
	}
}
