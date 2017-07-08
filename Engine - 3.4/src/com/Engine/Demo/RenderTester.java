package com.Engine.Demo;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;

import com.Engine.Demo.Input.CameraMovement;
import com.Engine.Demo.MultiRender.MultiModel;
import com.Engine.Demo.MultiRender.MultiRenderProperties;
import com.Engine.Demo.MultiRender.MultiShader;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionEllipse;
import com.Engine.PhysicsEngine.Render.PhysicsRenderProperties;
import com.Engine.PhysicsEngine.Render.PhysicsShader;
import com.Engine.PhysicsEngine.Render.Sphere.SphereRender;
import com.Engine.RenderEngine.RenderEngine;
import com.Engine.RenderEngine.Font.Font;
import com.Engine.RenderEngine.Font.TextMeshStitcher;
import com.Engine.RenderEngine.Font.Render.TextMesh;
import com.Engine.RenderEngine.Font.Render.TextRenderProperties;
import com.Engine.RenderEngine.Lights.Light;
import com.Engine.RenderEngine.Models.ModelLoader;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.Attachment;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderBuffer;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderTexture;
import com.Engine.RenderEngine.Particles.ParticleManager;
import com.Engine.RenderEngine.Particles.Texture.ParticleTexture;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Default.DefaultRenderProperties;
import com.Engine.RenderEngine.Shaders.Default.DefaultShader;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.RenderEngine.Window.Window;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;
import com.Engine.Util.Vectors.Vector4f;

// https://open.gl/depthstencils

public class RenderTester {
	public static void main(String[] args) throws IOException, LWJGLException {
		Window window = new Window();
		window.initDisplay(600, 600, false);
		window.setFPS(120);
		
		Camera camera = new Camera(70, window.getAspectRatio(), 0.3f, 1000);
		CameraMovement movement = new CameraMovement(camera, 2f, 1.5f, 0.1f);
		
		MultiShader multiShader = new MultiShader();
		DefaultShader defaultShader = new DefaultShader();
		PhysicsShader physicsShader = new PhysicsShader();
		
		Texture2D texture0 = new Texture2D(ImageIO.read(RenderTester.class.getResource("/textures/Base.png")));
		Texture2D texture1 = new Texture2D(ImageIO.read(RenderTester.class.getResource("/textures/Mask.png")));
		Texture2D texture2 = new Texture2D(ImageIO.read(RenderTester.class.getResource("/textures/CubeMap.png")));
		
// --------------------------------------- Standard Models -------------------------------------------------- \\

		MultiModel multiModel = new MultiModel(multiShader);
		
		Model cubeModel = new Model(ModelLoader.loadOBJ("res/models/CubeSingle.obj"));
		cubeModel.setShader(defaultShader);
		cubeModel.setTexture(texture2);

		SphereRender sphereRender = new SphereRender(new CollisionEllipse(new Vector3f(cubeModel.getModelData().getRadius())), 30);
		sphereRender.setShader(physicsShader);
		

// ------------------------------------------- Particles -------------------------------------------------- \\
		
		ParticleManager particleManager = new ParticleManager();
		ParticleTexture particleTexture = ParticleTexture.getRegistry().registerTexture(4, RenderTester.class.getResource("/textures/ParticleD.png"));
		ParticleTexture.getRegistry().compressTexture();
		ParticleEmitter emitter = new ParticleEmitter(particleManager, new Vector3f(-2, 0, -2.5), particleTexture, 0.05f);

// ------------------------------------------- Text -------------------------------------------------- \\
		
		Font font = Font.loadFont(RenderEngine.class.getResourceAsStream("/fonts/ArialM.qFnt"));
		TextMesh mesh = TextMeshStitcher.createMesh("THis is a TEST to see how long this thing can be :) 1928374650", 
				font, 5, window.getAspectRatio(), new Vector2f(2.5f, .75f));
		
// ------------------------------------------- FBOs -------------------------------------------------- \\
		
		FBO cubeTextureFBO = new FBO(window.getWidth() / 2, window.getHeight() / 2);
		RenderTexture cubeTexture = new RenderTexture(cubeTextureFBO);
		cubeTextureFBO.attach(cubeTexture, Attachment.ColourBuffer);
		cubeTextureFBO.attach(new RenderBuffer(cubeTextureFBO), Attachment.DepthBuffer);
		Texture2D cubeTexture2D = cubeTexture.cloneTo2D();
		
		FBO mainRenderFBO = new FBO(window.getWidth(), window.getHeight(), 4);
		mainRenderFBO.attach(new RenderBuffer(mainRenderFBO), Attachment.ColourBuffer);
		mainRenderFBO.attach(new RenderBuffer(mainRenderFBO), Attachment.DepthBuffer);
		
		FBO.SCREEN_FBO.screenResized(window);
		
// ------------------------------------------ Lights --------------------------------------------------- \\
		
		float rot = 0;
		Light rotLight = new Light(new Vector3f(), new Vector3f(1), new Vector3f(.5, 0, .01)); 
		Light light = new Light(new Vector3f(), new Vector3f(1), new Vector3f(1, 0, 0));
		Light followLight = new Light(new Vector3f(), new Vector3f(1), new Vector3f(1, 0, 0)); //(.2, 0, .01));
		
		double frameTimeAvg = 0.0;
		int frameAvgCounter = 0;	
		while(!window.isCloseRequested()) {
			emitter.update((float) window.getFrameTime());
			particleManager.update((float) window.getFrameTime(), camera);
			
			Shader.unbind();
			Shader.setProjectionMatrix(camera.getPorjectionMatrix());
			Shader.setViewMatrix(camera.getViewMatrix());
			
			Shader.setSkyColor(new Vector3f(.25f));
			glClearColor(Shader.getSkyColor().x, Shader.getSkyColor().y, Shader.getSkyColor().z, 1);
			
			glEnable(GL_DEPTH_TEST);
		    glEnable(GL_BLEND);
		    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
		// ---------------------------- --------------- ---------------------------- \\
		//	---------------------------- Keyboard Input ---------------------------- \\		
		// ---------------------------- --------------- ---------------------------- \\
			
			movement.update((float) window.getFrameTime());
			followLight.setPosition(camera.getPosition());
			rot += window.getFrameTime() * 250;
			
		// ---------------------------- ----------------- ---------------------------- \\
		// ---------------------------- Render all Models ---------------------------- \\
		// ---------------------------- ----------------- ---------------------------- \\
			
			cubeModel.setTexture(texture0);
			cubeModel.render(new DefaultRenderProperties(
					new Transform(new Vector3f(0, 0, -5), new Vector3f(90, 0, 0), new Vector3f(1)), 100, .95f, 0), camera);
			
			cubeModel.render(new DefaultRenderProperties(
					new Transform(new Vector3f(0, 0, -8), new Vector3f(90, 0, 0), new Vector3f(1)), 100, .95f, 0), camera);
			
			Transform facingTransfrom = new Transform(new Vector3f(-1, 2, -5), null, null);
			mesh.render(new TextRenderProperties(facingTransfrom, new Vector4f(.75f).setW(1)), camera);
			multiModel.render(new MultiRenderProperties(facingTransfrom, texture0, texture1), camera);
			
			particleManager.render(camera);

			rotLight.setPosition(new Vector3f(3, 0, 0).rotate(new Vector3f(0, rot, 0)).add(0, 0, -5));

		// ---------------------------- ------------ ---------------------------- \\
		// ---------------------------- Draw to Cube ---------------------------- \\
		// ---------------------------- ------------ ---------------------------- \\
			
			cubeTextureFBO.bindDraw();
				defaultShader.bind();
				defaultShader.loadLights(rotLight);
				
				cubeTextureFBO.clear();
				
				multiShader.getRenderer().render();
				defaultShader.getRenderer().render();
				physicsShader.getRenderer().render();
//				Font.DefaultShader.getRenderer().render();
//				ParticleManager.ParticleShader.getRenderer().render();
			FBO.unbindDraw();
			

		// ---------------------------- ------------ ---------------------------- \\
		// ---------------------------- Draw to Main ---------------------------- \\
		// ---------------------------- ------------ ---------------------------- \\
			
			cubeModel.setTexture(cubeTexture2D);
			cubeModel.render(new DefaultRenderProperties(
					new Transform(rotLight.getPosition(), new Vector3f(90, 0, 0), new Vector3f(.1f)), 10, .5f, 0), camera);
			
			mainRenderFBO.bindDraw();
				defaultShader.bind();
				defaultShader.loadLights(light, followLight);

				mainRenderFBO.clear();
				
				multiShader.getRenderer().render();
				defaultShader.getRenderer().render();
				physicsShader.getRenderer().render();
				Font.DefaultShader.getRenderer().render();
				ParticleManager.ParticleShader.getRenderer().render();
			FBO.unbindDraw();
			
		// ---------------------------- -------------- ---------------------------- \\
		// ---------------------------- Resolve to FBO ---------------------------- \\
		// ---------------------------- -------------- ---------------------------- \\
			
			mainRenderFBO.resolve();
			
//			ParticleManager.ParticleShader.getRenderer().render();
//			Font.DefaultShader.getRenderer().render();
			
			
//			Texture blur = effect.apply((Texture) blurFBO.getAttachment(Attachment.ColourBuffer));
//			shader.bind(); ImageRenderer.prepOpenGL(); ImageRenderer.drawImage(blur); ImageRenderer.revertOpenGL(); Shader.unbind();

		// ---------------------------- ----------------- ---------------------------- \\
		// ---------------------------- Update the Window ---------------------------- \\
		// ---------------------------- ----------------- ---------------------------- \\
			
			multiShader.getRenderer().clear();
			defaultShader.getRenderer().clear();
			physicsShader.getRenderer().clear();
			Font.DefaultShader.getRenderer().clear();
			ParticleManager.ParticleShader.getRenderer().clear();
			
			window.update();
			
			if(window.wasResized()) {
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
		
		texture0.cleanUp();
		texture1.cleanUp();
		texture2.cleanUp();
		
		multiShader.cleanUp();
		defaultShader.cleanUp();
		physicsShader.cleanUp();
		Font.DefaultShader.cleanUp();
		ParticleManager.ParticleShader.cleanUp();

		multiModel.cleanUp();
		cubeModel.cleanUp();
		sphereRender.cleanUp();
		mesh.cleanUp();
		
		font.cleanUp();
		
		window.destroy();
	}
	
	public static void drawLine(SphereRender model, Camera camera, Vector3f start, Vector3f end, Vector3f color, int steps) {
		Vector3f step = end.subtract(start).divide(steps);
		for(int i = 0; i < steps; i ++) {
			model.render(new PhysicsRenderProperties(
					new Transform(start.add(step.multiply(i)), null, new Vector3f(0.01f)), color, false), camera);
		}
	}
}
