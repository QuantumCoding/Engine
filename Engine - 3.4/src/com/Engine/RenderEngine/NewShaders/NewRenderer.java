package com.Engine.RenderEngine.NewShaders;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector3f;

public abstract class NewRenderer<T extends INewRenderable<? super E>, E extends RenderProperties, S extends NewShader> {
	protected S shader;
	protected HashMap<T, List<E>> renders;
	private boolean usingFrustumCulling;
	
	@SuppressWarnings("unchecked")
	public NewRenderer(NewShader shader) {
		this.shader = (S) shader;
		renders = new HashMap<>();
		usingFrustumCulling = true;
	}
	
	public boolean addModel(T model, E property, Camera camera) {
		if(!isAcceptedShader(model.getShader())) 
			throw new IllegalArgumentException("Models shader is not excepted by the " + this.getClass());
		
		if(usingFrustumCulling) {
			Vector3f vertex = model.getModelData().getCenter();
			float radius = model.getModelData().getRadius();
			vertex = vertex.transform(property.getTransformMatrix());//modelView);
			
			if(shouldCull(camera, vertex, radius)) return false;
		}
		
		if(!renders.containsKey(model)) {
			renders.put(model, new LinkedList<>());
		}
		
		renders.get(model).add(property);
		return true;
	}
	
	protected boolean shouldCull(Camera camera, Vector3f point, float radius) {
		Vector3f pos = point.subtract(camera.getPosition());
		
		Vector3f dot = new Vector3f(
				pos.dot(MatrixUtil.right(camera.getViewMatrix())), 
				pos.dot(MatrixUtil.up(camera.getViewMatrix())), 
				pos.dot(MatrixUtil.forward(camera.getViewMatrix()).multiply(-1))
			);
		
		if(dot.z > camera.getZFar() + radius || dot.z < camera.getZNear() - radius)
			return true;
		
		float angle = (float) (Math.toRadians(camera.getFov()) / 2);
		
		float zx = (float) (dot.z * Math.tan(angle) * camera.getAspect());
		float dx = camera.getSphereCullX() * radius;
		if(dot.x > zx + dx || dot.x < -zx - dx)
			return true;
		
		float zy = (float) (dot.z * Math.tan(angle));
		float dy = camera.getSphereCullY() * radius;
		if(dot.y > zy + dy || dot.y < -zy - dy)
			return true;
		
		return false;
	}
	
	public void render() {
		prepareOpenGL();
		
		for(Iterator<Entry<T, List<E>>> modelIter = renders.entrySet().iterator(); modelIter.hasNext(); ) {
			Entry<T, List<E>> entry = modelIter.next();
			T model = entry.getKey();
			
			bindModel(model);
			for(Iterator<E> iter = entry.getValue().iterator(); iter.hasNext(); )
				renderModel(model, iter.next());
			unbindModel(model);
		}
		
		revertOpenGL();
	}
	
	public void clear() { renders.clear(); }
	
	protected void prepareOpenGL() {}
	public abstract void bindModel(T model);
	public abstract void renderModel(T model, E properties);
	public abstract void unbindModel(T model);
	protected void revertOpenGL() {}
	
	public boolean isAcceptedShader(NewShader shader) { return shader == getShader(); }
	public S getShader() { return shader; }
	
	public void usingFrustumCulling(boolean useFrustumCulling) {
		this.usingFrustumCulling = useFrustumCulling;
	}
}
