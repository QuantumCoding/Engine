package com.Engine.RenderEngine.Util;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Shaders.Render.RenderProperties;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.Util.Vectors.Vector3f;

public final class NullRenderProperties extends RenderProperties {
	private static final Transform NULL_TRANSFORM = new Transform();

	public NullRenderProperties() { super(NULL_TRANSFORM); }
	
	public void translate(Vector3f amount) { accessException(); }
	public void rotate(Vector3f amount)  { accessException(); }
	public void scale(Vector3f amount) { accessException(); }

	public void setTransform(Transform transform) { accessException(); }
	
	public Transform getTransform() { return null; }
	public Matrix4f getTransformMatrix() { return null; }

	private void accessException() { throw new IllegalStateException("NullRenderProperties cannot be modified"); }
	
	public NullRenderProperties clone() { return new NullRenderProperties(); }
}
