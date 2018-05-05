package com.Engine.RenderEngine.New_Pipeline.FBO;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_RGB4;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA16;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_STENCIL;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT16;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT24;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DEPTH24_STENCIL8;
import static org.lwjgl.opengl.GL30.GL_DEPTH32F_STENCIL8;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL;
import static org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FLOAT_32_UNSIGNED_INT_24_8_REV;
import static org.lwjgl.opengl.GL30.GL_STENCIL_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_STENCIL_INDEX8;
import static org.lwjgl.opengl.GL30.GL_UNSIGNED_INT_24_8;

public final class FBO_Types {
	private FBO_Types() { }
	
	public static enum Attachment {
		ColourBuffer(GL_COLOR_ATTACHMENT0, 15, GL_COLOR_BUFFER_BIT, TargetFormat.Colour),
		DepthBuffer(GL_DEPTH_ATTACHMENT, 0, GL_DEPTH_BUFFER_BIT, TargetFormat.Depth),
		StencilBuffer(GL_STENCIL_ATTACHMENT, 0, GL_STENCIL_BUFFER_BIT, TargetFormat.Stencil),
		DepthStencilBuffer(GL_DEPTH_STENCIL_ATTACHMENT, 0, GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT, TargetFormat.DepthStencil);
		
		private TargetFormat format;
		private int glAttchment, maxIndex, glClearBit;
		private Attachment(int glAttachment, int maxIndex, int glClearBit, TargetFormat format) {
			this.glAttchment = glAttachment;
			this.glClearBit = glClearBit;
			this.maxIndex = maxIndex;
			this.format = format;
		}
		
		public TargetFormat getFormat() { return format; }
		
		public int glAttchment() { return glAttchment; }
		public int glClearBit() { return glClearBit; }
		public int getMaxIndex() { return maxIndex; }
		
		public int maskClearBit(int mask) { return mask | glClearBit; }
	}
	
	public static enum TargetFormat {
		Colour(GL_RGBA, 
				GL_RGBA16, GL_FLOAT,
				GL_RGBA8,  GL_UNSIGNED_BYTE,
				GL_RGB4,   GL_UNSIGNED_BYTE
			),
		
		Depth(GL_DEPTH_COMPONENT, 
				GL_DEPTH_COMPONENT32, GL_UNSIGNED_INT,
				GL_DEPTH_COMPONENT24, GL_UNSIGNED_INT,
				GL_DEPTH_COMPONENT16, GL_UNSIGNED_INT
			),
		
		Stencil(GL_STENCIL, 
				GL_STENCIL_INDEX8, GL_UNSIGNED_BYTE,
				GL_STENCIL_INDEX8, GL_UNSIGNED_BYTE,
				GL_STENCIL_INDEX8, GL_UNSIGNED_BYTE
			),
		
		DepthStencil(GL_DEPTH_STENCIL, 
				GL_DEPTH32F_STENCIL8, GL_FLOAT_32_UNSIGNED_INT_24_8_REV,
				GL_DEPTH24_STENCIL8,  GL_UNSIGNED_INT_24_8,
				GL_DEPTH24_STENCIL8,  GL_UNSIGNED_INT_24_8
			);
		
		private int external, max, norm, min;
		private int typeMax, typeNorm, typeMin;
		
		private TargetFormat(int external, int max, int tMax, int norm, int tNorm, int min, int tMin) {
			this.typeMax = tMax; this.typeNorm = tNorm; this.typeMin = tMin;
			this.max = max; this.norm = norm; this.min = min;
			this.external = external;
		}
		
		public int getExternal() { return external; }
		
		public int getMax() { return max; }
		public int getDefualt() { return norm; }
		public int getMin() { return min; }
		
		public int getFormat(TargetLevel level) {
			switch(level) {
				case Min: return getMin();
				case Default: return getDefualt();
				case Max: return getMax();
				
				default: throw new IllegalArgumentException(level + " is not a valid InitLevel");
			}
		}
		
		public int getStorageType(TargetLevel level) {
			switch(level) {
				case Min: return typeMax;
				case Default: return typeNorm;
				case Max: return typeMin;
				
				default: throw new IllegalArgumentException(level + " is not a valid InitLevel");
			}
		}
		
		public static enum TargetLevel { Min, Default, Max; }
	}
}
