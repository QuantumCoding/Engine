package com.Engine.Demo;

import static org.lwjgl.opengl.GL11.GL_BACK_LEFT;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_CUBE_MAP_SEAMLESS;
import static org.lwjgl.opengl.GL40.GL_TEXTURE_CUBE_MAP_ARRAY;

import java.io.IOException;
import java.util.Arrays;

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
import com.Engine.RenderEngine.Environment.Reflection.ReflectionProbe;
import com.Engine.RenderEngine.Font.Font;
import com.Engine.RenderEngine.GLFunctions.BlendFunc;
import com.Engine.RenderEngine.GLFunctions.CullFace;
import com.Engine.RenderEngine.GLFunctions.DepthTest;
import com.Engine.RenderEngine.Models.OBJModelLoader;
import com.Engine.RenderEngine.Models.OBJModelLoader.OBJLoaderSettings;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.Attachment;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderBuffer;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderTexture;
import com.Engine.RenderEngine.Particles.ParticleManager;
import com.Engine.RenderEngine.Particles.Texture.ParticleTexture;
import com.Engine.RenderEngine.Shaders.Deferred.DeferredRenderProperties;
import com.Engine.RenderEngine.Shaders.Deferred.DeferredRenderingSystem;
import com.Engine.RenderEngine.Shaders.Deferred.PBRModel;
import com.Engine.RenderEngine.Shaders.Deferred.Lights.DirectionalLight;
import com.Engine.RenderEngine.Shaders.Deferred.Lights.PointLight;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.System.RenderOrderController;
import com.Engine.RenderEngine.System.RenderSceneInfo;
import com.Engine.RenderEngine.System.RenderingSystem;
import com.Engine.RenderEngine.System.RenderingSystem.ModelInstance;
import com.Engine.RenderEngine.System.RenderingSystem.RenderLevel;
import com.Engine.RenderEngine.Textures.Texture;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Textures.TextureCubeMap;
import com.Engine.RenderEngine.Textures.TextureCubeMapArray;
import com.Engine.RenderEngine.Util.BasicRenderingSystem;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.RenderEngine.Window.Window;
import com.Engine.Util.Vectors.Vector3f;

// TODO: Add different types of Lights
// TODO: Shadow Mapping
// TODO: Render Dependencies Based on Model
// TODO:	Multiple Render Probes 

// TODO: Deferred Shader Sample Resolver 

public class RenderSystemTesting {
	public static void main(String[] args) throws LWJGLException, IOException {
		Window window = new Window();
		window.initDisplay(600, 600, false);
		window.setFPS(72);
		
		PerspectiveCamera camera = new PerspectiveCamera(90, window.getAspectRatio(), 0.3f, 1000);
		CameraMovement movement = new CameraMovement(camera, 5f, 1.5f, 0.1f);
		camera.setY(2f);
		
		TextureCubeMapArray environmentMaps = new TextureCubeMapArray(
				new Vector3f(512, 512, 2), 
				Texture.calculateMaxMipmapCount(512, 512, 2)
			);
		
		environmentMaps.bind();
			glTexParameteri(GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

		
//		ReflectionProbe reflectionProbe = new ReflectionProbe(environmentMaps, 0, 512 / 1, .3f, 1000);
		ReflectionProbe reflectionProbe = new ReflectionProbe(512 / 1, .3f, 1000);
		reflectionProbe.setPosition(new Vector3f(0, 3, -10));
		
// --------------------------------------- Deferred Rendering Models -------------------------------------------------- \\
		
		DeferredRenderingSystem deferredShader = new DeferredRenderingSystem();
		OBJLoaderSettings settings = new OBJLoaderSettings();
		
		Texture2D albedoTexture = new Texture2D(true, DeferredTesting.class.getResourceAsStream("/models/Chest/chest_albedo.png"));
		Texture2D roughtnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/models/Chest/chest_roughness.png"));
		Texture2D occulsionTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/models/Chest/chest_ao.png"));
		Texture2D metalnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/models/Chest/chest_metalness.png"));
		Texture2D normalTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/models/Chest/chest_normal.png"));
		PBRModel chestModel = new PBRModel(OBJModelLoader.load("/models/Chest/Chest.obj", settings), 
				albedoTexture, normalTexture, occulsionTexture, metalnessTexture, roughtnessTexture);
		chestModel.setShader(deferredShader);
		
		Texture2D floorAlbedoTexture = new Texture2D(true, DeferredTesting.class.getResourceAsStream("/textures/tileFloor/tile4b_basecolor.png"));
		Texture2D floorRoughtnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/tileFloor/tile4b_roughness.png"));
		Texture2D floorOcculsionTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/tileFloor/tile4b_ao.png"));
		Texture2D floorMetalnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/tileFloor/tile4b_metalness.png"));
		Texture2D floorNormalTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/tileFloor/tile4b_normal-dx.png"));
		PBRModel pbrFloorModel = new PBRModel(OBJModelLoader.load("/models/floorModel.obj", settings), 
				floorAlbedoTexture, floorNormalTexture, floorOcculsionTexture, floorMetalnessTexture, floorRoughtnessTexture);
		pbrFloorModel.setShader(deferredShader);
		
		Texture2D lampAlbedoTexture = new Texture2D(true, DeferredTesting.class.getResourceAsStream("/textures/aluminum_Scuffed/Aluminum-Scuffed_basecolor.png"));
		Texture2D lampRoughtnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/aluminum_Scuffed/Aluminum-Scuffed_roughness.png"));
		Texture2D lampOcculsionTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/aluminum_Scuffed/Aluminum-Scuffed_ao.png"));
		Texture2D lampMetalnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/aluminum_Scuffed/Aluminum-Scuffed_metallic.png"));
		Texture2D lampNormalTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/aluminum_Scuffed/Aluminum-Scuffed_normal.png"));
		PBRModel pbrLampModel = new PBRModel(OBJModelLoader.load("/models/TexturedSphereSmooth.obj", settings), //icoSphere 
				lampAlbedoTexture, lampNormalTexture, lampOcculsionTexture, lampMetalnessTexture, lampRoughtnessTexture);
		pbrLampModel.setShader(deferredShader); 
		
		Texture2D wallMetalnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/tileFloor/tile4b_metalness.png"));
		Texture2D wallAlbedoTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/aluminum_Scuffed/Aluminum-Scuffed_metallic.png"));
		Texture2D wallRoughtnessTexture = new Texture2D(false, DeferredTesting.class.getResourceAsStream("/textures/aluminum_Scuffed/Aluminum-Scuffed_metallic.png"));
		PBRModel prbBackWall = new PBRModel(OBJModelLoader.load("/models/floorModel.obj", settings), 
				wallAlbedoTexture, lampNormalTexture, lampOcculsionTexture, wallMetalnessTexture, wallRoughtnessTexture);
		prbBackWall.setShader(deferredShader); 
		
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
			skyboxTexture.copyToLayer(0, environmentMaps);
			
// --------------------------------------- Render Models -------------------------------------------------- \\
		
		chestModel.render(new DeferredRenderProperties( // -1.5f, 1, -6  // 0, 35, 0
				new Transform(new Vector3f(-1.5f, 1, -6), new Vector3f(0, 35, 0), new Vector3f(4)), 0));
		
		ModelInstance<PBRModel, DeferredRenderProperties> lampToken = pbrLampModel.render(new DeferredRenderProperties(
				new Transform(new Vector3f(0, 3, -10), Vector3f.random(360), new Vector3f(1)), 1));
		
		ModelInstance<PBRModel, DeferredRenderProperties> glowToken = pbrLampModel.render(new DeferredRenderProperties(
				new Transform(new Vector3f(0, 3, -10), Vector3f.random(360), new Vector3f(.1f)), 0));
		
//		prbBackWall.render(new DeferredRenderProperties(
//				new Transform(new Vector3f(0, 25, -5), new Vector3f(90, 0, 0), new Vector3f(5, 1, 1)), 0));
		
		pbrFloorModel.render(new DeferredRenderProperties(
				new Transform(null, new Vector3f(), new Vector3f(1))));
		
		skybox.render(new SkyboxRenderProperties());
		
		deferredShader.setEnvironmentMap(environmentMaps);
		
// ------------------------------------------- FBOs -------------------------------------------------- \\
		
		FBO mainFBO = new FBO(window.getWidth(), window.getHeight());
		RenderTexture mainRenderTexture = (RenderTexture) new RenderTexture(mainFBO);

		mainFBO.attach(mainRenderTexture, 		  Attachment.ColourBuffer);
		mainFBO.attach(new RenderBuffer(mainFBO), Attachment.DepthBuffer);

		Shader.setSkyColor(new Vector3f(.25f, .1f, .5f).multiply(0));
		FBO.SCREEN_FBO.screenResized(window);
		
		RenderOrderController scene = new RenderOrderController();
		
		scene.addRenderSystem(skyboxShader);
		scene.addRenderSystem(deferredShader);
//		scene.addRenderSystem(ParticleManager.ParticleShader);
		
// ------------------------------------------ Lights --------------------------------------------------- \\

//		SpotLight spotLight = new SpotLight(camera.getPosition(), new Vector3f(10, 0, 0), new Vector3f(50, 0, 0), MatrixUtil.forward(camera.getViewMatrix()), 0.005f);
		DirectionalLight sunLight = new DirectionalLight(new Vector3f(.25, 10, 1),  new Vector3f(89, 136, 140).divide(255));
		PointLight glowLight = new PointLight(emitter.getPosition(), new Vector3f(1, .75, .5), new Vector3f(5, 8, 1)); // 2.4, 0.1f, 10
		float exposure = 5;
		
//		spotLight.setFalloffAngle(.05f);
		
// ------------------------------------------ GL Tests / Functions  --------------------------------------------------- \\

		DepthTest defualtDepth = DepthTest.normal();
		BlendFunc defaultBlend = BlendFunc.normal();
		CullFace defaultCull = CullFace.ccw();
		
		defualtDepth.push();
		defaultBlend.push();
		defaultCull.push();
		
// ------------------------------------------ Main Loop --------------------------------------------------- \\
		
		Keyboard.enableRepeatEvents(true);
		FBO.allowMasterAccess(false);
		
		double time = 0;
		double frameTimeAvg = 0.0;
		int frameAvgCounter = 0;	
		while(!window.isCloseRequested()) {
			
			emitter.update((float) window.getFrameTime());
			particleManager.update((float) window.getFrameTime(), camera);
			particleManager.render();
			
		// ---------------------------- -------------- ---------------------------- \\
		//	--------------------------- Keyboard Input ---------------------------- \\		
		// ---------------------------- -------------- ---------------------------- \\
			
			movement.update((float) window.getFrameTime());
			
			time += window.getFrameTime();
			emitter.setPosition(emitter.getPosition().setY((float) Math.sin(time * 2) / 3 + 2));
			glowToken.propreties.getTransform().setTranslation(emitter.getPosition());
			glowToken.propreties.getTransform().rotate(Vector3f.random(5));
			glowLight.setPosition(emitter.getPosition());
			
			exposure += Mouse.getDWheel() / 1200f;
			if(exposure < 0) exposure = 0;
			
//			if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
//				spotLight.setDirection(MatrixUtil.forward(camera.getViewMatrix()));
//				spotLight.setPosition(camera.getPosition());
//			}
			
		// ---------------------------- --------------- ---------------------------- \\
		//	--------------------------- Update Uniforms ---------------------------- \\		
		// ---------------------------- --------------- ---------------------------- \\
			
			deferredShader.setExposure(exposure);
//			deferredShader.setLights(Collections.singleton(spotLight));
			deferredShader.setLights(Arrays.asList(sunLight, glowLight));
//			deferredShader.setEnvironmentMap(skyboxTextureArray); // Use Skybox for all passes but last
			deferredShader.setEnvironmentMapCap(0);
			
		// ---------------------------- ----------------- ---------------------------- \\
		// ---------------------------- Render all Models ---------------------------- \\
		// ---------------------------- ----------------- ---------------------------- \\
			
			lampToken.propreties.getTransform().setRotation(new Vector3f(time * 31f, time * 17f, time * 63f).divide(1));
				
		// ---------------------------- ---------------- ---------------------------- \\
		//	--------------------------- Prepare Env. Map ---------------------------- \\		
		// ---------------------------- ---------------- ---------------------------- \\
			
			reflectionProbe.render(new RenderSceneInfo(scene, RenderLevel.High, camera) { });
			
//			environmentMaps.bind();
//				glGenerateMipmap(GL_TEXTURE_CUBE_MAP_ARRAY);
//				glTexParameteri(GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
//				
//				System.out.println(glGetTexLevelParameteri(GL_TEXTURE_CUBE_MAP_ARRAY, 1, GL_TEXTURE_WIDTH));
//				System.out.println(glGetTexLevelParameteri(GL_TEXTURE_CUBE_MAP_ARRAY, 1, GL_TEXTURE_HEIGHT));
//				System.out.println(glGetTexLevelParameteri(GL_TEXTURE_CUBE_MAP_ARRAY, 1, GL_TEXTURE_DEPTH));
			
			reflectionProbe.getReflection().copyToLayer(1, environmentMaps);

		// ---------------------------- --------------- ---------------------------- \\
		//	--------------------------- Main FBO Render ---------------------------- \\		
		// ---------------------------- --------------- ---------------------------- \\

			mainFBO.bindDraw(0);
				mainFBO.clear();

				deferredShader.setEnvironmentMapCap(1000);
//				deferredShader.setEnvironmentMap(environmentMaps);//reflectionProbe.getReflection());
				scene.render(camera, RenderLevel.High);
			FBO.unbindDraw();
							
		// ---------------------------- -------------- ---------------------------- \\
		// ---------------------------- Resolve to FBO ---------------------------- \\
		// ---------------------------- -------------- ---------------------------- \\
			
			mainFBO.resolve(null, 0, GL_BACK_LEFT);

		// ---------------------------- ----------------- ---------------------------- \\
		// ---------------------------- Update the Window ---------------------------- \\
		// ---------------------------- ----------------- ---------------------------- \\

			ParticleManager.ParticleShader.removeAllModels();
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
		
		
		Font.Text2DShader.cleanUp();
		Font.BillboardShader.cleanUp();
		OrthoModel.OTHO_SHADER.cleanUp();
//		ParticleManager.ParticleShader.cleanUp();

		window.destroy();
	}
}
