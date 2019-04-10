package com.Engine.RenderEngine.Shaders.Deferred.Lights;

import com.Engine.Util.Vectors.Vector3f;

public class SpotLight extends PointLight {
	protected Vector3f direction;
	protected float angle;
	
	public SpotLight(Vector3f position, Vector3f color, Vector3f attenuation, Vector3f direction, float angle) {
		super(position, color, attenuation);
		
		setDirection(direction);
		setAngle(angle);
	}

	public Vector3f getDirection() { return direction; }
	public float getAngle() { return (float) Math.toDegrees(angle); }

	public void setDirection(Vector3f direction) { this.direction = direction.normalize(); }
	public void setAngle(float angle) { this.angle = (float) Math.toRadians(angle % 180); }
	
	private static final float THRESHOLD = 0.005f;
	
	public void setFalloffAngle(float angle) {
		attenuation.y = angle = (float) Math.toRadians(angle % 180);
		attenuation.z = (float) (Math.log(2 / THRESHOLD - 1) / (angle * angle));
		System.out.println(attenuation);
	}

	public int type() { return Light.SPOT_LIGHT; }
}
