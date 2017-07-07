package com.Engine.Demo.MultiRender;

import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;

public class MultiRenderProperties extends RenderProperties {
	private Texture2D texture0, texture1;
	
	public MultiRenderProperties(Transform transform, Texture2D texture0, Texture2D texture1) {
		super(transform);
		this.texture0 = texture0;
		this.texture1 = texture1;
	}
	
	public Texture2D getTexture0() { return texture0; }
	public Texture2D getTexture1() { return texture1; }

	public RenderProperties clone() { return new MultiRenderProperties(getTransform(), texture0, texture1); }
}
