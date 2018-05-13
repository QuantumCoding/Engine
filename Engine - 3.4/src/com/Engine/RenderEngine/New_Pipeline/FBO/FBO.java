package com.Engine.RenderEngine.New_Pipeline.FBO;

import static org.lwjgl.opengl.GL11.GL_BACK_LEFT;
import static org.lwjgl.opengl.GL11.GL_BACK_RIGHT;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DOUBLEBUFFER;
import static org.lwjgl.opengl.GL11.GL_FRONT_RIGHT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_READ_BUFFER;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STEREO;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetBoolean;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL11.glStencilMask;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.opengl.GL20.GL_DRAW_BUFFER0;
import static org.lwjgl.opengl.GL20.GL_DRAW_BUFFER1;
import static org.lwjgl.opengl.GL20.GL_MAX_DRAW_BUFFERS;
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
import java.util.Arrays;

import org.lwjgl.BufferUtils;

import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.Attachment;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Window.Window;
import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector2f;

public class FBO {
	public static final int MAX_DRAW_BUFFERS = glGetInteger(GL_MAX_DRAW_BUFFERS);
	
	public static final class ScreenFBO extends FBO {
		public static final boolean IS_DOUBLE_BUFFERED = glGetBoolean(GL_DOUBLEBUFFER);
		public static final boolean IS_STEREOSCOPIC = glGetBoolean(GL_STEREO);
		
		private ScreenFBO() { 
			super(0, 0, -1); 
			
			super.id = 0;
			super.bitMask = GL_COLOR_BUFFER_BIT; 
			
			super.readBuffer = glGetInteger(GL_READ_BUFFER);
			
			for(int i = 0; i < MAX_DRAW_BUFFERS; i ++) {
				super.drawBuffersRaw[i] = glGetInteger(GL_DRAW_BUFFER0 + i);
			}
		}
		
		public void screenResized(Window window) {
			super.width = window.getWidth();
			super.height = window.getHeight();
			
			Shader.setOrthographicMatrix(MatrixUtil.initOrthographicMatrix(0, super.width, super.height, 0, -1, 1));
		}
		
		public void attach(IRenderTarget target, Attachment attachment, int index) { notifyType(); }
		public FBO resolve(FBO fbo) { notifyType(); return null; }
		public void cleanUp() { notifyType(); }
		
		private void notifyType() { throw new IllegalStateException("Screen FBO cannot be changed"); }
		
		private void checkRenderTarget(int target) {
			if(!ScreenFBO.IS_DOUBLE_BUFFERED && (target == GL_BACK_LEFT || target == GL_BACK_RIGHT))
				throw new IllegalArgumentException("Default FBO is not Double Buffered! Back buffers cannot be used");

			if(!ScreenFBO.IS_STEREOSCOPIC && (target == GL_FRONT_RIGHT || target == GL_BACK_RIGHT))
				throw new IllegalArgumentException("Default FBO is not Stereoscopic! Right buffers cannot be used");
		}
		
		public void setReadBuffer(int target) { 
			if(target == super.readBuffer) return;

			checkRenderTarget(target);
			super.readBuffer = target;
			super.readBuffersChanged = true;
		}
		
		public void setDrawBuffers(int... indexOrder) {
			if(indexOrder.length > MAX_DRAW_BUFFERS)  throw new IllegalArgumentException(
					"Can only have " + MAX_DRAW_BUFFERS + " active at a time; not " + indexOrder.length);
			
			for(int i = 0; i < MAX_DRAW_BUFFERS; i ++) {
				int newValue = GL_NONE;
				
				if(i < indexOrder.length) {
					newValue = indexOrder[i];
					checkRenderTarget(newValue);
				}
				
				if(super.drawBuffersRaw[i] != newValue) {
					super.drawBuffersChanged = true;
					super.drawBuffersRaw[i] = newValue;
				}
			}
		}
	};
	
	public static final ScreenFBO SCREEN_FBO = new ScreenFBO();
	
//	------

	private static FBO currentDraw;
	private static FBO currentRead;
	private static boolean allowMasterAccess = true;
	
	public static void allowMasterAccess(boolean allow) { FBO.allowMasterAccess = allow; }
	
	public static void clear_bound() {
		if(!allowMasterAccess) return;
		currentDraw.clear();
	}
	
	public static FBO currentDraw() { return currentDraw; }
	public static FBO currentRead() { return currentRead; }
	
//	------
	
	public static boolean FBOS_CAN_INITALISE_TARGETS = true;
	
	private int readBuffer;
	private boolean readBuffersChanged;
	
	private int[] drawBuffersRaw;
	private IntBuffer drawBuffers;
	private boolean drawBuffersChanged;
	
	private IRenderTarget[][] attachemnts;
	
	private int multiSampleCount;
	private float origWidth, origHeight;
	private int width, height;
	private int id;
	
	private int bitMask;
	
	public FBO(int width, int height) { this(width, height, -1); }
	public FBO(int width, int height, int multiSampleCount) {
		this.origWidth = this.width = width;
		this.origHeight = this.height = height;
		this.multiSampleCount = multiSampleCount;
		
		if(isMultiSampled()) glEnable(GL_MULTISAMPLE);
		
		id = glGenFramebuffers();
		
		drawBuffersRaw = new int[MAX_DRAW_BUFFERS];
		drawBuffers = BufferUtils.createIntBuffer(MAX_DRAW_BUFFERS);
		attachemnts = new IRenderTarget[Attachment.values().length][];
		
		Arrays.fill(drawBuffersRaw, GL_NONE);
		drawBuffers.put(drawBuffersRaw);
		drawBuffers.flip();
	}
	
	public void setReadBuffer(int attachmentIndex) { 
		int newValue = GL_COLOR_ATTACHMENT0 + attachmentIndex;
		if(newValue == this.readBuffer) return;
		this.readBuffer = newValue;
		
		readBuffersChanged = true;
	}
	
	public void setDrawBuffers(int... indexOrder) {
		if(indexOrder.length > MAX_DRAW_BUFFERS) 
			throw new IllegalArgumentException("Can only have " + MAX_DRAW_BUFFERS + " active at a time; not " + indexOrder.length);
		
		for(int i = 0; i < MAX_DRAW_BUFFERS; i ++) {
			int newValue = i >= indexOrder.length || indexOrder[i] < 0 ? GL_NONE : GL_COLOR_ATTACHMENT0 + indexOrder[i];
			
			if(drawBuffersRaw[i] != newValue) {
				drawBuffersChanged = true;
				drawBuffersRaw[i] = newValue;
			}
		}
	}
	
	public void bind() { bind(GL_FRAMEBUFFER); }
	public void bindRead() { bind(GL_READ_FRAMEBUFFER); }
	public void bindDraw() { bind(GL_DRAW_FRAMEBUFFER); }
	
	public void bindRead(int index)    { setReadBuffer(index);  bind(GL_READ_FRAMEBUFFER); }
	public void bindDraw(int... index) { setDrawBuffers(index); bind(GL_DRAW_FRAMEBUFFER); }

	public void bind(int glTarget) { bind(glTarget, this); }
	
	protected static void bind(int glTarget, FBO fbo) {
		glBindFramebuffer(glTarget, fbo.id);
		
		boolean both = false;
		switch(glTarget) {
			case GL_FRAMEBUFFER: both = true;
			
			case GL_DRAW_FRAMEBUFFER: 
				currentDraw = fbo; 
				glViewport(0, 0, fbo.width, fbo.height);
				
				if(fbo.drawBuffersChanged) {
					fbo.drawBuffers.clear(); fbo.drawBuffers.put(fbo.drawBuffersRaw); fbo.drawBuffers.flip();
					fbo.drawBuffersChanged = false;
					glDrawBuffers(fbo.drawBuffers); 
				}
			if(!both) break;
			
			case GL_READ_FRAMEBUFFER: 
				currentRead = fbo; 
				
				if(fbo.readBuffersChanged) {
					fbo.readBuffersChanged = false;
					glReadBuffer(fbo.readBuffer);
				}
			break;
			
			default: throw new IllegalArgumentException(glTarget + " is not a valid FBO Binding Target");
		}
	}
	
	public static void unbind() { unbind(GL_FRAMEBUFFER); }
	public static void unbindRead() { unbind(GL_READ_FRAMEBUFFER); }
	public static void unbindDraw() { unbind(GL_DRAW_FRAMEBUFFER); }
	
	protected static void unbind(int glTarget) { SCREEN_FBO.bind(glTarget); }
	
	public void clear() { if((bitMask | GL_STENCIL_BUFFER_BIT) != 0) glStencilMask(~0); glClear(bitMask); }
	
	public void attach(IRenderTarget target, Attachment attachment) { attach(target, attachment, 0); }
	public void attach(IRenderTarget target, Attachment attachment, int index) {
		if(index >= attachment.getMaxIndex()) throw new IllegalArgumentException(
				"Index out of range for " + attachment + "; Max index for " + attachment + " is " + attachment.getMaxIndex());

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
		if(array.length < index + 1) {
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
	
	public void resize(int width, int height) {
		this.width = width; this.height = height;
		
		float scaleX = width / origWidth;
		float scaleY = height / origHeight;
		
		for(IRenderTarget[] tagets : attachemnts)
		if(tagets != null) for(IRenderTarget taget : tagets)
			taget.resize(scaleX, scaleY);
	}
	
	public void cleanUp() {
		glDeleteFramebuffers(id);
		
		for(IRenderTarget[] tagets : attachemnts)
		if(tagets != null) for(IRenderTarget taget : tagets)
			taget.cleanUp();
	}

	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public Vector2f getSize() { return new Vector2f(width, height); }

	public float getAspect() { return (float) width / height; }
	
	public int getMultiSampleCount() { return multiSampleCount; }
	public boolean isMultiSampled() { return multiSampleCount > 0; }
}
