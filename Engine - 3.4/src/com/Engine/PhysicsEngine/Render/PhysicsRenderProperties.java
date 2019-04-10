package com.Engine.PhysicsEngine.Render;

import com.Engine.RenderEngine.Shaders.Render.RenderProperties;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.Util.Vectors.Vector3f;

public class PhysicsRenderProperties extends RenderProperties {
	
	private Vector3f color;
	private boolean quadRender;
	private boolean solid;
	
	public PhysicsRenderProperties(Vector3f color, boolean solid) { this(new Transform(), color, solid); }
	public PhysicsRenderProperties(Transform transform, Vector3f color, boolean solid) { this(transform, color, solid, false); }
	
	public PhysicsRenderProperties(Transform transform, Vector3f color, boolean solid, boolean quadRender) {
		super(transform);
		
		this.color = color;
		this.quadRender = quadRender;
		this.solid = solid;
	}
	
	public Vector3f getColor() { return color; }
	public boolean useQuadRender() { return quadRender; }
	public boolean isSolid() { return solid; }
	
	public RenderProperties clone() {
		return new PhysicsRenderProperties(getTransform().clone(), color.clone(), solid, quadRender);
	}
}
