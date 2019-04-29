package com.Engine.RenderEngine.GUI.Components.SubComponents;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.GUI.Shader.UIGraphics;
import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.Util.Vectors.Vector3f;

public class QuadComponent extends RenderProperties implements ISubUIComponent {
	private Transform textureTransform;
	private boolean requiresUniformScaling;
	
	public QuadComponent() { this(null, null); }
	
	public QuadComponent(float x, float y, float width, float height) {
		this(new Transform(new Vector3f(x, y, 0), null, new Vector3f(width, height, 1)), null);
	}

	public QuadComponent(Transform transform, Transform textureTransform) {
		super(transform); 
		this.textureTransform = textureTransform == null ? new Transform() : textureTransform;
		this.requiresUniformScaling = false;
	}
	
	public boolean requiresUniformScaling() { return requiresUniformScaling; }
	public QuadComponent setUniformScaling(boolean uniform) { this.requiresUniformScaling = uniform; return this; }
	
	public Transform getTextureTransfrom() { return textureTransform; }
	public Matrix4f getTextureMatrix() { return textureTransform.getTransformMatrix(); }
	
	public QuadComponent setTextureTransfrom(float x, float y, float width, float height) { 
		return setTextureTransfrom(new Transform(new Vector3f(x, y, 0), null, new Vector3f(width, height, 1)));
	}
	public QuadComponent setTextureTransfrom(Transform transform) { 
		this.textureTransform = transform; return this;
	}
	
	public QuadComponent updateBounds(float x, float y, float width, float height) {
		super.getTransform().setTranslation(new Vector3f(x, y, 0)).setScale(new Vector3f(width, height, 1));
		return this;
	}
	
	public QuadComponent updateTextureBounds(float x, float y, float width, float height) {
		getTextureTransfrom().setTranslation(new Vector3f(x, y, 0)).setScale(new Vector3f(width, height, 1));
		return this;
	}
	
	public RenderProperties clone() { return new QuadComponent(getTransform(), textureTransform); }

	public void render() { UIGraphics.drawRect(this); }
}
