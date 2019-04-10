package com.Engine.RenderEngine.System;

import java.util.ArrayList;
import java.util.Collection;

import com.Engine.RenderEngine.Camera.Camera;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.Shaders.Render.IRenderable;
import com.Engine.RenderEngine.Shaders.Render.RenderProperties;
import com.Engine.RenderEngine.Shaders.Render.Renderer;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.System.Dependencies.IRenderDependency;

public abstract class RenderingSystem<M extends IRenderable<? super P>, P extends RenderProperties> {
	public static enum RenderLevel { High, Medium, Low; }
	
	private ArrayList<ModelInstance<M, P>> instances;
	
	public RenderingSystem() {
		instances = new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends P> ModelInstance<M, T> addModel(M model, T renderProperties) {
		ModelInstance<M, T> token = new ModelInstance<>(model, renderProperties);
		instances.add((ModelInstance<M, P>) token);
		return token;
	}
	
	public boolean removeModel(ModelInstance<M, P> token) {
		return instances.remove(token);
	}
	

	public void render(RenderLevel level, Camera camera) { render(level, camera, FBO.currentDraw()); }
	
	@SuppressWarnings("unchecked")
	public void render(RenderLevel level, Camera camera, FBO dest) {
		Shader shader = selectShader(level);
		if(shader == null) return;
		
		Renderer<M, P, ?> renderer = (Renderer<M, P, ?>) shader.getRenderer();
	
		renderer.clear();
		for(ModelInstance<M, P> model : instances) {
			renderer.addModel(model.model, model.propreties);
		}
		
		prepareRender(level);
		renderer.render(camera);
		
		if(dest != null)
			renderer.performPostProcessing(dest);
	}
	
	public void removeAllModels() { instances.clear(); }

	public abstract Shader selectShader(RenderLevel level);
	protected abstract void prepareRender(RenderLevel level);
	protected Collection<IRenderDependency> getDependencies(RenderLevel level) { return null; }
	
	public boolean isAcceptedShader(Shader shader) { return true; }
	
	public static class ModelInstance<M extends IRenderable<? super P>, P extends RenderProperties> {
		public M model; 
		public P propreties;

		private ModelInstance(M model, P propreties) {
			this.model = model;
			this.propreties = propreties;
		}
	}
	
	public boolean supportsTransparency() { return false; }
	public boolean usesAdditiveBlending() { return false; }
	
	public float getZLevel(Camera camera) { return camera.getZFar() / 2; }
}
