package com.Engine.Demo;

import static org.lwjgl.opengl.GL11.GL_BACK_LEFT;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_CUBE_MAP_SEAMLESS;
import static org.lwjgl.opengl.GL43.GL_TEXTURE_CUBE_MAP;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.Engine.Demo.Input.CameraMovement;
import com.Engine.Demo.OrthoTesting.OrthoModel;
import com.Engine.Demo.Skybox.Skybox;
import com.Engine.Demo.Skybox.SkyboxRenderProperties;
import com.Engine.Demo.Skybox.SkyboxShader;
import com.Engine.Demo.particles.ParticleEmitter;
import com.Engine.RenderEngine.Camera.PerspectiveCamera;
import com.Engine.RenderEngine.Environment.AxialCubeMapBlur;
import com.Engine.RenderEngine.Environment.CubeCamera;
import com.Engine.RenderEngine.Environment.CubeCamera.CameraDirection;
import com.Engine.RenderEngine.Font.Font;
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
import com.Engine.RenderEngine.Shaders.Default.DefaultShader;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Shaders.Deferred.DeferredRenderProperties;
import com.Engine.RenderEngine.Shaders.Deferred.DeferredRenderingSystem;
import com.Engine.RenderEngine.Shaders.Deferred.PBRModel;
import com.Engine.RenderEngine.Shaders.Deferred.Lights.PointLight;
import com.Engine.RenderEngine.Shaders.Deferred.Low.LowForwardRenderer;
import com.Engine.RenderEngine.Shaders.Deferred.Low.LowForwardShader;
import com.Engine.RenderEngine.Shaders.Render.RenderProperties;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.System.RenderingSystem;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Textures.TextureCubeMap;
import com.Engine.RenderEngine.Util.BasicRenderingSystem;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.RenderEngine.Window.Window;
import com.Engine.Util.Vectors.Vector3f;

public class DeferredTesting {
	public static void main(String[] args) throws LWJGLException, IOException {
		Window window = new Window();
		window.initDisplay(600, 600, false);
		window.setFPS(60);
		
		PerspectiveCamera camera = new PerspectiveCamera(90, window.getAspectRatio(), 0.3f, 1000);
		CameraMovement movement = new CameraMovement(camera, 5f, 1.5f, 0.1f);
		camera.setY(2f);
		
		CubeCamera cubeCamera = new CubeCamera(512, .3f, 1000);
		cubeCamera.setPosition(new Vector3f(0, 3, -10));
		
		AxialCubeMapBlur cubeMapBlur = new AxialCubeMapBlur(cubeCamera.getSize(), cubeCamera.getSize());

//		DeferredComputeShader computeShader = new DeferredComputeShader();
		DefaultShader defaultShader = new DefaultShader();
		defaultShader.getRenderer().usingFrustumCulling(false);
		
		BasicRenderingSystem<Model, RenderProperties> defaultSystem = new BasicRenderingSystem<>(defaultShader);
		
// --------------------------------------- Standard Models -------------------------------------------------- \\

		Texture2D tableInnerTexture = new Texture2D(true, DeferredTesting.class.getResourceAsStream("/textures/tableBot.png"));
		Model cubeModel = new Model(ModelLoader.loadOBJ("res/models/TexturedCube.obj"));
		cubeModel.setShader(defaultSystem);
		cubeModel.setTexture(tableInnerTexture);

		Texture2D tableTexture = new Texture2D(true, DeferredTesting.class.getResourceAsStream("/textures/tableTop.png"));
		Model floorModel = new Model(ModelLoader.loadOBJ("res/models/floor.obj"));
		floorModel.setShader(defaultSystem);
		floorModel.setTexture(tableTexture);
		
		Model defaultChestModel = new Model(ModelLoader.loadOBJ("res/models/Chest/Chest.obj"));
		defaultChestModel.setShader(defaultSystem);
		defaultChestModel.setTexture(new Texture2D(true, DeferredTesting.class.getResourceAsStream("/models/Chest/chest_albedo.png")));
		
// --------------------------------------- Deferred Rendering -------------------------------------------------- \\
		
//		DeferredShader deferredShader = new DeferredShader();
//		LowForwardShader deferredShader = new LowForwardShader();
//		deferredShader.getRenderer().usingFrustumCulling(false);
		
		DeferredRenderingSystem deferredShader = new DeferredRenderingSystem();
		
		Texture2D albedoTexture = new Texture2D(true, DeferredTesting.class.getResourceAsStream("/models/Chest/chest_albedo.png"));
		Texture2D roughtnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/models/Chest/chest_roughness.png"));
		Texture2D occulsionTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/models/Chest/chest_ao.png"));
		Texture2D metalnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/models/Chest/chest_metalness.png"));
		Texture2D normalTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/models/Chest/chest_normal.png"));
		
		PBRModel chestModel = new PBRModel(ModelLoader.loadOBJ("res/models/Chest/Chest.obj"), 
				albedoTexture, normalTexture, occulsionTexture, metalnessTexture, roughtnessTexture);

		chestModel.setShader(deferredShader);
		
		Texture2D floorAlbedoTexture = new Texture2D(true, DeferredTesting.class.getResourceAsStream("/textures/tileFloor/tile4b_basecolor.png"));
		Texture2D floorRoughtnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/tileFloor/tile4b_roughness.png"));
		Texture2D floorOcculsionTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/tileFloor/tile4b_ao.png"));
		Texture2D floorMetalnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/tileFloor/tile4b_metalness.png"));
		Texture2D floorNormalTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/tileFloor/tile4b_normal-dx.png"));
		
		PBRModel pbrFloorModel = new PBRModel(ModelLoader.loadOBJ("res/models/floorModel.obj"), 
				floorAlbedoTexture, floorNormalTexture, floorOcculsionTexture, floorMetalnessTexture, floorRoughtnessTexture);
		
		pbrFloorModel.setShader(deferredShader);
		
		Texture2D lampAlbedoTexture = new Texture2D(true, DeferredTesting.class.getResourceAsStream("/textures/aluminum_Scuffed/Aluminum-Scuffed_basecolor.png"));
		Texture2D lampRoughtnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/aluminum_Scuffed/Aluminum-Scuffed_roughness2.png"));
		Texture2D lampOcculsionTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/aluminum_Scuffed/Aluminum-Scuffed_ao.png"));
		Texture2D lampMetalnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/aluminum_Scuffed/Aluminum-Scuffed_metallic.png"));
		Texture2D lampNormalTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/aluminum_Scuffed/Aluminum-Scuffed_normal.png"));
		
		long start = System.currentTimeMillis();
		PBRModel pbrLampModel = new PBRModel(ModelLoader.loadOBJ("res/models/icoSphere.obj"), //icoSphere 
				lampAlbedoTexture, lampNormalTexture, lampOcculsionTexture, lampMetalnessTexture, lampRoughtnessTexture);
		System.out.println("Durration: " + (System.currentTimeMillis() - start));
		pbrLampModel.setShader(deferredShader);
		
		
		
//		ByteBuffer buffer = BufferUtils.createByteBuffer(256 * 256 * 4); //window.getWidth() * window.getHeight()
//		for(int i = 0; i < buffer.capacity(); i ++) buffer.put((byte) 255); buffer.flip();
//		Texture2D outBuffer = new Texture2D(buffer, new Vector2f(256, 256));//window.getWidth(), window.getHeight()));
		
//		int textureId = glGenTextures();
//		glBindTexture(GL_TEXTURE_2D, textureId);
//		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA16F, window.getWidth(), window.getHeight(), 0, GL_RGBA, GL_FLOAT, (ByteBuffer) null);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
//		Texture2D outBuffer = new Texture2D(textureId);
//		glBindTexture(GL_TEXTURE_2D, 0);
		
//		OrthoModel outBufferOutput = new OrthoModel(outBuffer);
//		Vector3f dispSize = new Vector3f(window.getWidth() / 3, window.getHeight() / 3, 0);
//		OrthoRenderProperties outBufferRenderProp = new OrthoRenderProperties(new Transform(new Vector3f(0, 0, 0), null, dispSize));
//	
//		OrthoModel positionOutput = new OrthoModel(((DeferredRenderer) deferredShader.getRenderer()).deferredPositionTexture.cloneTo2D());
//		OrthoModel normalOutput   = new OrthoModel(((DeferredRenderer) deferredShader.getRenderer()).deferredNormalTexture.cloneTo2D());
//		OrthoModel albedoOutput   = new OrthoModel(((DeferredRenderer) deferredShader.getRenderer()).deferredAlbedoTexture.cloneTo2D());
//		OrthoModel pbrDataOutput  = new OrthoModel(((DeferredRenderer) deferredShader.getRenderer()).deferredPBRTexture.cloneTo2D());
//		
//		Vector3f dispSize = new Vector3f(window.getWidth() / 3, window.getHeight() / 3, 0);
//		
//		OrthoRenderProperties positionRenderProp = new OrthoRenderProperties(new Transform(new Vector3f(0, 0, 0), null, dispSize));
//		OrthoRenderProperties normalRenderProp 	 = new OrthoRenderProperties(new Transform(new Vector3f(window.getWidth() - dispSize.x, 0, 0), null, dispSize));
//		OrthoRenderProperties albedoRenderProp 	 = new OrthoRenderProperties(new Transform(new Vector3f(0, window.getHeight() - dispSize.y, 0), null, dispSize));
//		OrthoRenderProperties pbrDataRenderProp  = new OrthoRenderProperties(new Transform(new Vector3f(window.getWidth() - dispSize.x, window.getHeight() - dispSize.y, 0), null, dispSize));
		
//		DeferredSamplerShader samplerShader = new DeferredSamplerShader();
//		((DeferredSamplerRenderer) samplerShader.getRenderer()).assignDataMaps(
//				deferredPositionTexture, deferredNormalTexture, deferredAlbedoTexture, deferredPBRTexture);
		
//		DeferredComputeShader computeShader = new DeferredComputeShader();
		
// ------------------------------------------- Particles -------------------------------------------------- \\
		
		ParticleManager particleManager = new ParticleManager();
		ParticleTexture particleTexture = ParticleTexture.getRegistry().registerTexture(4, DeferredTesting.class.getResource("/textures/ParticleD.png"));
		ParticleTexture.getRegistry().compressTexture();
		ParticleEmitter emitter = new ParticleEmitter(particleManager, new Vector3f(0, 2, -5), particleTexture, 0.05f);
		particleTexture.setAdditvieBlending(true);
		
// ------------------------------------------- Sky Box -------------------------------------------------- \\

		RenderingSystem<Skybox, SkyboxRenderProperties> skyboxShader = new BasicRenderingSystem<>(new SkyboxShader()); 
		
		String using = "Yokohama"; // CloudyLightRays DarkStormy FullMoon SunSet ThickCloudsWater TropicalSunnyDay Yokohama
		///textures/CUBE_MAP.png
		TextureCubeMap skyboxTexture;
		Skybox skybox = new Skybox(); skybox.setTexture(skyboxTexture = new TextureCubeMap(false, TextureCubeMap.from(
				DeferredTesting.class.getResourceAsStream("/textures/skyboxes/" + using + "/" + using + "Right2048.png"),
				DeferredTesting.class.getResourceAsStream("/textures/skyboxes/" + using + "/" + using + "Left2048.png"),
				DeferredTesting.class.getResourceAsStream("/textures/skyboxes/" + using + "/" + using + "Front2048.png"),
				DeferredTesting.class.getResourceAsStream("/textures/skyboxes/" + using + "/" + using + "Back2048.png"),
				DeferredTesting.class.getResourceAsStream("/textures/skyboxes/" + using + "/" + using + "Up2048.png"),
				DeferredTesting.class.getResourceAsStream("/textures/skyboxes/" + using + "/" + using + "Down2048.png")
			)));
		skybox.setShader(skyboxShader);
		
		glEnable(GL_TEXTURE_CUBE_MAP_SEAMLESS);
		
		skyboxTexture.bind();
		glGenerateMipmap(GL_TEXTURE_CUBE_MAP);		
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
//		glTexParameterf(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_LOD_BIAS, -0.6f);

// ------------------------------------------- Text -------------------------------------------------- \\
		
// ------------------------------------------- FBOs -------------------------------------------------- \\
		
		FBO mainFBO = new FBO(window.getWidth(), window.getHeight());
		RenderTexture mainRenderTexture = (RenderTexture) new RenderTexture(mainFBO);
		
		mainFBO.attach(mainRenderTexture, 		  Attachment.ColourBuffer);
		mainFBO.attach(new RenderBuffer(mainFBO), Attachment.DepthBuffer);

		Shader.setSkyColor(new Vector3f(.5f));
		FBO.SCREEN_FBO.screenResized(window);
		
		Scene scene = new Scene();
		
// ------------------------------------------ Lights --------------------------------------------------- \\
		
		PointLight glowLight = new PointLight(emitter.getPosition(), new Vector3f(1, .75, .5), new Vector3f(5, 8, 1)); // 2.4, 0.1f, 10
//		Light followLight = new Light(new Vector3f(1.5, 2, 1.5), new Vector3f(5), new Vector3f(1, 0, 0));//.5, 0, .09)); //(.2, 0, .01));

//		LightInfluenceModel glowLightModel = new LightInfluenceModel(glowLight);
//		glowLightModel.setShader(samplerShader); glowLightModel.render(null);

//		LightInfluenceModel followLightModel = new LightInfluenceModel(followLight);
//		followLightModel.setShader(samplerShader); followLightModel.render(null);
		PointLight[] lights = new PointLight[0];
		Vector3f[] targets = new Vector3f[lights.length];
		Vector3f[] velociest = new Vector3f[lights.length];
		for(int i = 0; i < lights.length; i ++) {
			lights[i] = new PointLight(new Vector3f(0, 5, 0), new Vector3f(.5).add(Vector3f.random(.2f).subtract(.1)), new Vector3f(5, 8, 1));
			
			//Vector3f.randomize(5)
			targets[i] = Vector3f.random(100, 3, 100).subtract(50, 0, 50);
			velociest[i] = new Vector3f();
		}
		
		float exposure = 5;
		
// ------------------------------------------ Tests --------------------------------------------------- \\

		DepthTest defualtDepth = DepthTest.normal();
		BlendFunc defaultBlend = BlendFunc.normal();
		CullFace defaultCull = CullFace.ccw();
		
// ------------------------------------------ Loop --------------------------------------------------- \\
		
		Keyboard.enableRepeatEvents(true);
		FBO.allowMasterAccess(false);
		
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
			
			time += window.getFrameTime();
			emitter.setPosition(emitter.getPosition().setY((float) Math.sin(time * 2) / 3 + 2));

			glowLight.setPosition(emitter.getPosition());
//			followLight.setPosition(camera.getPosition().withY(.5f));
			
//			double t = time / 7 / 2;
//			followLight.setPosition(
//					new Vector3f(Math.cos(t * 32), Math.cos(Math.sin(t * 17)), Math.sin(t * 22))
//					.multiply(3.5, 2, 2).add(-1.5f, 1, -6));
			
			if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0)) camera.setRotation(CameraDirection.values()[0].rotation);
			if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)) camera.setRotation(CameraDirection.values()[1].rotation);
			if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) camera.setRotation(CameraDirection.values()[2].rotation);
			if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3)) camera.setRotation(CameraDirection.values()[3].rotation);
			if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) camera.setRotation(CameraDirection.values()[4].rotation);
			if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5)) camera.setRotation(CameraDirection.values()[5].rotation);
			
			exposure += Mouse.getDWheel() / 1200f;
			if(exposure < 0) exposure = 0;
			
		// ---------------------------- ----------------- ---------------------------- \\
		// ---------------------------- Render all Models ---------------------------- \\
		// ---------------------------- ----------------- ---------------------------- \\

				
			chestModel.render(new DeferredRenderProperties( // -1.5f, 1, -6  // 0, 35, 0
					new Transform(new Vector3f(-1.5f, 1, -6), new Vector3f(0, 35, 0), new Vector3f(4))));
			
			pbrLampModel.render(new DeferredRenderProperties(
					new Transform(new Vector3f(0, 3, -10), new Vector3f(time / 31, time / 17, time / 63).multiply(0), new Vector3f(1))));
				
				
//			for(int i = 0; i < lights.length; i ++) {
//				pbrLampModel.render(new DeferredRenderProperties(
//						new Transform(lights[i].getPosition(), null, new Vector3f(
//								Keyboard.isKeyDown(Keyboard.KEY_O) ? lights[i].getRadius() : .05f))));
//			}
				
			particleManager.render();
			skybox.render(new SkyboxRenderProperties());
				
//			((DeferredRenderer) deferredShader.getRenderer()).setEnvironmentMap(skyboxTexture); ToDO
				
			for(CameraDirection dir : CameraDirection.values()) {
				cubeCamera.renderToFace(dir);
				
				Shader.setProjectionMatrix(cubeCamera.getProjectionMatrix());
				Shader.setViewMatrix(cubeCamera.getViewMatrix());
				
				cubeCamera.getFBO().clear();
//					deferredShader.getRenderer().render(cubeCamera);
//					deferredShader.getRenderer().performPostProcessing(cubeCamera.getFBO());
					
				defaultBlend.push();
				cubeCamera.renderToFace(dir);
//				skybox.setTexture(skyboxTexture);
				scene.render(cubeCamera, skyboxShader.getRenderer(), ParticleManager.ParticleShader.getRenderer());
			}
			
			TextureCubeMap environmentMap = cubeCamera.getTexture();//skyboxTexture;//cubeMapBlur.apply(skyboxTexture)[7];//(int) time / 1 % 4];
				environmentMap.bind();
				glGenerateMipmap(GL_TEXTURE_CUBE_MAP);
				glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			
		// ---------------------------- ---------------- ---------------------------- \\
		//	--------------------------- Compute Lighting ---------------------------- \\		
		// ---------------------------- ---------------- ---------------------------- \\

			mainFBO.bindDraw(0);
				Shader.setProjectionMatrix(camera.getProjectionMatrix());
				Shader.setViewMatrix(camera.getViewMatrix());

//				((DeferredRenderer) deferredShader.getRenderer()).setExposure(exposure);
//				((DeferredRenderer) deferredShader.getRenderer()).setLights(Collections.singleton(glowLight));
//				((DeferredRenderer) deferredShader.getRenderer()).setEnvironmentMap(environmentMap);
				
				deferredShader.bind();
					((LowForwardRenderer) deferredShader.getRenderer()).setLights(Collections.singleton(glowLight));
					((LowForwardRenderer) deferredShader.getRenderer()).setExposure(exposure);
				
				mainFBO.clear();
				
				deferredShader.getRenderer().render(camera);
				deferredShader.getRenderer().performPostProcessing(mainFBO);
				
		// ---------------------------- ---------- ---------------------------- \\
		//	--------------------------- Draw Floor ---------------------------- \\		
		// ---------------------------- ---------- ---------------------------- \\
			glClearColor(Shader.getSkyColor().x, Shader.getSkyColor().y, Shader.getSkyColor().z, 1);
			mainFBO.bindDraw(0);
				defualtDepth.push();
				defaultBlend.push();
				defaultCull.push();
				
				scene.render(camera, skyboxShader.getRenderer(), ParticleManager.ParticleShader.getRenderer());
				skybox.setTexture(skyboxTexture);
			FBO.unbindDraw();
			
		// ---------------------------- -------------- ---------------------------- \\
		// ---------------------------- Resolve to FBO ---------------------------- \\
		// ---------------------------- -------------- ---------------------------- \\
			
			mainFBO.resolve(null, 0, GL_BACK_LEFT);

		// ---------------------------- ----------------- ---------------------------- \\
		// ---------------------------- Update the Window ---------------------------- \\
		// ---------------------------- ----------------- ---------------------------- \\

			skyboxShader.clear();
			deferredShader.getRenderer().clear();
			defaultShader.getRenderer().clear();
			ParticleManager.ParticleShader.getRenderer().clear();
			OrthoModel.RENDERER.clear();
			
			window.update();
			
			if(window.wasResized()) {
				mainFBO.resize(window.getWidth(), window.getHeight());
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
		
		
		defaultShader.cleanUp();
		Font.Text2DShader.cleanUp();
		Font.BillboardShader.cleanUp();
		OrthoModel.OTHO_SHADER.cleanUp();
//		ParticleManager.ParticleShader.cleanUp();

		cubeModel.cleanUp();
		floorModel.cleanUp();
		
		window.destroy();
	}
}
