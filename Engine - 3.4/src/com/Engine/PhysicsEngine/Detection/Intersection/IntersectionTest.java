package com.Engine.PhysicsEngine.Detection.Intersection;

import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionBody;

public interface IntersectionTest<S extends CollisionBody, T extends CollisionBody> {
	public boolean intersects(PhysicsBody bodyA, S colliderA, PhysicsBody bodyB, T colliderB);
}
