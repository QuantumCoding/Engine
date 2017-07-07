package com.Engine.Demo;

import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

import com.Engine.Demo.Input.CameraMovement;
//import com.Engine.Demo.MultiRender.MultiModel;
//import com.Engine.Demo.MultiRender.MultiShader;
import com.Engine.Demo.MultiRenderNew.MultiModel;
import com.Engine.Demo.MultiRender.MultiRenderProperties;
import com.Engine.Demo.MultiRenderNew.MultiShader;
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
import com.Engine.RenderEngine.New_Pipeline.PostProcessing.ImageRenderer;
import com.Engine.RenderEngine.New_Pipeline.PostProcessing.Test.GaussianBlurEffect;
import com.Engine.RenderEngine.Particles.ParticleManager;
import com.Engine.RenderEngine.Particles.Texture.ParticleTexture;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Default.DefaultRenderProperties;
import com.Engine.RenderEngine.Shaders.Default.DefaultShader;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Textures.Texture;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.RenderEngine.Window.Window;
import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;
import com.Engine.Util.Vectors.Vector4f;

public class RenderTesting {
	public static void main(String[] args) throws LWJGLException, IOException {
		Window window = new Window();
		window.initDisplay(600, 600, false);
		window.setFPS(120000);
		
		RenderEngine renderEngine = new RenderEngine();
		Camera camera = new Camera(90, window.getAspectRatio(), 0.3f, 1000);
		CameraMovement movement = new CameraMovement(camera, 2f, 1.5f, 0.1f);
		renderEngine.render(camera);
		
		renderEngine.addRenderer(ParticleManager.ParticleShader.getRenderer());
		
		MultiShader multiShader = new MultiShader();
//		renderEngine.addRenderer(multiShader.getRenderer());
		
		MultiModel multiModel = new MultiModel(multiShader);
//		MultiModel multiModel = new MultiModel();
//		multiModel.setShader(multiShader);
		
		Texture2D texture0 = new Texture2D(ImageIO.read(RenderTesting.class.getResource("/textures/Base.png")));
		Texture2D texture1 = new Texture2D(ImageIO.read(RenderTesting.class.getResource("/textures/Mask.png")));
		Texture2D texture2 = new Texture2D(ImageIO.read(RenderTesting.class.getResource("/textures/CubeMap.png")));
		
		DefaultShader defaultShader = new DefaultShader();
		renderEngine.addRenderer(defaultShader.getRenderer());
		
		Model cubeModel = new Model(ModelLoader.loadOBJ("res/models/CubeSingle.obj"));
		cubeModel.setShader(defaultShader);
		cubeModel.setTexture(texture2);

		PhysicsShader physicsShader = new PhysicsShader();
		renderEngine.addRenderer(physicsShader);
		
		SphereRender sphereRender = new SphereRender(new CollisionEllipse(new Vector3f(cubeModel.getModelData().getRadius())), 30);
		sphereRender.setShader(physicsShader);
		
		Light light = new Light(new Vector3f(), new Vector3f(1), new Vector3f(1, 0, 0));
//		Light followLight = new Light(new Vector3f(), new Vector3f(1), new Vector3f(.2, 0, .01));
		Light followLight = new Light(new Vector3f(), new Vector3f(1), new Vector3f(1, 0, 0));
		
		ParticleTexture particleTexture = ParticleTexture.getRegistry().registerTexture(4, RenderTesting.class.getResource("/textures/ParticleD.png"));
		ParticleTexture.getRegistry().compressTexture();
		
		ParticleEmitter emitter = new ParticleEmitter(renderEngine.getParticleManager(), new Vector3f(0, 0, 2.5), particleTexture, 0.05f);
		
		Font font = Font.loadFont(RenderEngine.class.getResourceAsStream("/fonts/ArialM.qFnt"));
		renderEngine.addRenderer(Font.DefaultShader);
		
		// https://open.gl/depthstencils
		
		TextMesh mesh = TextMeshStitcher.createMesh("THis is a TEST to see how long this thing can be :) 1928374650", 
				font, 5, window.getAspectRatio(), new Vector2f(2.5f, .75f));
		
//		TextMesh[] allLetters = new TextMesh['z' - '!'];
//		for(int i = 0; i < allLetters.length; i ++) {
//			allLetters[i] = TextMeshStitcher.createMesh("" + (char)(i + '!'), font, 5, window.getAspectRatio(), new Vector2f());
//		}
		
		FBO fboMulti = new FBO(window.getWidth(), window.getHeight(), 4);
		fboMulti.attach(new RenderBuffer(fboMulti), Attachment.ColourBuffer);
		fboMulti.attach(new RenderBuffer(fboMulti), Attachment.DepthBuffer);
//		cubeModel.setTexture(texture.cloneTo2D());
		
		FBO fbo = new FBO(window.getWidth(), window.getHeight());
		RenderTexture texture = new RenderTexture(fbo);
		fbo.attach(texture, Attachment.ColourBuffer);
		fbo.attach(new RenderBuffer(fbo), Attachment.DepthBuffer);
		Texture2D rep = texture.cloneTo2D();

		Camera particleCamera = new Camera(90, fbo.getAspect(), 0.3f, 100);
//		Camera particleCamera = new Camera(90, 1, 0.3f, 100);
		particleCamera.rotateY(180);
 		
//		Fbo2 fbo2 = new Fbo2(100, 100, Fbo2.DEPTH_RENDER_BUFFER);
//		cubeModel.setTexture(new Texture2D(fbo2.getColourTexture(), new Vector2f(100, 100)));

		FBO.SCREEN_FBO.screenResized(window);
		
		TestImageShader shader = new TestImageShader();
		GaussianBlurEffect effect = new GaussianBlurEffect(window.getWidth() / 2, window.getHeight() / 2);
		
		FBO blurFBO = new FBO(window.getWidth(), window.getHeight());
		blurFBO.attach(new RenderTexture(blurFBO), Attachment.ColourBuffer);
		blurFBO.attach(new RenderBuffer(blurFBO), Attachment.DepthBuffer);
		
		float rot = 0;
		Light rotLight = new Light(new Vector3f(), new Vector3f(1), new Vector3f(.5, 0, .01)); 
		
		boolean toggle_b = false, b = false;
		boolean toggle_l = false, l = false;
//		boolean fullscreenSwap_released = false; 
		
		double frameTimeAvg = 0.0;
		int frameAvgCounter = 0;	
		while(!window.isCloseRequested()) {
			movement.update((float) window.getFrameTime());
			emitter.update((float) window.getFrameTime());
			renderEngine.getParticleManager().update((float) window.getFrameTime(), camera);
			
//			if(Keyboard.isKeyDown(Keyboard.KEY_0) && !fullscreenSwap_released)
//				window.setFullscreen(!window.isFullscreen());
//			fullscreenSwap_released = Keyboard.isKeyDown(Keyboard.KEY_0);
			
// ---------------------------- --------------- ----------------------------------------------------------------- \\
//	---------------------------- Keyboard Input ----------------------------------------------------------------- \\		
// ---------------------------- --------------- ----------------------------------------------------------------- \\

			if(Keyboard.isKeyDown(Keyboard.KEY_B) && !toggle_b)
				b = !b;
			toggle_b = Keyboard.isKeyDown(Keyboard.KEY_B);
			
			if(Keyboard.isKeyDown(Keyboard.KEY_L) && !toggle_l)
				l = !l;
			toggle_l = Keyboard.isKeyDown(Keyboard.KEY_L);
			
			rot += window.getFrameTime() * 100;
			
// ---------------------------- ------------- ----------------------------------------------------------------- \\
//	---------------------------- Loop Drawing ----------------------------------------------------------------- \\
// ---------------------------- ------------- ----------------------------------------------------------------- \\
			
//			for(int i = 0; i < 5; i ++) {
//				cubeModel.setTexture(i == 0 ? texture0 : rep);
//				cubeModel.render(new DefaultRenderProperties(
//						new Transform(new Vector3f(0, 0, -5), new Vector3f(90, 0, 0), new Vector3f(1)), 100, .95f, 0), camera);
//				
//				cubeModel.render(new DefaultRenderProperties(
//						new Transform(new Vector3f(0, 0, -8), new Vector3f(90, 0, 0), new Vector3f(1)), 100, .95f, 0), camera);
//				
//				mesh.render(new TextRenderProperties(new Transform(), new Vector4f(.75f).setW(1)), camera);
//			
//				rotLight.setPosition(new Vector3f(3, 0, 0).rotate(new Vector3f(0, rot + (i%2*180), 0)).add(0, 0, -5));
//				defaultShader.bind(); defaultShader.loadLights(rotLight, followLight);
//
//				fboMulti.bind();
//					/*RenderEngine Init*/ {
//						renderEngine.getParticleManager().render(particleCamera);
//						Shader.unbind();
//						Shader.setProjectionMatrix(particleCamera.getPorjectionMatrix());
//						Shader.setViewMatrix(MatrixUtil.initViewMatrix(particleCamera));
//						Vector3f skyColor = new Vector3f(i / 5.0, 0, 1-i / 5.0);
//						glClearColor(skyColor.x, skyColor.y, skyColor.z, 1.0f);
//						fboMulti.clear();
//					}
//					
//					renderEngine.render(camera);
//				FBO.unbind();
//			}
			

// ---------------------------- --------------------- ----------------------------------------------------------------- \\
// ---------------------------- Normal Drawing to FBO ----------------------------------------------------------------- \\
// ---------------------------- --------------------- ----------------------------------------------------------------- \\
			
			cubeModel.setTexture(texture0);
			cubeModel.render(new DefaultRenderProperties(
					new Transform(new Vector3f(0, 0, -5), new Vector3f(90, 0, 0), new Vector3f(1)), 100, .95f, 0), camera);
			
			cubeModel.render(new DefaultRenderProperties(
					new Transform(new Vector3f(0, 0, -8), new Vector3f(90, 0, 0), new Vector3f(1)), 100, .95f, 0), camera);
			
			mesh.render(new TextRenderProperties(new Transform(), new Vector4f(.75f).setW(1)), camera);
			
//			multiModel.render(new MultiRenderProperties(
//					new Transform(new Vector3f(0, 0, -1), new Vector3f(), new Vector3f(1)),
//					texture0, texture1), camera);

			rotLight.setPosition(new Vector3f(3, 0, 0).rotate(new Vector3f(0, rot, 0)).add(0, 0, -5));
			
			defaultShader.bind();
			defaultShader.loadLights(rotLight);

			fboMulti.bindDraw();
				/*RenderEngine Init*/ {
					Shader.unbind();
					Shader.setProjectionMatrix(particleCamera.getPorjectionMatrix());
					Shader.setViewMatrix(MatrixUtil.initViewMatrix(particleCamera));
					Vector3f skyColor = new Vector3f(.1, .75, 1);
					glClearColor(skyColor.x, skyColor.y, skyColor.z, 1.0f);
					fboMulti.clear();
				}

				renderEngine.render(camera);
			FBO.unbindDraw();
			
// ---------------------------- -------------- ----------------------------------------------------------------- \\
// ---------------------------- Resolve to FBO ----------------------------------------------------------------- \\
// ---------------------------- -------------- ----------------------------------------------------------------- \\
			
			fboMulti.resolve(fbo);
			
//			fbo.resolve();
			
//			ParticleManager.ParticleShader.getRenderer().render();
//			Font.DefaultShader.getRenderer().render();
			
// ---------------------------- -------------- ----------------------------------------------------------------- \\
// ---------------------------- Normal Drawing ----------------------------------------------------------------- \\
// ---------------------------- -------------- ----------------------------------------------------------------- \\

			if(b) cubeModel.setTexture(rep);
			cubeModel.render(new DefaultRenderProperties(
					new Transform(new Vector3f(0, 0, -5), new Vector3f(90, 0, 0), new Vector3f(1)), 100, .01f, 0), camera);
//			mesh.render(new TextRenderProperties(new Transform(), new Vector4f(.75f).setW(1)), camera);

			multiModel.render(new MultiRenderProperties(
					new Transform(new Vector3f(0, 0, -1), new Vector3f(), new Vector3f(1)),
					texture0, texture1), camera);
			
			cubeModel.render(new DefaultRenderProperties(new Transform(rotLight.getPosition(), null, new Vector3f(.1f))), camera);
			
			if(l) followLight.setPosition(camera.getPosition());
			
			defaultShader.bind();
			defaultShader.loadLights(light, followLight);

			blurFBO.bindDraw();
				Vector3f skyColor = Shader.getSkyColor();
				glClearColor(skyColor.x, skyColor.y, skyColor.z, 1.0f);
				renderEngine.render(camera);
	
				multiShader.getRenderer().render();
				multiShader.getRenderer().clear();
			FBO.unbindDraw();
			
//			blurFBO.resolve();
			
			ImageRenderer.prepOpenGL();
			effect.apply((Texture) blurFBO.getAttachment(Attachment.ColourBuffer));
			ImageRenderer.revertOpenGL();
			effect.getFBO().resolve();
			
//			Texture blur = effect.apply((Texture) blurFBO.getAttachment(Attachment.ColourBuffer));
//			shader.bind(); ImageRenderer.prepOpenGL(); ImageRenderer.drawImage(blur); ImageRenderer.revertOpenGL(); Shader.unbind();
// ---------------------------- ----------------- ----------------------------------------------------------------- \\
// ---------------------------- Update the Window ----------------------------------------------------------------- \\
// ---------------------------- ----------------- ----------------------------------------------------------------- \\
			
			window.update();
//			window.setTitle(ParticleManager.avg / Time.SECONDS + "");
			
			if(window.wasResized()) {
				FBO.SCREEN_FBO.screenResized(window);
				camera.setAspect(window.getAspectRatio());
				camera.recalculate();
			}
			
			if(frameAvgCounter >= 50) {
				frameTimeAvg /= (double) frameAvgCounter;
				window.setTitle("New Pipeline V3.4 | FPS: " + (int)(1.0/frameTimeAvg) + "\t FrameTime: " + (float)frameTimeAvg);
				
				frameTimeAvg = 0.0;
				frameAvgCounter = 0;
			} else {
				frameTimeAvg += window.getFrameTime();
				frameAvgCounter ++;
			}
		}
		
		texture0.cleanUp();
		texture1.cleanUp();
		
		multiModel.cleanUp();
		cubeModel.cleanUp();
		
		multiShader.cleanUp();
		
		mesh.cleanUp();
		font.cleanUp();
		
		Font.DefaultShader.cleanUp();
		
		window.destroy();
	}
	
	public static void drawLine(SphereRender model, Camera camera, Vector3f start, Vector3f end, Vector3f color, int steps) {
		Vector3f step = end.subtract(start).divide(steps);
		for(int i = 0; i < steps; i ++) {
			model.render(new PhysicsRenderProperties(
					new Transform(start.add(step.multiply(i)), null, new Vector3f(0.01f)), color, false), camera);
		}
	}
	
//	multiModel.render(new MultiRenderProperties(
//	new Transform(new Vector3f(0, 0, -1), new Vector3f(), new Vector3f(1)),
//	texture0, texture1), camera);
	
//	for(float i = 0; i < allLetters.length; i ++) {
//		if(allLetters[(int) i] == null) continue;
//		allLetters[(int) i].render(new TextRenderProperties(
//				new Transform(new Vector3f(
//						i % 8, 
//						(int) i / 8, 
//						0
//					).multiply(.3f), null, null), 
//				new Vector4f(1, 1, 1, 1)), camera);
//		//i / allLetters.length, 1, 1 - i / allLetters.length
//	}
	
//	sphereRender.render(new PhysicsRenderProperties(
//			new Transform(cubeModel.getModelData().getCenter().add(0, 0, -5), null, null), 
//			new Vector3f(1, 1, 0), false), camera);
}
