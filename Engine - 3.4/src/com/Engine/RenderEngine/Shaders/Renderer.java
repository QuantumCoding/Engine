package com.Engine.RenderEngine.Shaders;

import java.util.ArrayList;
import java.util.HashMap;

import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector3f;

public abstract class Renderer<T extends IRenderable<? super E>, E extends RenderProperties> {
	protected Shader shader;
	protected HashMap<T, ArrayList<E>> renders;
	private boolean usingFrustumCulling;
	
	public Renderer(Shader shader) {
		this.shader = shader;
		renders = new HashMap<>();
		usingFrustumCulling = true;
	}
	
	public boolean addModel(T model, E property, Camera camera) {
		if(!isAcceptedShader(model.getShader())) 
			throw new IllegalArgumentException("Models shader is not excepted by the " + this.getClass());
		
		if(usingFrustumCulling) {
			Vector3f vertex = model.getModelData().getCenter();
			float radius = model.getModelData().getRadius();
			
//			Matrix4f viewMatrix = Shader.getViewMatrix();
//			if(viewMatrix == null) viewMatrix = MatrixUtil.initViewMatrix(camera);
//			Matrix4f modelView = Matrix4f.mul(viewMatrix, property.getTransformMatrix(), null);
			vertex = vertex.transform(property.getTransformMatrix());//modelView);
			
			if(shouldCull(camera, vertex, radius)) return false;
			
//			if(getClass() == DefaultRenderer.class)
//				System.out.println("Render!" + System.currentTimeMillis());
		}
		
		if(!renders.containsKey(model)) {
			renders.put(model, new ArrayList<>());
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
		
//		float d = camera.getSphereCullY() * radius;
//		float z = (float) (dot.z * Math.tan(camera.getFov() / 2));
//		if(dot.y > z + d || dot.y < z - d)
//			return true;
//		
//		d = camera.getSphereCullX() * radius;
//		z *= camera.getAspect();
//		if(dot.x > z + d || dot.x < z - d)
//			return true;
		
		return false;
	}
	
	public void render() {
		prepareOpenGL();
		renderModels();	
		revertOpenGL();
		
		renders.clear();
	}

	protected void prepareOpenGL() {}
	protected void revertOpenGL() {}
	
	public abstract void renderModels();	
	public boolean isAcceptedShader(Shader shader) { return shader == getShader(); }
	public abstract int getRenderStage();
	
	public Shader getShader() {
		return shader;
	}
	
	public void usingFrustumCulling(boolean useFrustumCulling) {
		this.usingFrustumCulling = useFrustumCulling;
	}
}
