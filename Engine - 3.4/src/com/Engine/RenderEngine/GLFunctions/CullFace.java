package com.Engine.RenderEngine.GLFunctions;

import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_FRONT_FACE;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glIsEnabled;

public class CullFace extends GL_Function {
	private static final CullFace NORMAL_CW  = new CullFace().setCullFace(GLFace.Front).setFrontFace(FaceWinding.CW);
	private static final CullFace NORMAL_CCW = new CullFace().setCullFace(GLFace.Front).setFrontFace(FaceWinding.CCW);
	
	private GLFace cull;
	private FaceWinding winding;
		
	private CullFace() { super(); }
	
	public void push() {
		glCullFace(cull.getValue());
		glFrontFace(winding.glValue);
	}

	public void pull() {
		cull = GLFace.lookUp(glGetInteger(GL_CULL_FACE));
		winding = FaceWinding.lookUp(glGetInteger(GL_FRONT_FACE));
	}

	protected int getGLCapablity() { return GL_CULL_FACE; }
	
	public CullFace clone() {
		CullFace cull = new CullFace();
			cull.cull = this.cull;
			cull.winding = this.winding;
		return cull;
	}
	
	public static boolean isEnabled() { return glIsEnabled(GL_CULL_FACE); }
	public static void disable() { glDisable(GL_CULL_FACE); }
	
	public static CullFace current() { return new CullFace(); }
	public static CullFace cw() { return NORMAL_CW.clone(); }
	public static CullFace ccw() { return NORMAL_CCW.clone(); }
	
	public CullFace setCullFace(GLFace face) { this.cull = face; return this; }
	public CullFace setFrontFace(FaceWinding winding) { this.winding = winding; return this; }
	
	public static enum FaceWinding {
		Clockwise(GL_CW), CW(GL_CW),
		CounterClockwise(GL_CCW), CCW(GL_CCW);
		
		private int glValue;
		private FaceWinding(int glValue) { this.glValue = glValue; }
		public int getFunction() { return glValue; }
		
		public static FaceWinding lookUp(int glValue) {
			for(FaceWinding side : values()) if(side.glValue == glValue) return side;
			throw new IllegalArgumentException(glValue + " is not a valid CullSide");
		}
	}
}
