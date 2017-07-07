package com.Engine.Demo.Input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector3f;

public class CameraMovement {
	private Camera camera;
	
	private float movementSpeed;
	private float jumpSpeed;
	private float mouseSensetivity;
	
	private boolean escapeLastFrame;
	
	public CameraMovement(Camera camera, float movementSpeed, float jumpSpeed, float mouseSensitivity){
		this.movementSpeed = movementSpeed;
		this.jumpSpeed = jumpSpeed;
		this.mouseSensetivity = mouseSensitivity;
		this.camera = camera;
		
		Mouse.setGrabbed(true);
	}
	
	public void update(float delta) {
		Vector3f caped = camera.getRotation().capMax(100).capMin(-100);
		camera.setRotX(caped.x);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !escapeLastFrame){
			Mouse.setGrabbed(!Mouse.isGrabbed());
			escapeLastFrame = true;
		}
		else if(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			escapeLastFrame = false;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_R)){
			camera.setPosition(new Vector3f());
			camera.setRotation(new Vector3f());
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			camera.moveForward(movementSpeed * delta);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			camera.moveRight(-movementSpeed * delta);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			camera.moveForward(-movementSpeed * delta);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			camera.moveRight(movementSpeed * delta);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			camera.moveUp(jumpSpeed * delta);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			camera.moveUp(-jumpSpeed * delta);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
			camera.setRotation(new Vector3f(0, 45, 0));
		}
		
		if(Mouse.isGrabbed()){
			if(Math.abs(camera.getRotX()) <= 100){
				camera.rotateX(-Mouse.getDY() * mouseSensetivity);
			}
			camera.rotateY(Mouse.getDX() * mouseSensetivity);
		}
	}
}