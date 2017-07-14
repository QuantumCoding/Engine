package com.Engine.Demo.OrthoTesting;

import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;

public class OrthoRenderProperties extends RenderProperties {

	public OrthoRenderProperties(Transform transform) { super(transform); }

	public RenderProperties clone() { return new OrthoRenderProperties(super.getTransform()); }
}
