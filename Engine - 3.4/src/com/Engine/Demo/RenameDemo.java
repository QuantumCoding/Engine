package com.Engine.Demo;

import org.lwjgl.LWJGLException;

import com.Engine.Demo.Input.CameraMovement;
import com.Engine.RenderEngine.Camera.PerspectiveCamera;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.Window.Window;
import com.Engine.Util.Time;

public class RenameDemo {
	public static void main(String[] args) throws LWJGLException {
	// ================================ Window ================================ \\
	
		Window window = new Window();
		window.initDisplay(600, 600, false);
		window.setTitle("<Rename> Demo");
		window.setFPS(60);
		
		System.out.println(Window.getOpenGLVersion());
		System.out.println(Window.getGLSLVersion());

	// ================================ Camera ================================ \\
		
		PerspectiveCamera camera = new PerspectiveCamera(85, window.getAspectRatio(), .3f, 1000);
		CameraMovement movement = new CameraMovement(camera, 5f, 1.5f, 0.1f);

	// ================================ FPS Tracking ================================ \\
		
		int frameSampleIndex = 0;
		long frameStartTime = Time.getSystemTime();
		long[] frameTimeSamples = new long[window.getFPS() / 2];
		
	// ================================ Main Loop ================================ \\
	
		while(!window.isCloseRequested()) {
		// ================================ Input update ================================ \\
			
			movement.update((float) window.getFrameTime());
			

		// ================================ Window update ================================ \\
			window.update();
			
			if(window.wasResized()) {
				FBO.SCREEN_FBO.screenResized(window);
				camera.setAspect(window.getAspectRatio());
				camera.recalculate();
			}
			
			frameTimeSamples[frameSampleIndex ++] = frameStartTime - Time.getSystemTime();
			frameSampleIndex %= frameTimeSamples.length;
		}
		
		window.destroy();
	}
}