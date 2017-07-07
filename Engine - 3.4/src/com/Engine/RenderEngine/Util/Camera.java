package com.Engine.RenderEngine.Util;

import static org.lwjgl.opengl.ARBDepthClamp.GL_DEPTH_CLAMP;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.vector.Matrix4f;

import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector3f;

public class Camera {
	private float x, y, z;
	private float rotX, rotY, rotZ;
	
	private float scale;
	
	private float fov, aspect;
	private float zNear, zFar;
	
	private Matrix4f projectionMatrix, viewMatrix;
	private float sphereCullX, sphereCullY;
	
	public Camera(float fov, float aspect, float zNear, float zFar) {		
		this.fov = fov;
		this.aspect = aspect;
		this.zNear = zNear;
		this.zFar = zFar;
		
		recalculate();
		
		this.scale = 1.0f;
	}
	
	public void recalculate() {
		projectionMatrix = MatrixUtil.initPerspectiveMatrix(fov, aspect, zNear, zFar);
		
		double angle = Math.toRadians(fov) / 2;
		double xAngle = Math.atan(Math.tan(angle) * aspect);
		
		sphereCullX = (float) (1 / Math.cos(xAngle));
		sphereCullY = (float) (1 / Math.cos(angle));
		
		viewMatrix = null;
	}
	
	public void applyOptimalStates() {
        if (GLContext.getCapabilities().GL_ARB_depth_clamp) {
            glEnable(GL_DEPTH_CLAMP);
        }
    }
	
	public void moveForward(float amt) {
		z += -amt * Math.sin(Math.toRadians(rotY + 90));
		x += -amt * Math.cos(Math.toRadians(rotY + 90));
		viewMatrix = null;
	}

	public void moveRight(float amt) {
		z += amt * Math.sin(Math.toRadians(rotY));
		x += amt * Math.cos(Math.toRadians(rotY));
		viewMatrix = null;
	}
	
	public void moveUp(float amt) { y += amt; viewMatrix = null; }	

	public void rotateY(float amt) { rotY += amt; viewMatrix = null; }
	public void rotateX(float amt) { rotX += amt; viewMatrix = null; }
	public void rotateZ(float amt) { rotZ += amt; viewMatrix = null; }

	public void setScale(float scale) { this.scale = scale; viewMatrix = null; }
	
	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }
	
	public void setX(float x) { this.x = x; viewMatrix = null; }
	public void setY(float y) { this.y = y; viewMatrix = null; }
	public void setZ(float z) { this.z = z; viewMatrix = null; }

	public float getRotX() { return rotX; }
	public float getRotY() { return rotY; }
	public float getRotZ() { return rotZ; }

	public void setRotX(float rotX) { this.rotX = rotX; viewMatrix = null; }
	public void setRotY(float rotY) { this.rotY = rotY; viewMatrix = null; }
	public void setRotZ(float rotZ) { this.rotZ = rotZ; viewMatrix = null; }
	
	public float getScale() { return scale; }
	
	public float getFov() { return fov; }
	public float getAspect() { return aspect; }
	public float getZNear() { return zNear; }
	public float getZFar() { return zFar; }
	
	public void setFov(float fov) { this.fov = fov; }
	public void setAspect(float aspect) { this.aspect = aspect; }
	public void setZNear(float zNear) { this.zNear = zNear; }
	public void setZFar(float zFar) { this.zFar = zFar; }

	public Vector3f getPosition() { return new Vector3f(x, y, z); }
	public Vector3f getRotation() { return new Vector3f(rotX, rotY, rotZ); }
	
	public void setPosition(Vector3f pos) { this.x = pos.x; this.y = pos.y; this.z = pos.z; viewMatrix = null; }
	public void setRotation(Vector3f rot) { this.rotX = rot.x; this.rotY = rot.y; this.rotZ = rot.z; viewMatrix = null; }
	
	public Matrix4f getViewMatrix() {
		if(viewMatrix == null) viewMatrix = MatrixUtil.initViewMatrix(this);
		return viewMatrix;
	}
	
	public Matrix4f getPorjectionMatrix() { return projectionMatrix; }
	public float getSphereCullX() { return sphereCullX; }
	public float getSphereCullY() { return sphereCullY; }
}
