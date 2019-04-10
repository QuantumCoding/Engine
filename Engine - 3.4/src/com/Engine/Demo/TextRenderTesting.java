package com.Engine.Demo;

import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;

import com.Engine.Demo.Input.CameraMovement;
import com.Engine.RenderEngine.Camera.PerspectiveCamera;
import com.Engine.RenderEngine.Font.Font;
import com.Engine.RenderEngine.Font.TextMeshStitcher;
import com.Engine.RenderEngine.Font.Render.TextMesh;
import com.Engine.RenderEngine.Font.Render.TextRenderProperties;
import com.Engine.RenderEngine.GLFunctions.BlendFunc;
import com.Engine.RenderEngine.GLFunctions.DepthTest;
import com.Engine.RenderEngine.Models.ModelLoader;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.Attachment;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderBuffer;
import com.Engine.RenderEngine.Particles.ParticleManager;
import com.Engine.RenderEngine.Shaders.Default.DefaultRenderProperties;
import com.Engine.RenderEngine.Shaders.Default.DefaultShader;
import com.Engine.RenderEngine.Shaders.Default.Light;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.RenderEngine.Window.Window;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;
import com.Engine.Util.Vectors.Vector4f;

public class TextRenderTesting {
	public static void main(String[] args) throws LWJGLException, IOException {
		Window window = new Window();
		window.initDisplay(600, 600, false);
		window.setFPS(120);
		
		
		PerspectiveCamera camera = new PerspectiveCamera(70, window.getAspectRatio(), 0.3f, 1000);
		CameraMovement movement = new CameraMovement(camera, 5f, 1.5f, 0.1f);
		
		DefaultShader defaultShader = new DefaultShader();
		defaultShader.getRenderer().usingFrustumCulling(false);
		
		
		Texture2D tableTexture = new Texture2D(ImageIO.read(RenderTester.class.getResource("/textures/tableTop.png")));
		
// --------------------------------------- Standard Models -------------------------------------------------- \\
		
		Model floorModel = new Model(ModelLoader.loadOBJ("res/models/CubeSingle.obj"));
		floorModel.setShader(defaultShader);
		floorModel.setTexture(tableTexture);


// ------------------------------------------- Text -------------------------------------------------- \\
		
		Font font = Font.loadFont(RenderTester.class.getResourceAsStream("/fonts/ArialM.qFnt"));
		TextMesh mesh = TextMeshStitcher.createMesh("THis is a TEST to see how long this thing can be :) 1928374650", 
				font, 500, 1.1f, null);
		mesh.setShader(Font.Text2DShader);
		
		TextMesh mesh3D = TextMeshStitcher.createMesh("THis is a TEST to see how long this thing can be :) 1928374650", 
				font, 5, window.getAspectRatio(), new Vector2f(2.5, 3));
		
// ------------------------------------------- FBOs -------------------------------------------------- \\
		
		FBO fbo = new FBO(window.getWidth(), window.getHeight());
		fbo.attach(new RenderBuffer(fbo), Attachment.ColourBuffer);
		fbo.attach(new RenderBuffer(fbo), Attachment.DepthBuffer);
		
		FBO.SCREEN_FBO.screenResized(window);
		
// ------------------------------------------ Lights --------------------------------------------------- \\
		
		Light light = new Light(new Vector3f(), new Vector3f(1), new Vector3f(1, 0, 0));
		Light followLight = new Light(new Vector3f(), new Vector3f(1), new Vector3f(1, 0, 0)); //(.2, 0, .01));


// ------------------------------------------ State --------------------------------------------------- \\
		
		DepthTest depthTest = DepthTest.normal();
		BlendFunc blending = BlendFunc.normal();
		
		depthTest.push();
		blending.push();
		
// ------------------------------------------ Loop --------------------------------------------------- \\

		double frameTimeAvg = 0.0;
		int frameAvgCounter = 0;	
		while(!window.isCloseRequested()) {
			
			Shader.unbind();
			Shader.setProjectionMatrix(camera.getProjectionMatrix());
			Shader.setViewMatrix(camera.getViewMatrix());
			
			Shader.setSkyColor(new Vector3f(.25f));
			glClearColor(Shader.getSkyColor().x, Shader.getSkyColor().y, Shader.getSkyColor().z, 1);
			
		    FBO.SCREEN_FBO.clear();
		    
		// ---------------------------- -------------- ---------------------------- \\
		//	--------------------------- Keyboard Input ---------------------------- \\		
		// ---------------------------- -------------- ---------------------------- \\
			
			movement.update((float) window.getFrameTime());
			followLight.setPosition(camera.getPosition());
		
		// ---------------------------- ---------- ---------------------------- \\
		//	--------------------------- Draw Floor ---------------------------- \\		
		// ---------------------------- ---------- ---------------------------- \\

		    fbo.bind();
				defaultShader.bind();
				defaultShader.loadLights(light, followLight);
				
				fbo.clear();
				
				mesh.render(new TextRenderProperties(new Transform(new Vector3f(10, 100, 0), null, null), new Vector4f(1, 0, 0, 1)));
				mesh3D.render(new TextRenderProperties(new Transform().translate(new Vector3f(-.5, -.5, -5)), new Vector4f(.75f, 0, .5f, 1)));
				
				floorModel.render(new DefaultRenderProperties(
						new Transform(new Vector3f(0, -1, 0), null, new Vector3f(100).setY(0.01f)), 100, .01f, 0));
				defaultShader.getRenderer().render(camera);
				Font.Text2DShader.getRenderer().render(camera);
				Font.BillboardShader.getRenderer().render(camera);
			FBO.unbind();

		// ---------------------------- ----------------- ---------------------------- \\
		// ---------------------------- Update the Window ---------------------------- \\
		// ---------------------------- ----------------- ---------------------------- \\
			
			fbo.resolve();
			
			defaultShader.getRenderer().clear();
			Font.Text2DShader.getRenderer().clear();
			Font.BillboardShader.getRenderer().clear();
			
			window.update();
			
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
			
			if(window.wasResized()) {
				if(fbo.getWidth() == window.getWidth() && fbo.getHeight() == window.getHeight())
					continue;
				
				FBO.SCREEN_FBO.screenResized(window);
				camera.setAspect(window.getAspectRatio());
				camera.recalculate();
			}
		}
		
		defaultShader.cleanUp();
		Font.BillboardShader.cleanUp();
		ParticleManager.ParticleShader.cleanUp();

		mesh.cleanUp();
		font.cleanUp();
		
		window.destroy();
	}
}
