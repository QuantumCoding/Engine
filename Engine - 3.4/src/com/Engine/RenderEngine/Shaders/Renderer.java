package com.Engine.RenderEngine.Shaders;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector3f;

public abstract class Renderer<T extends IRenderable<? super E>, E extends RenderProperties, S extends Shader> {
	protected S shader;
	protected HashMap<T, List<E>> renders;
	protected boolean usingFrustumCulling;
	
	@SuppressWarnings("unchecked")
	public Renderer(Shader shader) {
		this.shader = (S) shader;
		renders = new HashMap<>();
		usingFrustumCulling = true;
	}
	
	public boolean addModel(T model, E property) {
		if(!isAcceptedShader(model.getShader())) 
			throw new IllegalArgumentException("Models shader is not excepted by the " + this.getClass());
		
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
	
	long fullTimeSUM, bindTimeSUM, propIterTimeSUM, renderTimeSUM, unbindTimeSUM, revertTimeSUM, prepTimeSUM;
	int itterCount, bindCountSUM, propCountSUM, renderCountSUM, unbindCountSUM;
	
	public void render(Camera camera) {
		long fullTime = 0, bindTime = 0, propIterTime = 0, renderTime = 0, unbindTime = 0, revertTime = 0, prepTime = System.nanoTime();
		prepareOpenGL();
		prepTime = System.nanoTime() - prepTime;
		
		int bindCount = 0, propCount = 0, renderCount = 0, unbindCount = 0;
		
		fullTime = System.nanoTime();
		for(Iterator<Entry<T, List<E>>> modelIter = renders.entrySet().iterator(); modelIter.hasNext(); ) {
			Entry<T, List<E>> entry = modelIter.next();
			T model = entry.getKey();
			
			long bindTime_ = System.nanoTime();
			bindModel(model);
			bindTime_ = System.nanoTime() - bindTime_;
			bindTime += bindTime_; bindCount ++;
			
			long propIterTime_ = System.nanoTime();
			for(Iterator<E> iter = entry.getValue().iterator(); iter.hasNext(); ) {
				E property = iter.next();
				
				if(usingFrustumCulling) {
					Vector3f vertex = model.getModelData().getCenter();
					float radius = model.getModelData().getRadius();
					vertex = vertex.transform(property.getTransformMatrix());//modelView);
					
					if(shouldCull(camera, vertex, radius)) continue;
				}
				
				long renderTime_ = System.nanoTime();
				renderModel(model, property);
				renderTime_ = System.nanoTime() - renderTime_;
				renderTime += renderTime_; renderCount ++;
			}
			propIterTime_ = System.nanoTime() - propIterTime_;
			propIterTime += propIterTime_; propCount ++;
			
			long unbindTime_ = System.nanoTime();
			unbindModel(model);
			unbindTime_ = System.nanoTime() - unbindTime_;
			unbindTime += unbindTime_; unbindCount ++;
		}
		fullTime = System.nanoTime() - fullTime;
		
		revertTime = System.nanoTime();
		revertOpenGL();
		revertTime = System.nanoTime() - revertTime;
		
//		fullTime, bindTime, propIterTime, renderTime, unbindTime, revertTime, prepTime
//		System.out.println("-------- " + getClass().getSimpleName() + " --------");
//		System.out.println("Prep: " + prepTime);
//		System.out.println("All Renders: " + fullTime);
//			System.out.println("\tBind Sum: " + bindTime 	 + "\tAvg: " + (bindTime / bindCount));
//			System.out.println("\tProp Sum: " + propIterTime + "\tAvg: " + (propIterTime / propCount));
//				System.out.println("\t\tRend Sum: " + renderTime 	 + "\tAvg: " + (renderTime / renderCount));
//			System.out.println("\tPrp- Sum: " + (propIterTime - renderTime) + "\tAvg: " + ((propIterTime - renderTime) / propCount));
//			System.out.println("\tUnbd Sum: " + unbindTime   + "\tAvg: " + (unbindTime / unbindCount));
//		System.out.println("Not Tacked: " + (fullTime - (bindTime + propIterTime + unbindTime)));
//		System.out.println("Revt: " + revertTime);
//		System.out.println();
//		
//		bindCountSUM += bindCount;
//		propCountSUM += propCount;
//		renderCountSUM += renderCount;
//		unbindCountSUM += unbindCount;
//		
//		fullTimeSUM		 += fullTime		;
//		bindTimeSUM		 += bindTime		;
//		propIterTimeSUM	 += propIterTime	;
//		renderTimeSUM	 += renderTime		;
//		unbindTimeSUM	 += unbindTime		;
//		revertTimeSUM	 += revertTime		;
//		prepTimeSUM		 += prepTime		;
//		itterCount ++;
//		
//		System.out.println("SUM Prep: " + prepTimeSUM / itterCount);
//		System.out.println("SUM All Renders: " + fullTimeSUM / itterCount);
//			System.out.println("\tSUM Bind Sum: " + (bindTimeSUM / itterCount) 	 			+ "\tAvg: " + (bindTimeSUM / itterCount / bindCountSUM));
//			System.out.println("\tSUM Prop Sum: " + (propIterTimeSUM / itterCount) 			+ "\tAvg: " + (propIterTimeSUM / itterCount / propCountSUM));
//				System.out.println("\t\tSUM Rend Sum: " + (renderTimeSUM  / itterCount)	 	+ "\tAvg: " + (renderTimeSUM / itterCount / renderCountSUM));
//			System.out.println("\tSUM Prp- Sum: " + (propIterTimeSUM / itterCount - renderTimeSUM / itterCount) + "\tAvg: " + ((propIterTimeSUM / itterCount - renderTimeSUM / itterCount) / propCountSUM));
//			System.out.println("\tSUM Unbd Sum: " + (unbindTimeSUM   / itterCount) 			+ "\tAvg: " + (unbindTimeSUM / itterCount / unbindCountSUM));
//		System.out.println("SUM Not Tacked: " + (fullTimeSUM / itterCount - (bindTimeSUM / itterCount + propIterTimeSUM / itterCount + unbindTimeSUM / itterCount)));
//		System.out.println("SUM Revt: " + revertTimeSUM / itterCount);
//		System.out.println();
	}
	
	public void clear() { renders.clear(); }
	
	protected abstract void prepareOpenGL();
	protected abstract void bindModel(T model);
	protected abstract void renderModel(T model, E properties);
	protected abstract void unbindModel(T model);
	protected abstract void revertOpenGL();
	
	public boolean isAcceptedShader(Shader shader) { return shader == getShader(); }
	public S getShader() { return shader; }
	
	public void usingFrustumCulling(boolean useFrustumCulling) {
		this.usingFrustumCulling = useFrustumCulling;
	}
}
