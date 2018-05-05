package com.Engine.RenderEngine.GLFunctions;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE_MODE;
import static org.lwjgl.opengl.GL11.GL_FRONT_FACE;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glFrontFace;

import com.Engine.RenderEngine.GLFunctions.enums.FaceWinding;
import com.Engine.RenderEngine.GLFunctions.enums.GLFace;

public class CullFace extends GL_Function {
	private static final CullFace CURRENT = new CullFace(); static { CURRENT.syncGLState(); }
	
	private static final CullFace NORMAL_CW  = new CullFace().setCullFace(GLFace.Back).setFrontFace(FaceWinding.CW);
	private static final CullFace NORMAL_CCW = new CullFace().setCullFace(GLFace.Back).setFrontFace(FaceWinding.CCW);
	
	private GLFace cull;
	private FaceWinding winding;
		
	private CullFace() { }
	private CullFace(boolean enabled) { super(enabled); }
	
	protected void _push() {
		glCullFace(cull.getValue());
		glFrontFace(winding.getFunction());
		
		CURRENT.cull = cull;
		CURRENT.winding = winding;
	}

	protected void _pull() {
		cull = GLFace.lookUp(getInt(GL_CULL_FACE_MODE));
		winding = FaceWinding.lookUp(getInt(GL_FRONT_FACE));
	}
	
	protected CullFace getLocalCashe() { return CURRENT; }

	protected int getGLCapablity() { return GL_CULL_FACE; }
	public CullFace clone() { return (CullFace) copyTo(new CullFace()); }
	
	protected CullFace _copyTo(GL_Function function) {
		CullFace cullFace = (CullFace) function;
			cullFace.cull = this.cull;
			cullFace.winding = this.winding;
		return cullFace;
	}
	
	public static boolean isEnabled() { return CURRENT.enabled; } //glIsEnabled(GL_CULL_FACE); }
	public static void disable() { glDisable(GL_CULL_FACE); }

	public static CullFace diabled() { return new CullFace(false); }
	public static CullFace enabled() { return new CullFace(true); }
	
	public static CullFace current() { return new CullFace(isEnabled()); }
	public static CullFace cw() { return NORMAL_CW.clone(); }
	public static CullFace ccw() { return NORMAL_CCW.clone(); }
	
	public CullFace setCullFace(GLFace face) { this.cull = face; return this; }
	public CullFace setFrontFace(FaceWinding winding) { this.winding = winding; return this; }
}
