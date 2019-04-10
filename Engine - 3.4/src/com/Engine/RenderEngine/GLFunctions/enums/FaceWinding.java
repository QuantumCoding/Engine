package com.Engine.RenderEngine.GLFunctions.enums;

import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_CW;

public enum FaceWinding {
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
