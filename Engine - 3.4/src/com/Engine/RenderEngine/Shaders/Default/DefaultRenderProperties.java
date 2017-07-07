package com.Engine.RenderEngine.Shaders.Default;

import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;

public class DefaultRenderProperties extends RenderProperties {
	private float shineDamper;
	private float reflectivity;
	
	private int textureAtlasIndex;

	public DefaultRenderProperties() { this(new Transform()); }
	
	public DefaultRenderProperties(Transform transform, float shineDamper, float reflectivity, int textureAtlasIndex) {
		super(transform);
		
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
		this.textureAtlasIndex = textureAtlasIndex;
	}

	public DefaultRenderProperties(int textureAtlasIndex) {
		this(new Transform(), 1, 0, textureAtlasIndex);
	}

	public DefaultRenderProperties(float shineDamper, float reflectivity) {
		this(new Transform(), shineDamper, reflectivity, 0);
	}

	public DefaultRenderProperties(Transform transform) {
		this(transform, 1, 0, 0);
	}

	public float getShineDamper() { return shineDamper; }
	public float getReflectivity() { return reflectivity; }
	
	public int getTextureAtlasIndex() { return textureAtlasIndex; }
	
	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public void setTextureAtlasIndex(int textureAtlasIndex) {
		this.textureAtlasIndex = textureAtlasIndex;
	}

	public DefaultRenderProperties clone() {
		return new DefaultRenderProperties(getTransform().clone(), shineDamper, reflectivity, textureAtlasIndex);
	}
}
