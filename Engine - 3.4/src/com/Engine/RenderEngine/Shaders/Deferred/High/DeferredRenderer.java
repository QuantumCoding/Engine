package com.Engine.RenderEngine.Shaders.Deferred.High;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;

import java.util.Collection;

import com.Engine.RenderEngine.GLFunctions.BlendFunc;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.Attachment;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.TargetFormat;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.TargetFormat.TargetLevel;
import com.Engine.RenderEngine.New_Pipeline.FBO.IRenderTarget;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderBuffer;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderTexture;
import com.Engine.RenderEngine.New_Pipeline.PostProcessing.ImageRenderer;
import com.Engine.RenderEngine.Shaders.Deferred.DeferredRenderProperties;
import com.Engine.RenderEngine.Shaders.Deferred.PBRModel;
import com.Engine.RenderEngine.Shaders.Deferred.High.Compute.DeferredComputeShader;
import com.Engine.RenderEngine.Shaders.Deferred.Lights.Light;
import com.Engine.RenderEngine.Shaders.Render.Renderer;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Textures.Texture;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Textures.TextureCubeMapArray;
import com.Engine.Util.Vectors.Vector2f;

public class DeferredRenderer extends Renderer<PBRModel, DeferredRenderProperties, DeferredShader> {
	private static final DeferredComputeShader COMPUTE_SHADER = new DeferredComputeShader();
	
	private RenderTexture deferredPositionTexture, deferredNormalTexture, deferredAlbedoTexture, deferredPBRTexture;
	public FBO deferredFBO;
	
	private Texture2D fallbackTarget;
	private Vector2f lastFallbackSize;
	
	public DeferredRenderer(Shader shader) {
		super(shader);
		usingFrustumCulling(false);
		
		deferredFBO = new FBO(640, 480);//window.getWidth(), window.getHeight());
		
		deferredPositionTexture	= new RenderTexture(deferredFBO).init(TargetFormat.ColourData, TargetLevel.Max); 
		deferredNormalTexture	= new RenderTexture(deferredFBO).init(TargetFormat.ColourData, TargetLevel.Default);

		deferredFBO.attach(deferredPositionTexture									, Attachment.ColourBuffer, 0);
		deferredFBO.attach(deferredNormalTexture									, Attachment.ColourBuffer, 1);
		deferredFBO.attach(deferredAlbedoTexture	= new RenderTexture(deferredFBO), Attachment.ColourBuffer, 2);
		deferredFBO.attach(deferredPBRTexture		= new RenderTexture(deferredFBO), Attachment.ColourBuffer, 3);
		deferredFBO.attach(new RenderBuffer(deferredFBO), Attachment.DepthBuffer);
				
		fallbackTarget = new Texture2D(false, null, lastFallbackSize = deferredFBO.getSize());
	}
	
	private BlendFunc prevBlendState;
	
	protected void prepareOpenGL() {
		shader.bind();
		
		shader.viewMatrix.load(Shader.getViewMatrix());
		shader.projectionMatrix.load(Shader.getProjectionMatrix());
		
//		if(camera instanceof CubeCamera) return;
		deferredFBO.bindDraw(0, 1, 2, 3);
			deferredFBO.clear();

		prevBlendState = BlendFunc.current();
		BlendFunc.disable();
	}

	protected void bindModel(PBRModel model) {
		model.bind();
		
		shader.albedoMap.load(model.getAlbedoMap());
		shader.normalMap.load(model.getNormalMap());
		
		shader.occlusionMap.load(model.getOcclusionMap());
		shader.metalnessMap.load(model.getMetalnessMap());
		shader.roughnessMap.load(model.getRoughnessMap());
	}

	protected void renderModel(PBRModel model, DeferredRenderProperties properties) {
		shader.transformationMatrix.load(properties.getTransformMatrix());
		shader.environmentLayer.load(properties.getEnvironmentLayer());
		
		glDrawElements(GL_TRIANGLES, model.getIndiceCount(), GL_UNSIGNED_INT, 0);
	}

	protected void unbindModel(PBRModel model) {
		Texture2D.unbind_2D(0);
		Texture2D.unbind_2D(1);
		Texture2D.unbind_2D(2);
		Texture2D.unbind_2D(3);
		Texture2D.unbind_2D(4);

		model.bind();
	}

	protected void revertOpenGL() {
		prevBlendState.push();
		
		Shader.unbind();
		FBO.unbindDraw();
	}
	
	private float compShader_exposure;
	private TextureCubeMapArray environmentMap;
	private int environmentMapCap;
	
	public void setExposure(float exposure) { this.compShader_exposure = exposure; }
	public void setLights(Collection<Light> lights) {
		int index = 0; for(Light light : lights) COMPUTE_SHADER.Lights.lights[index ++].load(light);
		COMPUTE_SHADER.Lights.numActiveLights.load(lights.size());
	}
	
	public void setEnvironmentMap(TextureCubeMapArray map) { this.environmentMap = map; }
	public void setEnvironmentMapCap(int cap) { this.environmentMapCap = cap; }
	
	public void performPostProcessing(FBO dest) { // TODO: protected
		COMPUTE_SHADER.bind();
			COMPUTE_SHADER.Lights.load();
			
			COMPUTE_SHADER.projectionMatrix.load(camera.getProjectionMatrix());
			COMPUTE_SHADER.viewMatrix.load(camera.getViewMatrix());
			
			COMPUTE_SHADER.cameraPos.load(camera.getPosition());
			COMPUTE_SHADER.exposure.load(compShader_exposure);
			
			COMPUTE_SHADER.framebufferDim.load(dest.getWidth(), dest.getHeight());
			
			COMPUTE_SHADER.positionMap.load(deferredPositionTexture);
			COMPUTE_SHADER.normalMap.load(deferredNormalTexture);
			COMPUTE_SHADER.albedoMap.load(deferredAlbedoTexture);
			COMPUTE_SHADER.pbrDataMap.load(deferredPBRTexture);
			
			COMPUTE_SHADER.environmentMap.load(environmentMap);
			COMPUTE_SHADER.environmentMapSudoLayerCap.load(environmentMapCap);
			
			Texture renderTarget = null;
			
			int targetIndex = dest.getDrawBuffers()[0] - GL_COLOR_ATTACHMENT0;
			if(0 <= targetIndex && targetIndex < FBO.MAX_DRAW_BUFFERS) {
				IRenderTarget target = dest.getAttachment(Attachment.ColourBuffer, targetIndex);
				if(target != null && target instanceof RenderTexture) renderTarget = (RenderTexture) target;
				if(renderTarget.getGLTextureType() == GL_TEXTURE_CUBE_MAP) renderTarget = null;
			}
			
			if(renderTarget == null) {
				if(!lastFallbackSize.equals(dest.getSize()))
					fallbackTarget.rebuffer(false, null, lastFallbackSize = dest.getSize());
				
				renderTarget = fallbackTarget;
			}
			
			COMPUTE_SHADER.outTexture.load(renderTarget);
		COMPUTE_SHADER.execute(dest.getWidth(), dest.getHeight(), 1);
//		glMemoryBarrier(GL_ALL_BARRIER_BITS);
		
		dest.bindDraw();
		deferredFBO.bindRead(0);
			glBlitFramebuffer(
				0, 0, deferredFBO.getWidth(), deferredFBO.getHeight(), 
				0, 0, dest.getWidth(), dest.getHeight(), 
				GL_DEPTH_BUFFER_BIT, GL_NEAREST
			);
			
			if(renderTarget == fallbackTarget) {
				// TODO: Blitz fallback to Dest.
				
				ImageRenderer.PLAIN_SHADER.bind();
				
				ImageRenderer.prepOpenGL();
					ImageRenderer.drawImage(renderTarget);
				ImageRenderer.revertOpenGL();
			}
		FBO.unbindRead();
	}
}
