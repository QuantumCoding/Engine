package com.Engine.RenderEngine.Camera;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector3f;

public class OrthographicCamera extends Camera {
	private float xLeft, 	xRight;
	private float yBottom,	yTop;
	private float zNear, 	zFar;
	
	private Matrix4f projectionMatrix;
	
	public OrthographicCamera(float xLeft, float xRight, float yBottom, float yTop, float zNear, float zFar) {		
		this.xLeft = xLeft; 	this.xRight = xRight;
		this.yBottom = yBottom; this.yTop = yTop;
		this.zNear = zNear; 	this.zFar = zFar;

		recalculate();
	}
	
	public void recalculate() {
		projectionMatrix = MatrixUtil.initOrthographicMatrix(xLeft, xRight, yBottom, yTop, zNear, zFar);
	}
	
	public boolean isInsideFrustum(Vector3f point, float radius) {
		return true; // TODO: Frustum Culling Code for a Orthographic Projection
	}
	
	public float getLeft() 	 { return xLeft; }
	public float getRight()  { return xRight; }
	public float getTop() 	 { return yTop; }
	public float getBottom() { return yBottom; }
	public float getZNear()  { return zNear; }
	public float getZFar() 	 { return zFar; }
	
	public float getWidth()  { return (xRight - xLeft) / 2; }
	public float getHeight() { return (yTop - yBottom) / 2; }
	public float getDepth()  { return (zFar - zNear) / 2; }
	
	public void setLeft(float xLeft) 	 { this.xLeft = xLeft;		projectionMatrix = null; }
	public void setRight(float xRight)   { this.xRight = xRight;	projectionMatrix = null; }
	public void setTop(float yTop) 	 	 { this.yTop = yTop;		projectionMatrix = null; }
	public void setBottom(float yBottom) { this.yBottom = yBottom; 	projectionMatrix = null; }
	public void setZNear(float zNear) 	 { this.zNear = zNear; 		projectionMatrix = null; }
	public void setZFar(float zFar) 	 { this.zFar = zFar; 		projectionMatrix = null; }

	public Matrix4f getProjectionMatrix() {
		if(projectionMatrix == null) recalculate();
		return projectionMatrix;
	}
}
