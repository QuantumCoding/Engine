package com.Engine.PhysicsEngine.Detection.Intersection;

import java.util.Collection;

import com.Engine.PhysicsEngine.Bodies.MovingBody;
import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionBody;

public interface MovingIntersectionTest<S extends CollisionBody, T extends CollisionBody> {
	public Collection<IntersectionResult> intersects(MovingBody bodyA, S colliderA, PhysicsBody bodyB, T colliderB);
	
	@SuppressWarnings("unchecked")
	public default Collection<IntersectionResult> intersect(MovingBody bodyA, CollisionBody colliderA, PhysicsBody bodyB, CollisionBody colliderB) {
		Collection<IntersectionResult> results = intersects(bodyA, (S) colliderA, bodyB, (T) colliderB);
		
		if(results != null && !results.isEmpty()) {
			for(IntersectionResult result : results) {
				result.setSource(bodyA);
				result.setIntersected(bodyB);
			}
		}
			
		return results;
	}
	
	public Class<S> getMovingBodyType();
	public Class<T> getOtherBodyType();
}
