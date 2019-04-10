package com.Engine.RenderEngine.Shaders.Deferred.Lights;

import com.Engine.Util.Vectors.Vector3f;

public abstract class Light {
	public static final int SPOT_LIGHT = 0;
	public static final int POINT_LIGHT = 1;
	public static final int DIRECTION_LIGHT = 2;
	
	protected Vector3f color;

	public Light(Vector3f color) { setColor(color); }

	public Vector3f getColor() { return color; }
	public void setColor(Vector3f color) { this.color = color; }
	
	public abstract int type();
	public abstract float getRadius();
}
