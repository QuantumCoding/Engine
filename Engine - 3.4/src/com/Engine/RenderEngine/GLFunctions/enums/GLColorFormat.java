package com.Engine.RenderEngine.GLFunctions.enums;

import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGB10_A2;
import static org.lwjgl.opengl.GL11.GL_RGB4;
import static org.lwjgl.opengl.GL11.GL_RGB5_A1;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA12;
import static org.lwjgl.opengl.GL11.GL_RGBA16;
import static org.lwjgl.opengl.GL11.GL_RGBA2;
import static org.lwjgl.opengl.GL11.GL_RGBA4;
import static org.lwjgl.opengl.GL11.GL_RGBA8;

public enum GLColorFormat {
	RGB(8, 0, GL_RGB), RGBA(8, GL_RGBA),
	
	RGB4(4, 0, GL_RGB4), RGB5(5, 0, GL_RGB4), RGB8(8, 0, GL_RGB4), 
	RGB10(10, 0, GL_RGB4), RGB12(12, 0, GL_RGB4), RGB16(16, 0, GL_RGB4),
	
	RGBA2(2, GL_RGBA2), RGBA4(4, GL_RGBA4), RGBA8(8, GL_RGBA8),
	RGBA12(12, GL_RGBA12), RGBA16(16, GL_RGBA16),
	
	RGB5_A1(5, 1, GL_RGB5_A1), RGB10_A2(10, 2, GL_RGB10_A2);
	
	String order;
	
	byte r, g, b, a; // Channel Bit-Depth
	int bitDepth;
	
	int glEnum;

	private GLColorFormat(int rgba, int glEnum)		  { this(null, rgba, glEnum); }
	private GLColorFormat(int rgb, int a, int glEnum) { this(null, rgb, a, glEnum); }
	
	private GLColorFormat(String order, int rgba, int glEnum)		  { this(order, rgba, rgba, glEnum); }
	private GLColorFormat(String order, int rgb, int a, int glEnum) { this(order, rgb, rgb, rgb, a, glEnum); }
	
	private GLColorFormat(String order, int r, int g, int b, int a, int glEnum) {
		this.order = order == null ? toString() : order;
		
		this.r = (byte) r;		this.g = (byte) g;
		this.b = (byte) b;		this.a = (byte) a;
		
		this.bitDepth = r + g + b + a;
		this.glEnum = glEnum;
	}
	
	public int bitDepth()  { return bitDepth; }
	public int byteDepth() { return (int) Math.ceil(bitDepth / 8f); }
	
	public int gl() { return glEnum; }
}
