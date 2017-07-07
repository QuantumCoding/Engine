package com.Engine.RenderEngine.New_Pipeline.FBO;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;

import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.TargetFormat;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.TargetFormat.TargetLevel;

public class RenderBuffer implements IRenderTarget {
	private int multiSampleCount;
	private int width, height;
	private int id;
	
	private TargetFormat format;
	private TargetLevel level;
	
	public RenderBuffer(FBO fbo) { this(fbo.getWidth(), fbo.getHeight(), fbo.getMultiSampleCount()); }
	public RenderBuffer(int width, int height) { this(width, height, -1); }
	
	public RenderBuffer(int width, int height, int multiSampleCount) {
		this.multiSampleCount = multiSampleCount;
		this.width = width;
		this.height = height;
	
		id = glGenRenderbuffers();
	}
	
	public void bind() { glBindRenderbuffer(GL_RENDERBUFFER, id); }
	public static void unbind() { glBindRenderbuffer(GL_RENDERBUFFER, 0); }

	public RenderBuffer init(TargetFormat format, TargetLevel level) {
		if(format == this.format && level == this.level) return this;
		
		bind();
			if(multiSampleCount > 0)
				glRenderbufferStorageMultisample(GL_RENDERBUFFER, multiSampleCount, format.getFormat(level), width, height);
			else
				glRenderbufferStorage(GL_RENDERBUFFER, format.getFormat(level), width, height);
		unbind();
		
		this.format = format;
		this.level = level;
		return this;
	}
	
	public void attach(int attachment) {
		bind();
			glFramebufferRenderbuffer(GL_FRAMEBUFFER, attachment, GL_RENDERBUFFER, id);
		unbind();
	}

	public void cleanUp() {
		glDeleteRenderbuffers(id);
	}
	
	public TargetFormat getFormat() { return format; }
	public int getMultiSampleCount() { return multiSampleCount; }
}
