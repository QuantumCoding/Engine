package com.Engine.RenderEngine.Camera;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector3f;

public class PerspectiveCamera extends Camera {
	private float fov, aspect;
	private float zNear, zFar;
	
	private Matrix4f projectionMatrix;
	private float sphereCullX, sphereCullY;
	
	public PerspectiveCamera(float fov, float aspect, float zNear, float zFar) {		
		this.fov = fov;
		this.aspect = aspect;
		this.zNear = zNear;
		this.zFar = zFar;
		
		recalculate();
	}
	
	public void recalculate() {
		projectionMatrix = MatrixUtil.initPerspectiveMatrix(fov, aspect, zNear, zFar);
		
		double angle = Math.toRadians(fov) / 2;
		double xAngle = Math.atan(Math.tan(angle) * aspect);
		
		sphereCullX = (float) (1 / Math.cos(xAngle));
		sphereCullY = (float) (1 / Math.cos(angle));
	}
	
	public boolean isInsideFrustum(Vector3f point, float radius) {
		Vector3f pos = point.subtract(getPosition());
		
		Vector3f dot = new Vector3f(
				pos.dot(MatrixUtil.right(getViewMatrix())), 
				pos.dot(MatrixUtil.up(getViewMatrix())), 
				pos.dot(MatrixUtil.forward(getViewMatrix()).multiply(-1))
			);
		
		if(dot.z > zFar + radius || dot.z < zNear - radius)
			return true;
		
		float angle = (float) (Math.toRadians(fov) / 2);
		
		float zx = (float) (dot.z * Math.tan(angle) * aspect);
		float dx = sphereCullX * radius;
		if(dot.x > zx + dx || dot.x < -zx - dx)
			return true;
		
		float zy = (float) (dot.z * Math.tan(angle));
		float dy = sphereCullY * radius;
		if(dot.y > zy + dy || dot.y < -zy - dy)
			return true;
		
		return false;
	}
	
	public float getFov() 	 { return fov; }
	public float getAspect() { return aspect; }
	public float getZNear()  { return zNear; }
	public float getZFar() 	 { return zFar; }
	
	public void setFov(float fov) 		{ this.fov = fov; 		projectionMatrix = null; }
	public void setAspect(float aspect) { this.aspect = aspect; projectionMatrix = null; }
	public void setZNear(float zNear) 	{ this.zNear = zNear; 	projectionMatrix = null; }
	public void setZFar(float zFar) 	{ this.zFar = zFar; 	projectionMatrix = null; }

	public Matrix4f getProjectionMatrix() {
		if(projectionMatrix == null) recalculate();
		return projectionMatrix;
	}
}
