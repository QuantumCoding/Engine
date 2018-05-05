package com.Engine.RenderEngine.Font.Render;

import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.Util.Vectors.Vector4f;

public class TextRenderProperties extends RenderProperties {
	private Vector4f colour;
	
	public TextRenderProperties(Transform transform, Vector4f colour) {
		super(transform);
		this.colour = colour;
	}
	
	public Vector4f getColour() { return colour; }
	
	public void setColour(Vector4f colour) { this.colour = colour; }
	
	public RenderProperties clone() { return new TextRenderProperties(getTransform(), colour); }
}
