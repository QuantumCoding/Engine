package com.Engine.Demo;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import com.Engine.Demo.Input.CameraMovement;
import com.Engine.RenderEngine.Font.Font;
import com.Engine.RenderEngine.GUI.UIBase;
import com.Engine.RenderEngine.GUI.Components.UIButton;
import com.Engine.RenderEngine.GUI.Shader.UIGraphics;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.Attachment;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderBuffer;
import com.Engine.RenderEngine.New_Pipeline.Scene.Scene;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.RenderEngine.Window.Window;
import com.Engine.Util.Vectors.Vector3f;

public class TestingUI {
	public static void main(String[] args) throws LWJGLException, IOException {
		Window window = new Window();
		window.initDisplay(600, 600, false);
		window.setFPS(120);
		
		Camera camera = new Camera(70, window.getAspectRatio(), 0.3f, 1000);
		CameraMovement movement = new CameraMovement(camera, 5f, 1.5f, 0.1f);
		Mouse.setGrabbed(false);
//		
// ------------------------------------------- GUIs -------------------------------------------------- \\
	
		UIBase button = new UIButton("Test Button fg").setX(0).setY(100).setWidth(150*1).setHeight(50);
		button.updateUI();
		
		Texture2D testTexture = new Texture2D(ImageIO.read(TestingUI.class.getResource("/textures/testButtonUIs.png")));
		testTexture.bind();
			testTexture.setDefaultProperties();
// ------------------------------------------- FBOs -------------------------------------------------- \\

		FBO multiFBO = new FBO(window.getWidth(), window.getHeight(), 4);
		multiFBO.attach(new RenderBuffer(multiFBO), Attachment.ColourBuffer);
		multiFBO.attach(new RenderBuffer(multiFBO), Attachment.DepthBuffer);

		Scene scene = new Scene();
		Shader.setSkyColor(new Vector3f(.75f));
		FBO.SCREEN_FBO.screenResized(window);
		
// ------------------------------------------ Loop --------------------------------------------------- \\
		
		double sum = 0;
		double frameTimeAvg = 0.0;
		int frameAvgCounter = 0;	
		while(!window.isCloseRequested()) {
			
		// ---------------------------- -------------- ---------------------------- \\
		//	--------------------------- Keyboard Input ---------------------------- \\		
		// ---------------------------- -------------- ---------------------------- \\
			
			movement.update((float) window.getFrameTime());
			
		// ---------------------------- ----------------- ---------------------------- \\
		// ---------------------------- Render all Models ---------------------------- \\
		// ---------------------------- ----------------- ---------------------------- \\
			
//			sum += window.getFrameTime() / 10;
//			button.setWidth((float) Math.abs(Math.cos(sum * 11)) * 100 + 25).setHeight((float) Math.abs(Math.sin(sum * 17)) * 100 + 25);
//			button.setX((float) Math.abs(Math.cos(sum * 13)) * 100 + 25).setY((float) Math.abs(Math.sin(sum * 19)) * 100 + 25);
			
			multiFBO.bindDraw(); 
				button.render();
			
				testTexture.bind(0);
				scene.render(camera, UIGraphics.RENDERER, Font.Text2DShader.getRenderer());
			FBO.unbindDraw(); 

			multiFBO.resolve();
			
		// ---------------------------- ----------------- ---------------------------- \\
		// ---------------------------- Update the Window ---------------------------- \\
		// ---------------------------- ----------------- ---------------------------- \\
			
			Font.Text2DShader.getRenderer().clear();
			UIGraphics.RENDERER.clear();
			
			window.update();
			
			if(window.wasResized()) {
				multiFBO.resize(window.getWidth(), window.getHeight());
				FBO.SCREEN_FBO.screenResized(window);
				camera.setAspect(window.getAspectRatio());
				camera.recalculate();
			}
			
			if(frameAvgCounter >= 50) {
				frameTimeAvg /= (double) frameAvgCounter;
				window.setTitle("New Pipeline V3.4 | FPS: " + (int)(1.0/frameTimeAvg) +
						"\t FrameTime: " + (float)frameTimeAvg);
				
				frameTimeAvg = 0.0;
				frameAvgCounter = 0;
			} else {
				frameTimeAvg += window.getFrameTime();
				frameAvgCounter ++;
			}
		}
		
		testTexture.cleanUp();
		
		Font.Text2DShader.cleanUp();
		Font.BillboardShader.cleanUp();

		window.destroy();
	}
}
