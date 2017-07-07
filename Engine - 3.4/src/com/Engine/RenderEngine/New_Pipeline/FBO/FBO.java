package com.Engine.RenderEngine.New_Pipeline.FBO;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_READ_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.Attachment;
import com.Engine.RenderEngine.Window.Window;

public class FBO {
	public static final class ScreenFBO extends FBO {
		private ScreenFBO() { 
			super(0, 0, -1); 
			
			super.id = 0;
			super.bitMask = GL_COLOR_BUFFER_BIT; 
			
			super.drawBuffers = BufferUtils.createIntBuffer(1);
			super.drawBuffers.put(GL_BACK);
			super.drawBuffers.flip();
		}
		
		public void screenResized(Window window) {
			super.width = window.getWidth();
			super.height = window.getHeight();
		}
		
		public void attach(IRenderTarget target, Attachment attachment, int index) { notifyType(); }
		public FBO resolve(FBO fbo) { notifyType(); return null; }
		public void cleanUp() { notifyType(); }
		
		private void notifyType() { throw new IllegalStateException("Screen FBO cannot be changed"); }
	};
	
	public static final ScreenFBO SCREEN_FBO = new ScreenFBO();
	
//	------
	
	public static boolean FBOS_CAN_INITALISE_TARGETS = true;
	
	private IntBuffer drawBuffers;
	private IRenderTarget[][] attachemnts;
	private int multiSampleCount;
	private int width, height;
	private int id;
	
	private int bitMask;
	
	public FBO(int width, int height) { this(width, height, -1); }
	public FBO(int width, int height, int multiSampleCount) {
		this.width = width;
		this.height = height;
		this.multiSampleCount = multiSampleCount;
		
		if(isMultiSampled()) glEnable(GL_MULTISAMPLE);
		
		id = glGenFramebuffers();
		drawBuffers = BufferUtils.createIntBuffer(0);
		attachemnts = new IRenderTarget[Attachment.values().length][];
		
		drawBuffers.flip();
	}
	
	public void bind() { bind(GL_FRAMEBUFFER); }
	public void bindRead() { bind(GL_READ_FRAMEBUFFER); }
	public void bindDraw() { bind(GL_DRAW_FRAMEBUFFER); }
	
	public void bindRead(int index) { bind(GL_READ_FRAMEBUFFER, index); }
	public void bindDraw(int index) { bind(GL_DRAW_FRAMEBUFFER, index); }

	public void bind(int glTarget) { bind(glTarget, this, -1); }
	public void bind(int glTarget, int index) { bind(glTarget, this, index); }
	
	protected static void bind(int glTarget, FBO fbo, int index) {
		glBindFramebuffer(glTarget, fbo.id);
		glViewport(0, 0, fbo.width, fbo.height);
		
		if(glTarget == GL_DRAW_FRAMEBUFFER && fbo != SCREEN_FBO)
			if(index < 0) glDrawBuffers(fbo.drawBuffers); 
			else glDrawBuffers(GL_COLOR_ATTACHMENT0 + index);
		
		else if(glTarget == GL_READ_FRAMEBUFFER && fbo != SCREEN_FBO) 
			if(index < 0) glReadBuffer(GL_COLOR_ATTACHMENT0); 
			else glReadBuffer(GL_COLOR_ATTACHMENT0 + index);
		
		else
			glDrawBuffers(fbo.drawBuffers);
	}
	
	public static void unbind() { unbind(GL_FRAMEBUFFER); }
	public static void unbindRead() { unbind(GL_READ_FRAMEBUFFER); }
	public static void unbindDraw() { unbind(GL_DRAW_FRAMEBUFFER); }
	
	protected static void unbind(int glTarget) { SCREEN_FBO.bind(glTarget); }
	
	public void clear() { glClear(bitMask); }
	
	public void attach(IRenderTarget target, Attachment attachment) { attach(target, attachment, 0); }
	public void attach(IRenderTarget target, Attachment attachment, int index) {
		if(index > attachment.getMaxIndex()) throw new IllegalArgumentException(
				"Index out of range for " + attachment + "; max index is " + attachment.getMaxIndex());

		if(target.getFormat() == null && FBOS_CAN_INITALISE_TARGETS) 
			target.init(attachment.getFormat());
		
		else if(target.getFormat() != attachment.getFormat()) 
			throw new IllegalStateException("RenderTarget must be initialized for use as a " +
					attachment + " before being attached as one");
		
		if(target.isMultiSampled() != this.isMultiSampled() ||
				(this.isMultiSampled() && this.multiSampleCount != target.getMultiSampleCount()))
			throw new IllegalStateException("RenderTagets must use the same level of Multi-Sampling as the FBO");
		
		IRenderTarget[] array = attachemnts[attachment.ordinal()];
		if(array == null) array = attachemnts[attachment.ordinal()] = new IRenderTarget[index + 1];
		if(array.length < index) {
			IRenderTarget[] arrayExpanded = new IRenderTarget[index + 1];
			System.arraycopy(array, 0, arrayExpanded, 0, array.length);
			array = attachemnts[attachment.ordinal()] = arrayExpanded;
		}
		
		if(array[index] != null) {
			// Old Target - Dispose???
			array[index].cleanUp();
			
		} else if(attachment == Attachment.ColourBuffer) {
			IntBuffer newDrawBuffers = BufferUtils.createIntBuffer(drawBuffers.capacity() + 1);
			newDrawBuffers.put(drawBuffers);
			newDrawBuffers.put(attachment.glAttchment() + index);
			
			drawBuffers = newDrawBuffers;
			drawBuffers.flip();
		}

		bind();
			target.attach(attachment.glAttchment() + index);
		unbind();
		
		array[index] = target;
		bitMask = attachment.maskClearBit(bitMask);
	}
	

	public IRenderTarget getAttachment(Attachment attachment) { return getAttachment(attachment, 0); } 
	public IRenderTarget getAttachment(Attachment attachment, int index) {
		if(attachemnts[attachment.ordinal()] == null) return null;
		if(attachemnts[attachment.ordinal()].length < index) return null;
		return attachemnts[attachment.ordinal()][index];
	} 
	
	public FBO resolve() { return resolve(null); }								
	public FBO resolve(FBO dest) { return resolve(dest, -1, -1); }				// Resolve all to FBO
	public FBO resolve(FBO dest, int srcIndex, int destIndex) {					// Resolve selected to FBO at selected
		if(dest == null) dest = SCREEN_FBO;
		
		bindRead(srcIndex); dest.bindDraw(destIndex); 
			glBlitFramebuffer(0, 0, width, height, 0, 0, dest.width, dest.height, bitMask & dest.bitMask, GL_NEAREST);
		unbindRead(); unbindDraw();  
		
		return dest;
	}
	
	public void cleanUp() {
		glDeleteFramebuffers(id);
		
		for(IRenderTarget[] tagets : attachemnts)
		for(IRenderTarget taget : tagets)
			taget.cleanUp();
	}

	public int getWidth() { return width; }
	public int getHeight() { return height; }

	public float getAspect() { return (float) width / height; }
	
	public int getMultiSampleCount() { return multiSampleCount; }
	public boolean isMultiSampled() { return multiSampleCount > 0; }
}
