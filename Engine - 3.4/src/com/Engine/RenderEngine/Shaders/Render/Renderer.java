package com.Engine.RenderEngine.Shaders.Render;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.Engine.RenderEngine.Camera.Camera;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.Util.Vectors.Vector3f;

public abstract class Renderer<T extends IRenderable<? super E>, E extends RenderProperties, S extends Shader> {
	protected S shader;
	protected HashMap<T, List<E>> renders;
	protected boolean usingFrustumCulling;
	
	protected Camera camera;
	
	@SuppressWarnings("unchecked")
	public Renderer(Shader shader) {
		this.shader = (S) shader;
		renders = new HashMap<>();
		usingFrustumCulling = true;
	}
	
	public boolean addModel(T model, E property) {
		if(model.getShader() != null && !model.getShader().isAcceptedShader(shader)) 
			throw new IllegalArgumentException("Models shader is not excepted by the " + this.getClass());
		
		if(!renders.containsKey(model)) {
			renders.put(model, new LinkedList<>());
		}
		
		renders.get(model).add(property);
		return true;
	}
	
	protected boolean shouldCull(Camera camera, Vector3f point, float radius) {
		return camera.isInsideFrustum(point, radius);
	}
	
	public void render(Camera camera) {
		this.camera = camera;
		prepareOpenGL();

		for(Iterator<Entry<T, List<E>>> modelIter = renders.entrySet().iterator(); modelIter.hasNext(); ) {
			Entry<T, List<E>> entry = modelIter.next();
			T model = entry.getKey();
			
			bindModel(model);
			
			for(Iterator<E> iter = entry.getValue().iterator(); iter.hasNext(); ) {
				E property = iter.next();
				
				if(usingFrustumCulling) {
					Vector3f vertex = model.getModelData().getCenter();
					float radius = model.getModelData().getRadius();
					vertex = vertex.transform(property.getTransformMatrix());//modelView);
					
					if(shouldCull(camera, vertex, radius)) continue;
				}
				
				renderModel(model, property);
			}
			
			unbindModel(model);
		}

		revertOpenGL();
//		this.camera = null; // TODO: UnComment
	}
	
	public void clear() { renders.clear(); }
	
	protected abstract void prepareOpenGL();
	protected abstract void bindModel(T model);
	protected abstract void renderModel(T model, E properties);
	protected abstract void unbindModel(T model);
	protected abstract void revertOpenGL();
	
	public void performPostProcessing(FBO dest) {} // TODO: Protected
	
	public S getShader() { return shader; }
	
	public void usingFrustumCulling(boolean useFrustumCulling) {
		this.usingFrustumCulling = useFrustumCulling;
	}
}
