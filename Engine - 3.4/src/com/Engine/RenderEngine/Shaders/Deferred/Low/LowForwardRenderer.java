package com.Engine.RenderEngine.Shaders.Deferred.Low;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import java.util.Collection;

import com.Engine.RenderEngine.GLFunctions.BlendFunc;
import com.Engine.RenderEngine.Shaders.Deferred.DeferredRenderProperties;
import com.Engine.RenderEngine.Shaders.Deferred.PBRModel;
import com.Engine.RenderEngine.Shaders.Deferred.Lights.Light;
import com.Engine.RenderEngine.Shaders.Render.Renderer;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Textures.Texture2D;

public class LowForwardRenderer extends Renderer<PBRModel, DeferredRenderProperties, LowForwardShader> {
	public LowForwardRenderer(Shader shader) {
		super(shader);
		usingFrustumCulling(false);
	}

	private BlendFunc prevBlend;
	
	protected void prepareOpenGL() {
		shader.bind();
		
		shader.viewMatrix.load(Shader.getViewMatrix());
		shader.projectionMatrix.load(Shader.getProjectionMatrix());
		
		prevBlend = BlendFunc.current();
		BlendFunc.disable();
	}

	protected void bindModel(PBRModel model) {
		model.bind();
		shader.albedoMap.load(model.getAlbedoMap());
	}

	protected void renderModel(PBRModel model, DeferredRenderProperties properties) {
		shader.transformationMatrix.load(properties.getTransformMatrix());
		glDrawElements(GL_TRIANGLES, model.getIndiceCount(), GL_UNSIGNED_INT, 0);
	}

	protected void unbindModel(PBRModel model) {
		Texture2D.unbind_2D(0);
		model.bind();
	}

	protected void revertOpenGL() {
		Shader.unbind();
		prevBlend.push();
	}
	
	public void setLights(Collection<Light> lights) { shader.loadLights(lights); }
	public void setExposure(float exposure) { shader.exposure.load(exposure); }
}
