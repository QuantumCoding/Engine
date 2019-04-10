package com.Engine.RenderEngine.Shaders.Deferred.Lights;

import com.Engine.Util.Vectors.Vector3f;

public class DirectionalLight extends Light {
	protected Vector3f direction;
	
	public DirectionalLight(Vector3f direction, Vector3f color) {
		super(color);
		setDirection(direction);
	}

	public Vector3f getDirection() { return direction; }
	public void setDirection(Vector3f direction) { this.direction = direction.normalize(); }

	public float getRadius() { return Float.POSITIVE_INFINITY; }
	public int type() { return Light.DIRECTION_LIGHT; }
}
