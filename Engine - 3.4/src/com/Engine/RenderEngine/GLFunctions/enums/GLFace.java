package com.Engine.RenderEngine.GLFunctions.enums;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;

public enum GLFace {
	Front(GL_FRONT), Back(GL_BACK),
	Both(GL_FRONT_AND_BACK);
	
	private int glValue;
	private GLFace(int glValue) { this.glValue = glValue; }
	public int getValue() { return glValue; }
	
	public static GLFace lookUp(int glValue) {
		for(GLFace side : values()) if(side.glValue == glValue) return side;
		throw new IllegalArgumentException(glValue + " is not a valid GLFace");
	}
}
