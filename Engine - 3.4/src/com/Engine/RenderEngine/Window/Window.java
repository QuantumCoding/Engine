package com.Engine.RenderEngine.Window;

import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION;
import static org.lwjgl.opengl.GL43.GL_DEBUG_OUTPUT;
import static org.lwjgl.opengl.GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS;
import static org.lwjgl.opengl.GL43.GL_DEBUG_SEVERITY_HIGH;
import static org.lwjgl.opengl.GL43.GL_DEBUG_SEVERITY_NOTIFICATION;
import static org.lwjgl.opengl.GL43.glDebugMessageCallback;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.KHRDebugCallback;
import org.lwjgl.opengl.PixelFormat;

import com.Engine.Util.Time;
import com.Engine.Util.Vectors.Vector2f;

public class Window {
	private static DisplayMode DEFAULT_FULLSCREEN_DISPLAY_MODE = null;
	static {
		try {
			Vector2f largestDimention = new Vector2f();
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			for(DisplayMode mode : modes) {
				Vector2f size = new Vector2f(mode.getWidth(), mode.getHeight());
				if(size.greaterThen(largestDimention)) {
					largestDimention = size;
					DEFAULT_FULLSCREEN_DISPLAY_MODE = mode;
				}
			}
		} catch(LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	public static final int DEFAULT_FPS = 60;
		
	private int fps;
	private String title;
	private ByteBuffer[] icon;
	
	private double frameTime;
	private long lastFrameTime;
	
	private DisplayMode displayMode;
	private ContextAttribs attribs;
	
	public Window() {
		fps = DEFAULT_FPS;
	}
	
	public void initDisplay() throws LWJGLException 					 { initDisplay(DEFAULT_WIDTH, DEFAULT_HEIGHT); }
	public void initDisplay(int width, int height) throws LWJGLException { initDisplay(width, height, false); }
	public void initDisplay(int width, int height, boolean forceToSize) throws LWJGLException {
		if(Display.isCreated()) destroy();

		if(forceToSize) {
			displayMode = new DisplayMode(width, height);
			
		} else {
			float error = -1;
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			for(DisplayMode mode : modes) {
				float newError = new Vector2f(width - mode.getWidth(), height - mode.getHeight()).length();
				if(newError < error || error < 0) {
					error = newError;
					displayMode = mode;
				}
			}
		}
		
		attribs = new ContextAttribs(3, 3).withForwardCompatible(true).withProfileCore(true);

		Display.setResizable(true);
		Display.setDisplayMode(displayMode);
		Display.create(new PixelFormat(), attribs);
		
//		AL.create();
		Mouse.create();
		Keyboard.create();
//		Controllers.create();
		
		setupErrorHandle();
	}
	
	private void setupErrorHandle() {
		KHRDebugCallback.Handler errorHandlerBeta = null;
		try {
			Method getHandler = KHRDebugCallback.class.getDeclaredMethod("getHandler");
			getHandler.setAccessible(true);
			errorHandlerBeta = (KHRDebugCallback.Handler) getHandler.invoke(new KHRDebugCallback());
		} catch(NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) { 
			e.printStackTrace(); 
		}
		
		KHRDebugCallback.Handler errorHandler = errorHandlerBeta;
		
		glEnable(GL_DEBUG_OUTPUT);
		glEnable(GL_DEBUG_OUTPUT_SYNCHRONOUS);
		glDebugMessageCallback(new KHRDebugCallback(new KHRDebugCallback.Handler() {
			public void handleMessage(int source, int type, int id, int severity, String message) {
				if(severity == GL_DEBUG_SEVERITY_NOTIFICATION) return;
				
				errorHandler.handleMessage(source, type, id, severity, message);
				
				if(severity == GL_DEBUG_SEVERITY_HIGH)
					throw new RuntimeException();
			}
		}));
	}
	
	public void update() {
		Display.update();
		Display.sync(fps);
		
		frameTime = (double)(Time.getSystemTime() - lastFrameTime) / (double)Time.SECONDS;
		lastFrameTime = Time.getSystemTime();
		
		if(wasResized()) {
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}
	}
	
	public double getFrameTime() {
		return frameTime;
	}
	
	public void destroy() {
		Display.destroy();
		
		Mouse.destroy();
		Keyboard.destroy();
		Controllers.destroy();
	}

	public void setFullscreen(boolean fullscreen) throws LWJGLException { 
		if(fullscreen && !Display.getDisplayMode().isFullscreenCapable()) {
			Display.destroy();
			Display.setDisplayMode(DEFAULT_FULLSCREEN_DISPLAY_MODE);
			Display.create(new PixelFormat(), attribs);
			
		} else if(!fullscreen && Display.getDisplayMode() != displayMode) {
			Display.destroy();
			Display.setDisplayMode(displayMode);
			Display.create(new PixelFormat(), attribs);
		}
		
		Display.setFullscreen(fullscreen); 
	}
	
	public void setResizable(boolean resizable) { Display.setResizable(resizable); }
	public void enableVSync(boolean enable) { Display.setVSyncEnabled(enable); }
	
	public void setTitle(String title) { this.title = title; Display.setTitle(title); }
	public void setFPS(int fps) { this.fps = fps; }
	public void setIcon(BufferedImage image) {
		icon = IconLoader.load(image);
		Display.setIcon(icon);
	}
	
	public boolean isCloseRequested() { return Display.isCloseRequested(); }
	public boolean isFullscreen() { return Display.isFullscreen(); }	
	public boolean wasResized() { return Display.wasResized(); }
	
	public String getTitle() { return title; }
	public int getWidth() { return Display.getWidth(); }
	public int getHeight() { return Display.getHeight(); }
	public int getFPS() { return fps; }
	
	public float getAspectRatio() { return (float) getWidth() / getHeight(); }
	
	public static String getOpenGLVersion() {
		return glGetString(GL_VERSION);
	}
	
	public static String getGLSLVersion() {
		return glGetString(GL_SHADING_LANGUAGE_VERSION);
	}
}
