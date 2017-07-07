package com.Engine.RenderEngine.New_Pipeline.FBO;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_2D_MULTISAMPLE;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.TargetFormat;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.TargetFormat.TargetLevel;
import com.Engine.RenderEngine.Textures.Texture;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;

public class RenderTexture extends Texture implements IRenderTarget {
	private int multiSampleCount;
	private int width, height;
	
	private int glType;
	
	private TargetFormat format;
	private TargetLevel level;
	
	public RenderTexture(FBO fbo) { this(fbo.getWidth(), fbo.getHeight(), fbo.getMultiSampleCount()); }
	public RenderTexture(int width, int height) { this(width, height, -1); }
	
	public RenderTexture(int width, int height, int multiSampleCount) {
		super();
		
		this.multiSampleCount = multiSampleCount;
		this.width = width;
		this.height = height;
		
		this.glType = isMultiSampled() ? GL_TEXTURE_2D_MULTISAMPLE : GL_TEXTURE_2D;
	}

	public RenderTexture init(TargetFormat format, TargetLevel level) {
		if(format == this.format && level == this.level) return this;
		
		bind();
			if(multiSampleCount > 0)
				glTexImage2DMultisample(glType, multiSampleCount, format.getFormat(level), width, height, true);
			else
				glTexImage2D(glType, 0, format.getFormat(level), width, height, 0, format.getExternal(), GL_UNSIGNED_BYTE, (ByteBuffer) null);
		
	        glTexParameteri(glType, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	        glTexParameteri(glType, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	        glTexParameteri(glType, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	        glTexParameteri(glType, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		unbind();
		
		this.format = format;
		this.level = level;
		return this;
	}

	public void attach(int attachment) {
		bind();
			glFramebufferTexture(GL_FRAMEBUFFER, attachment, textureId, 0);
		unbind();
	}

	public TargetFormat getFormat() { return format; }
	public int getMultiSampleCount() { return multiSampleCount; }

	public int getGLTextureType() { return glType; }
	
	public Texture2D cloneTo2D() { return new Texture2D(textureId, new Vector2f(width, height)); }
}
