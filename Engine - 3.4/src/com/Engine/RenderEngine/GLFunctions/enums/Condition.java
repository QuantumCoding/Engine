package com.Engine.RenderEngine.GLFunctions.enums;

import static org.lwjgl.opengl.GL11.GL_ALWAYS;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_GEQUAL;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LESS;
import static org.lwjgl.opengl.GL11.GL_NEVER;
import static org.lwjgl.opengl.GL11.GL_NOTEQUAL;

public enum Condition {
	Always(GL_ALWAYS),
	Never(GL_NEVER),
	
	LessThen(GL_LESS),
	LessThenOrEqual(GL_LEQUAL),
	GreaterThen(GL_GREATER),
	GreaterThenOrEqual(GL_GEQUAL),
	
	Equal(GL_EQUAL),
	NotEqual(GL_NOTEQUAL);
	
	private int glValue;
	private Condition(int glValue) { this.glValue = glValue; }
	public int getValue() { return glValue; }
	
	public static Condition lookUp(int glValue) {
		for(Condition condition : values()) if(condition.glValue == glValue) return condition;
		throw new IllegalArgumentException(glValue + " is not a valid Condition");
	}
}
