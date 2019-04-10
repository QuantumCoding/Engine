package com.Engine.Demo;

import static org.lwjgl.opengl.GL11.GL_BACK_LEFT;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

import com.Engine.Demo.Input.CameraMovement;
import com.Engine.Demo.MultiRender.MultiModel;
import com.Engine.Demo.MultiRender.MultiShader;
import com.Engine.Demo.OrthoTesting.OrthoModel;
import com.Engine.Demo.TestImageDrawer.TestImageShader;
import com.Engine.Demo.particles.ParticleEmitter;
import com.Engine.RenderEngine.Camera.Camera;
import com.Engine.RenderEngine.Camera.PerspectiveCamera;
import com.Engine.RenderEngine.Font.Font;
import com.Engine.RenderEngine.Font.TextMeshStitcher;
import com.Engine.RenderEngine.Font.Render.TextMesh;
import com.Engine.RenderEngine.GLFunctions.BlendFunc;
import com.Engine.RenderEngine.GLFunctions.CullFace;
import com.Engine.RenderEngine.GLFunctions.DepthTest;
import com.Engine.RenderEngine.Models.ModelLoader;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.Attachment;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderBuffer;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderTexture;
import com.Engine.RenderEngine.New_Pipeline.Scene.Scene;
import com.Engine.RenderEngine.Particles.ParticleManager;
import com.Engine.RenderEngine.Particles.Texture.ParticleTexture;
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

public class SceneTester {
	public static Camera camera;
	
	public static void main(String[] args) throws IOException, LWJGLException {
		Window window = new Window();
		window.initDisplay(600, 600, false);
		window.setFPS(120);
		
		SceneTester.camera = new PerspectiveCamera(70, window.getAspectRatio(), 0.3f, 1000);
		PerspectiveCamera camera = new PerspectiveCamera(70, window.getAspectRatio(), 0.3f, 1000);
		CameraMovement movement = new CameraMovement(camera, 5f, 1.5f, 0.1f);
		camera.setY(2f);
		
		MultiShader multiShader = new MultiShader();
		DefaultShader defaultShader = new DefaultShader();
		
		TestImageShader imageShader = new TestImageShader();
		
//		defaultShader.getRenderer().usingFrustumCulling(false);
		multiShader.getRenderer().usingFrustumCulling(false);
		
		Texture2D texture0 = new Texture2D(ImageIO.read(RenderTester.class.getResource("/textures/Base.png")));
		Texture2D texture1 = new Texture2D(ImageIO.read(RenderTester.class.getResource("/textures/Mask.png")));
		Texture2D texture2 = new Texture2D(ImageIO.read(RenderTester.class.getResource("/textures/CubeMap.png")));
		
		Texture2D tableTexture = new Texture2D(ImageIO.read(RenderTester.class.getResource("/textures/tableTop.png")));
		Texture2D tableInnerTexture = new Texture2D(ImageIO.read(RenderTester.class.getResource("/textures/tableBot.png")));
		
// --------------------------------------- Standard Models -------------------------------------------------- \\

		MultiModel multiModel = new MultiModel(multiShader);
		
		Model cubeModel = new Model(ModelLoader.loadOBJ("res/models/CubeSingle.obj"));
		cubeModel.setShader(defaultShader);
		cubeModel.setTexture(tableInnerTexture);
		
		Model floorModel = new Model(ModelLoader.loadOBJ("res/models/floor.obj"));
		floorModel.setShader(defaultShader);
		floorModel.setTexture(tableTexture);

//		OrthoModel orthoModel = new OrthoModel();
		
// ------------------------------------------- Particles -------------------------------------------------- \\
		
		ParticleManager particleManager = new ParticleManager();
		ParticleTexture particleTexture = ParticleTexture.getRegistry().registerTexture(4, RenderTester.class.getResource("/textures/ParticleD.png"));
		ParticleTexture.getRegistry().compressTexture();
		ParticleEmitter emitter = new ParticleEmitter(particleManager, new Vector3f(0, 2, -5), particleTexture, 0.05f);
		particleTexture.setAdditvieBlending(true);

// ------------------------------------------- Text -------------------------------------------------- \\
		
		Font font = Font.loadFont(SceneTester.class.getResourceAsStream("/fonts/ArialM.qFnt"));
		TextMesh mesh = TextMeshStitcher.createMesh("THis is a TEST to see how long this thing can be :) 1928374650", 
				font, 5, window.getAspectRatio(), new Vector2f(2.5f, .75f));
		
		TextMesh mesh2D = TextMeshStitcher.createMesh("Bottom of the Screen!", 
				font, 16f * 1/TextMeshStitcher.DEFUALT_LINE_HEIGHT, window.getAspectRatio(), new Vector2f(window.getWidth() / 3 * 2, -1));
		
		mesh2D.setShader(Font.Text2DShader);
		
// ------------------------------------------- FBOs -------------------------------------------------- \\

		FBO multiFBO = new FBO(window.getWidth(), window.getHeight(), 4);
		multiFBO.attach(new RenderBuffer(multiFBO), Attachment.ColourBuffer);
		multiFBO.attach(new RenderBuffer(multiFBO), Attachment.DepthBuffer);

		FBO backSideFullFBO = new FBO(window.getWidth() / 4, window.getHeight() / 4, 8);
		backSideFullFBO.attach(new RenderBuffer(backSideFullFBO), Attachment.ColourBuffer);
		
		FBO backSideFBO = new FBO(backSideFullFBO.getWidth(), backSideFullFBO.getHeight());
		RenderTexture backTexture = new RenderTexture(backSideFBO);
		backSideFBO.attach(backTexture, Attachment.ColourBuffer);
		
		Shader.setSkyColor(new Vector3f(.5f));
		FBO.SCREEN_FBO.screenResized(window);
		
		Scene scene = new Scene();
		
// ------------------------------------------ Lights --------------------------------------------------- \\
		
		Light light = new Light(new Vector3f(), new Vector3f(1), new Vector3f(1, 0, 0));
		Light glowLight = new Light(emitter.getPosition(), new Vector3f(1, .75, .5), new Vector3f(.2, 0, .25));
		Light followLight = new Light(new Vector3f(), new Vector3f(1), new Vector3f(.5, 0, .09)); //(.2, 0, .01));

// ------------------------------------------ Tests --------------------------------------------------- \\

		DepthTest defualtDepth = DepthTest.normal();
		CullFace defaultCull = CullFace.ccw();
		CullFace cubeCull = CullFace.cw();
		BlendFunc defaultBlend = BlendFunc.normal();
		
// ------------------------------------------ Loop --------------------------------------------------- \\
		
		Keyboard.enableRepeatEvents(true);
		
		double time = 0;
		double frameTimeAvg = 0.0;
		int frameAvgCounter = 0;	
		while(!window.isCloseRequested()) {
			emitter.update((float) window.getFrameTime());
			particleManager.update((float) window.getFrameTime(), camera);
			
		// ---------------------------- -------------- ---------------------------- \\
		//	--------------------------- Keyboard Input ---------------------------- \\		
		// ---------------------------- -------------- ---------------------------- \\
			
			movement.update((float) window.getFrameTime());
			
			SceneTester.camera.setPosition(camera.getPosition());
			SceneTester.camera.setRotation(camera.getRotation());
			
//			if(Mouse.isButtonDown(1)) {
//				Vector3f p1 = MatrixUtil.projectPoint(new Vector2f(Mouse.getX(), Mouse.getY()), true);
//				Vector3f p2 = MatrixUtil.projectPoint(new Vector2f(Mouse.getX(), Mouse.getY()), false);
//				Vector3f dir = p2.subtract(p1), normal = new Vector3f(0, 1, 0);
//				
//				Vector3f intercet = p1.add(dir.multiply((p1.dot(normal)) / dir.dot(normal)));
//				emitter.setPosition(intercet.setY(emitter.getPosition().getY()));
//			}
			
			time += window.getFrameTime();
			emitter.setPosition(emitter.getPosition().setY((float)Math.sin(time*2) / 3 + 2));
			
			followLight.setPosition(camera.getPosition());
			glowLight.setPosition(emitter.getPosition());
		
		// ---------------------------- ---------- ---------------------------- \\
		//	--------------------------- Draw Floor ---------------------------- \\		
		// ---------------------------- ---------- ---------------------------- \\
			
//			floorModel.render(new DefaultRenderProperties(new Transform(null, null, new Vector3f(50).setY(0.1f))));
//			
//			multiFBO.bindDraw();
//				defualtDepth.enable(); defaultCull.enable(); defaultBlend.enable();
//				defaultShader.bind(); defaultShader.loadLights(followLight, glowLight); //light
//				scene.render(camera, defaultShader.getRenderer());
//			FBO.unbindDraw();
			
		// ---------------------------- ----------------- ---------------------------- \\
		// ---------------------------- Render all Models ---------------------------- \\
		// ---------------------------- ----------------- ---------------------------- \\
			
			particleManager.render();
			
			multiFBO.bindDraw(); 
				FBO.allowMasterAccess(false); multiFBO.clear();
				defualtDepth.push(); defaultCull.push(); defaultBlend.push();
				defaultShader.bind(); defaultShader.loadLights(followLight, glowLight); //light
				
				defaultShader.getRenderer().clear();
				floorModel.render(new DefaultRenderProperties(new Transform(null, null, new Vector3f(50).setY(0.1f))));
				scene.render(camera, defaultShader.getRenderer());
				
				defaultShader.getRenderer().usingFrustumCulling(true);
				defaultShader.getRenderer().clear(); cubeCull.push();
				cubeModel.render(new DefaultRenderProperties(new Transform(new Vector3f(1.5f, .55, -5), null, new Vector3f(.5f)),
						100, .15f, 0));
				scene.render(camera, defaultShader.getRenderer());
				defaultShader.getRenderer().usingFrustumCulling(false);
				
				CullFace.disable(); DepthTest.disable();
//				mesh.render(new TextRenderProperties(new Transform(new Vector3f(3, 2, -5), null, null), 
//						new Vector4f(.75f, .6f, .1f, 1)));
//				mesh2D.render(new TextRenderProperties(new Transform(new Vector3f(window.getWidth() / 2, window.getHeight() / 2, 1), null, new Vector3f(1)), 
//						new Vector4f(.95f, .3f, .1f, 1)));
//				mesh2D.render(new TextRenderProperties(new Transform(
//						new Vector3f((window.getWidth() - mesh2D.getSize().x) / 2, window.getHeight(), .5), 
//						null, 
//						new Vector3f(1, 1, 1)), 
//						new Vector4f(.75f, .6f, .1f, 1)));
//				scene.render(camera, /*mesh.getShader().getRenderer(),*/ mesh2D.getShader().getRenderer());
				
//				orthoModel.render(new OrthoRenderProperties(new Transform(
//						new Vector3f((window.getWidth() - mesh2D.getSize().x) / 2, window.getHeight() - mesh2D.getSize().y, .5).add(new Vector3f(mesh2D.getSize(), 0).divide(2)), 
//						null, new Vector3f(mesh2D.getSize(), 1))));
				scene.render(camera, OrthoModel.RENDERER, mesh2D.getShader().getRenderer());
				
//				UIModel.drawRect(100, 100, 250, 250);
//				scene.render(camera, UIModel.RENDERER);
				
				defaultCull.push();
				scene.render(camera, ParticleManager.ParticleShader.getRenderer());
				FBO.allowMasterAccess(true);
			FBO.unbindDraw(); 

		// ---------------------------- ------------ ---------------------------- \\
		// ---------------------------- Draw to Cube ---------------------------- \\
		// ---------------------------- ------------ ---------------------------- \\
			
			

		// ---------------------------- ------------ ---------------------------- \\
		// ---------------------------- Draw to Main ---------------------------- \\
		// ---------------------------- ------------ ---------------------------- \\

			

		// ---------------------------- -------------- ---------------------------- \\
		// ---------------------------- Draw Back Side ---------------------------- \\
		// ---------------------------- -------------- ---------------------------- \\
			
			/*floorModel.render(new DefaultRenderProperties(new Transform(null, null, new Vector3f(50).setY(0.1f))));
		
			float rot = (float)Math.toDegrees((Math.atan(Math.tan(Math.toRadians(camera.getFov())) * window.getAspectRatio())));
			backSideFullFBO.bindDraw();
				camera.rotateY(rot);
				
				FBO.allowMasterAccess(false); backSideFullFBO.clear();
				defualtDepth.enable(); defaultCull.enable(); defaultBlend.enable();
				defaultShader.bind(); defaultShader.loadLights(followLight, glowLight); //light
				
				defaultShader.getRenderer().clear();
				floorModel.render(new DefaultRenderProperties(new Transform(null, null, new Vector3f(50).setY(0.1f))));
				scene.render(camera, defaultShader.getRenderer());
				
				defaultShader.getRenderer().usingFrustumCulling(true);
				defaultShader.getRenderer().clear(); cubeCull.enable();
				cubeModel.render(new DefaultRenderProperties(new Transform(new Vector3f(1.5f, .55, -5), null, new Vector3f(.5f)),
						100, .95f, 0));
				scene.render(camera, defaultShader.getRenderer());
				defaultShader.getRenderer().usingFrustumCulling(false);
				
				defaultCull.enable();
				scene.render(camera, ParticleManager.ParticleShader.getRenderer());
				FBO.allowMasterAccess(true);
				
				camera.rotateY(-rot);
				
				backSideFullFBO.resolve(backSideFBO);*/
				
//			multiFBO.bindDraw();
//				FBO.allowMasterAccess(false);
//				imageShader.bind();
//					ImageRenderer.prepOpenGL();
//						ImageRenderer.drawImage(backTexture);
//					ImageRenderer.revertOpenGL();
//				Shader.unbind();
//				FBO.allowMasterAccess(true);
//			FBO.unbindDraw();
			
		// ---------------------------- -------------- ---------------------------- \\
		// ---------------------------- Resolve to FBO ---------------------------- \\
		// ---------------------------- -------------- ---------------------------- \\
			
			multiFBO.resolve(null, 0, GL_BACK_LEFT);

		// ---------------------------- ----------------- ---------------------------- \\
		// ---------------------------- Update the Window ---------------------------- \\
		// ---------------------------- ----------------- ---------------------------- \\
			
			multiShader.getRenderer().clear();
			defaultShader.getRenderer().clear();
			mesh.getShader().getRenderer().clear();
			mesh2D.getShader().getRenderer().clear();
			ParticleManager.ParticleShader.getRenderer().clear();
			OrthoModel.RENDERER.clear();
			
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
		
		texture0.cleanUp();
		texture1.cleanUp();
		texture2.cleanUp();
		
		multiShader.cleanUp();
		defaultShader.cleanUp();
		Font.Text2DShader.cleanUp();
		Font.BillboardShader.cleanUp();
		OrthoModel.OTHO_SHADER.cleanUp();
		ParticleManager.ParticleShader.cleanUp();

		imageShader.cleanUp();
		multiModel.cleanUp();
		cubeModel.cleanUp();
		floorModel.cleanUp();
		mesh.cleanUp();
		
		font.cleanUp();
		
		window.destroy();
	}
}
