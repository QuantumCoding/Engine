package com.Engine.PhysicsEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import com.Engine.PhysicsEngine.Bodies.MovingBody;
import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionBody;
import com.Engine.PhysicsEngine.Detection.Intersection.IntersectionResult;
import com.Engine.PhysicsEngine.Detection.Intersection.MovingIntersectionTest;
import com.Engine.PhysicsEngine.Response.BlockingPlaneResponse;
import com.Engine.PhysicsEngine.Response.PlaneResponse;
import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector3f;

public class PhysicsEngine {	
	private HashSet<PhysicsBody> bodies;
	private HashSet<MovingBody> movingBodies;
	
	private HashMap<Class<? extends CollisionBody>, HashMap<Class<? extends CollisionBody>, MovingIntersectionTest<?,?>>> movingTests;
	
	public PhysicsEngine() {
		bodies = new HashSet<>();
		movingBodies = new HashSet<>();
		
		movingTests = new HashMap<>();
	}
	
	public void addIntersectionTest(MovingIntersectionTest<? extends CollisionBody, ? extends CollisionBody> test) {
		HashMap<Class<? extends CollisionBody>, MovingIntersectionTest<?, ?>> tests = null;
		if((tests = movingTests.get(test.getMovingBodyType())) == null)
			movingTests.put(test.getMovingBodyType(), tests = new HashMap<>());
		tests.put(test.getOtherBodyType(), test);
	}
	
	public void add(PhysicsBody body) {
		if(!bodies.add(body)) return;
		
		if(body instanceof MovingBody) {
			MovingBody movingBody = (MovingBody) body;
			movingBodies.add(movingBody);
			movingBody.setPhysicsyEngine(this);
		}
	}
	
	public void remove(PhysicsBody body) {
		bodies.remove(body);
		
		if(body instanceof MovingBody)
			movingBodies.remove((MovingBody) body);
	}
	
	public void simulate(float delta) {
		try {
			for(MovingBody moving : movingBodies) {
//				Vector3f newPos = collideWithWorld(moving.getPosition(), moving.getVelocity(), moving, 0);
//				
//				for(Force force : moving.getAllForces()) {
//					newPos = collideWithWorld(newPos, force.getValue().multiply(force.isConstant() ? delta : 1), moving, 0);
//				}
//	
//				moving.setPosition(newPos);
				moving.update(delta);
			}
		} catch(StackOverflowError error) {
			System.exit(1);
		}
	}

	private Vector3f collideWithWorld(Vector3f position, Vector3f velocity, MovingBody body, int level) {
		if(level > 5) return position;

		velocity = velocity.multiply(0.9f);
		body.setPosition(position);
		body.setVelocity(velocity);
		
		IntersectionResult result = findClosestIntersection(body).getFirst();
		if(result == null) return position.add(velocity);
		
		position = position.transform(result.getResultSpace());
		velocity = MatrixUtil.rotateScale(result.getResultSpace(), velocity);
		
		BlockingPlaneResponse response = new BlockingPlaneResponse(result, position, velocity);
		response.process();
		
		position = response.getNewPosition().transform(result.getInverseResultSpace());
		velocity = new Vector3f();//.rotateScale(result.getInverseResultSpace(), response.getNewVelocity());
		
		if(velocity.length() < PlaneResponse.MIN_DISTANCE)
			return position;
		
		return collideWithWorld(position, velocity, body, level + 1);
	}
	
	public LinkedList<IntersectionResult> findClosestIntersection(MovingBody moving) {
		LinkedList<IntersectionResult> list = new LinkedList<>();
//		IntersectionResult result = null;
		
		for(PhysicsBody body : bodies) {
			if(body == moving) continue;
			
			MovingIntersectionTest<? extends CollisionBody, ? extends CollisionBody> intersectionTest = null;
			CollisionBody movingCollider = null, physicsCollider = null;
			
			intersectionTestSearch:
			for(CollisionBody mBody : moving.getBodies()) {
				HashMap<Class<? extends CollisionBody>, MovingIntersectionTest<?,?>> tests = movingTests.get(mBody.getClass());
				if(tests == null || tests.isEmpty()) continue;
				
				for(CollisionBody pBody : body.getBodies()) {
					if((intersectionTest = tests.get(pBody.getClass())) != null) {
						movingCollider = mBody;
						physicsCollider = pBody;
						
						break intersectionTestSearch;
					}
				}
			}
			
			if(intersectionTest == null) {
//				System.err.println("Cannot find registered Collider for " + moving + " and " + body);
				continue;
			}
			
			Collection<IntersectionResult> intersections = intersectionTest.intersect(moving, movingCollider, body, physicsCollider);
			if(intersections == null || intersections.isEmpty()) continue;
			list.addAll(intersections);
//			if(result == null || intersection.getDistance() < result.getDistance())
//				result = intersection;
		}
		
		list.sort(null);
		return list;
	}
}
