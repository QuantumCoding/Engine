package com.Engine.PhysicsEngine.Response;

import com.Engine.PhysicsEngine.Detection.Intersection.IntersectionResult;
import com.Engine.Util.Math.Plane;
import com.Engine.Util.Vectors.Vector3f;

public class BlockingPlaneResponse {
	public static final float MIN_DISTANCE = 0.001f;
	
	private IntersectionResult intersection;
	private Vector3f position, velocity;
	
	private Plane slidingPlane;
	
	private Vector3f newPosition;
	
	public BlockingPlaneResponse(IntersectionResult intersection, Vector3f position, Vector3f velocity) {
		this.intersection = intersection;
		this.position = position;
		this.velocity = velocity;
	}
	
	public void process() {
		newPosition = position;
		
		if(intersection == null) {
			newPosition = position.add(velocity);
			
		} if(intersection.getDistance() > MIN_DISTANCE) {
			Vector3f normVelocity = velocity.normalize();
			
			Vector3f scaledVelocity = normVelocity.multiply(intersection.getDistance() - MIN_DISTANCE);
			newPosition = position.add(scaledVelocity);
		}
	}
	
	public Vector3f getNewPosition() { return newPosition; }

	public Plane getSlidingPlane() { return slidingPlane; }
	public IntersectionResult getIntersection() { return intersection; }
}
