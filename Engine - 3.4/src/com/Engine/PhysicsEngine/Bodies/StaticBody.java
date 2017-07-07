package com.Engine.PhysicsEngine.Bodies;

import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMesh;
import com.Engine.Util.Vectors.Vector3f;

public class StaticBody extends PhysicsBody {
	public StaticBody(CollisionMesh mesh) {
		addBody(mesh);
	}
	
	public void setPosition(Vector3f position) { super.setPosition(position); }
	public void setRotation(Vector3f rotation) { super.setRotation(rotation); }
	public void setScale(Vector3f scale) { super.setScale(scale); }
}
