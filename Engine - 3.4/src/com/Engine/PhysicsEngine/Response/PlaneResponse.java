package com.Engine.PhysicsEngine.Response;

import com.Engine.PhysicsEngine.Detection.Intersection.IntersectionResult;
import com.Engine.Util.Math.Plane;
import com.Engine.Util.Vectors.Vector3f;

public class PlaneResponse {
	public static final float MIN_DISTANCE = 0.001f;
	
	private IntersectionResult intersection;
	private Vector3f position, velocity;
	
	private Plane slidingPlane;
	
	private Vector3f newPosition;
	private Vector3f newVelocity;
	
	public PlaneResponse(IntersectionResult intersection, Vector3f position, Vector3f velocity) {
		this.intersection = intersection;
		this.position = position;
		this.velocity = velocity;
	}
	
	public void process() {
		newPosition = position;
		Vector3f destinationPoint = position.add(velocity);
		Vector3f insterectPoint = intersection.getPoint();
		
		if(intersection.getDistance() > MIN_DISTANCE) {
			Vector3f normVelocity = velocity.normalize();
			
			Vector3f scaledVelocity = normVelocity.multiply(intersection.getDistance() - MIN_DISTANCE);
			newPosition = position.add(scaledVelocity);
			
			insterectPoint = insterectPoint.subtract(normVelocity.multiply(MIN_DISTANCE));
		}
		
		this.slidingPlane = new Plane(insterectPoint, newPosition.subtract(insterectPoint).normalize());
		
		Vector3f newDestinationPoint = destinationPoint.subtract(
				slidingPlane.getNormal().multiply(slidingPlane.signedDistance(destinationPoint)));

//		velocity.subtract(slidingPlane.getNormal().multiply(2 * slidingPlane.getNormal().dot(velocity)).normalize().multiply(2)); // 
		newVelocity = newDestinationPoint.subtract(insterectPoint);
	}
	
	public Vector3f getNewPosition() { return newPosition; }
	public Vector3f getNewVelocity() { return newVelocity; }

	public Plane getSlidingPlane() { return slidingPlane; }
	public IntersectionResult getIntersection() { return intersection; }
}
