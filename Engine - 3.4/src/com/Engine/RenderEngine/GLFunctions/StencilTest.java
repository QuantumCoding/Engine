package com.Engine.RenderEngine.GLFunctions;

import static org.lwjgl.opengl.GL11.GL_DECR;
import static org.lwjgl.opengl.GL11.GL_FRONT_FACE;
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
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glIsEnabled;
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

import com.Engine.RenderEngine.GLFunctions.CullFace.FaceWinding;

public class StencilTest extends GL_Function {
	private StencilSet back, front;
	private FaceWinding winding;
	private int clearValue;
	
	private StencilTest() { super(); }
	private StencilTest(boolean skip) { super(skip); }
	
	public void push() {
		back.push();
		front.push();
		
		if(winding != null) 
			glFrontFace(winding.getFunction());
		glClearStencil(clearValue);
	}

	public void pull() {
		StencilSet[] values = StencilSet.pull();
		this.back = values[0];
		this.front = values[1];
		
		clearValue = glGetInteger(GL_STENCIL_CLEAR_VALUE);
		winding = FaceWinding.lookUp(glGetInteger(GL_FRONT_FACE));
	}

	protected int getGLCapablity() { return GL_STENCIL_TEST; }

	public GL_Function clone() {
		StencilTest test = new StencilTest(true);
			test.back = this.back;
			test.front = this.front;
			test.winding = this.winding;
			test.clearValue = this.clearValue;
		return test;
	}
	
	public static boolean isEnabled() { return glIsEnabled(GL_STENCIL_TEST); }
	public static void disable() { glDisable(GL_STENCIL_TEST); }
	
	public static StencilTest current() { return new StencilTest(); }
	
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
	public StencilTest setFrontFace(FaceWinding winding) { this.winding = winding; return this; }
	
	public StencilTest set(GLFace face, ValueModifier fail, ValueModifier depthFail, ValueModifier depthPass, 
			Condition condition, int mask, int writeMask, int refValue
	) {
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
	
	private static class StencilSet {
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
		GLFace face;
		
		public void push() {
			glStencilFuncSeparate(face.getValue(), condition.getValue(), refValue, mask);
			glStencilOpSeparate(face.getValue(), fail.getValue(), depthFail.getValue(), depthPass.getValue());
			glStencilMaskSeparate(face.getValue(), writeMask);
		}
		
		public static StencilSet[] pull() {
			return new StencilSet[] {
				new StencilSet(
					ValueModifier.lookUp(glGetInteger(GL_STENCIL_BACK_FAIL)),
					ValueModifier.lookUp(glGetInteger(GL_STENCIL_BACK_PASS_DEPTH_FAIL)),
					ValueModifier.lookUp(glGetInteger(GL_STENCIL_BACK_PASS_DEPTH_PASS)),
					
					Condition.lookUp(glGetInteger(GL_STENCIL_BACK_FUNC)),
					
					glGetInteger(GL_STENCIL_BACK_VALUE_MASK),
					glGetInteger(GL_STENCIL_BACK_WRITEMASK),
					glGetInteger(GL_STENCIL_BACK_REF),
					
					GLFace.Back
				),
				
				new StencilSet(
					ValueModifier.lookUp(glGetInteger(GL_STENCIL_FAIL)),
					ValueModifier.lookUp(glGetInteger(GL_STENCIL_PASS_DEPTH_FAIL)),
					ValueModifier.lookUp(glGetInteger(GL_STENCIL_PASS_DEPTH_PASS)),
					
					Condition.lookUp(glGetInteger(GL_STENCIL_FUNC)),
					
					glGetInteger(GL_STENCIL_VALUE_MASK),
					glGetInteger(GL_STENCIL_WRITEMASK),
					glGetInteger(GL_STENCIL_REF),
					
					GLFace.Front
				)
			};
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
