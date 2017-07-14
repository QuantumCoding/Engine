package com.Engine.RenderEngine.Instancing;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector3f;

public abstract class InstanceRenderer<T extends IRenderableInstance<? super E>, E extends RenderProperties, S extends Shader> extends Renderer<T, E, S> {
	protected HashMap<T, InstanceVBO> instanceVBOs;

	public InstanceRenderer(Shader shader) {
		super(shader);
		
		instanceVBOs = new HashMap<>();
	}

	public boolean addModel(T model, E property) {
		if(super.addModel(model, property)) {
			if(!instanceVBOs.containsKey(model)) {
				InstanceVBO vbo = new InstanceVBO(model.getInstanceLength(), model.getInstanceCount());
				model.addInstanceAttributes(vbo);
				instanceVBOs.put(model, vbo);
			}
			
			return true;
		}
		
		return false;
	}
	
	public void render(Camera camera) {
		prepareOpenGL();
		
		for(Iterator<Entry<T, List<E>>> modelIter = renders.entrySet().iterator(); modelIter.hasNext(); ) {
			Entry<T, List<E>> entry = modelIter.next();
			T model = entry.getKey();
			InstanceVBO vbo = instanceVBOs.get(model);
			
			for(Iterator<E> iter = renders.get(model).iterator(); iter.hasNext();) {
				E property = iter.next();
				
				if(super.usingFrustumCulling) {
					Vector3f vertex = model.getModelData().getCenter();
					float radius = model.getModelData().getRadius();
					vertex = vertex.transform(property.getTransformMatrix());
					
					if(shouldCull(camera, vertex, radius)) continue;
				}

				prepInstanceVBO(vbo, model, property);
			}
			
			vbo.prepVBO();
			
			bindModel(model);
			renderInstance(vbo, model);
			unbindModel(model);
			
			vbo.reset();
		}
		
		revertOpenGL();
	}
	
	public abstract void prepInstanceVBO(InstanceVBO instanceVBO, T model, E property); 
	public abstract void renderInstance(InstanceVBO instanceVBO, T model); 

	public void renderModel(T model, E properties) {
		throw new IllegalStateException("Instanced Renderer should call renderInstance(InstanceVBO, T) instead");
	}
}
