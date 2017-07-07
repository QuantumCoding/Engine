package com.Engine.PhysicsEngine.Bodies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

import com.Engine.PhysicsEngine.PhysicsEngine;
import com.Engine.PhysicsEngine.Detection.Triangle;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionEllipse;
import com.Engine.PhysicsEngine.Detection.Intersection.IntersectionResult;
import com.Engine.PhysicsEngine.Response.PlaneResponse;
import com.Engine.Util.Math.Plane;
import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.MutableVector3f;
import com.Engine.Util.Vectors.Vector3f;

import sun.net.www.content.text.plain;

public class MovingBody extends PhysicsBody {
	
	private static final int P_COUNT = 4;
	public static final float PRECISION = (float) Math.pow(10, P_COUNT);
	public static final float DECIMAL_LIMIT = (float) Math.pow(10, -P_COUNT);
	public static final float DECIMAL_LIMIT_SQ = DECIMAL_LIMIT * DECIMAL_LIMIT;
	public static final float COF = 0.65f;

	private Vector3f velocity;
	private ArrayList<Vector3f> forces;
	private IntersectionResult intersection;
	
	private boolean isOnGround;
	private Vector3f netNormal, netForce;
	
	private PhysicsEngine engine;
	
	public MovingBody(Vector3f radius) {
		this(new CollisionEllipse(radius));
	}
	
	public MovingBody(CollisionEllipse body) {
		velocity = new Vector3f();
		forces = new ArrayList<>();
		addBody(body);
	}
	
	private Vector3f top = new Vector3f();
	
	public void update(float delta) {
		Vector3f startingVelocity = velocity;
		netNormal = new MutableVector3f();
		
		Vector3f netForce = new MutableVector3f();
		for(Vector3f force : forces) netForce.add(force);
		netForce = netForce.multiply(delta).multiply(PRECISION).truncate().divide(PRECISION).lock();
		this.netForce = netForce;
		
		HashSet<Vector3f> collidedNormals = new HashSet<>();
		
		velocity = velocity.add(netForce);
		velocity = velocity.multiply(PRECISION).truncate().divide(PRECISION);
		LinkedList<IntersectionResult> intersections;
		
		intersectionsLoop: do {
			if(velocity.lengthSquared() < DECIMAL_LIMIT_SQ) break;
			
			intersections = findIntersection();
			ListIterator<IntersectionResult> results = intersections.listIterator();
			
			while(results.hasNext()) {
				IntersectionResult result = results.next();
	
				Vector3f resultSpaceVelocity = MatrixUtil.rotateScale(result.getResultSpace(), velocity);
				Vector3f newPosition = resultSpaceVelocity.multiply((result.getDistance()) / resultSpaceVelocity.length());
				Vector3f planeNormal = newPosition.subtract(result.getPoint()).normalize();
				
				if(collidedNormals.contains(planeNormal)) continue;
				collidedNormals.add(planeNormal);
				
				System.out.println(planeNormal);
//				System.out.println(MatrixUtil.rotateScale(result.getInverseResultSpace(), planeNormal));
				
				Vector3f nnewPosition = newPosition.transform(result.getInverseResultSpace());
				nnewPosition = nnewPosition.multiply(PRECISION).truncate().divide(PRECISION);
				setPosition(nnewPosition);
				
				Vector3f resultSpaceNormal = planeNormal.multiply(planeNormal.dot(resultSpaceVelocity));
				Vector3f normal = MatrixUtil.rotateScale(result.getInverseResultSpace(), resultSpaceNormal);
				normal = normal.multiply(PRECISION).truncate().divide(PRECISION);
				
				velocity = velocity.subtract(normal);
				velocity = velocity.multiply(PRECISION).truncate().divide(PRECISION);
				
				netNormal.add(normal);
				continue intersectionsLoop;
			}
			
			break;
		} while(true);
		netNormal = netNormal.multiply(PRECISION).truncate().divide(PRECISION).lock();
		
		Vector3f friction = new Vector3f();
		Vector3f normalNetNormal = netNormal.normalize();
		Vector3f projection = velocity.subtract(normalNetNormal.multiply(-velocity.dot(normalNetNormal)));
		
		float projLengthSq = projection.lengthSquared();
		if(Float.isFinite(projLengthSq) && projLengthSq > DECIMAL_LIMIT_SQ) {
			friction = projection.multiply(-netNormal.length() * COF / (float) Math.sqrt(projLengthSq));
			friction = friction.multiply(PRECISION).truncate().divide(PRECISION);
			
			if(Math.abs(friction.x) > Math.abs(velocity.x)) friction.x = -velocity.x;
			if(Math.abs(friction.y) > Math.abs(velocity.y)) friction.y = -velocity.y;
			if(Math.abs(friction.z) > Math.abs(velocity.z)) friction.z = -velocity.z;
		}

		velocity = velocity.add(friction);
//		Vector3f airResistance = new Vector3f();
//		if(velocity.lengthSquared() > DECIMAL_LIMIT_SQ) {
//			float r = ((CollisionEllipse) getBodies().get(0)).getRadius().sum() / 3;
//			float area = (float) (Math.PI * r * r);
//			airResistance = velocity.normalize().multiply(velocity.lengthSquared() * 1.225f * .47f * area * .5f * delta);
//			airResistance = airResistance.multiply(PRECISION).truncate().divide(PRECISION);
//			
//			if(Math.abs(airResistance.x) > Math.abs(velocity.x)) airResistance.x = velocity.x;
//			if(Math.abs(airResistance.y) > Math.abs(velocity.y)) airResistance.y = velocity.y;
//			if(Math.abs(airResistance.z) > Math.abs(velocity.z)) airResistance.z = velocity.z;
//			
////			System.out.println("Air Resist: " + airResistance);
//		}
		
//		velocity = velocity.multiply(0.9f);
//		velocity = velocity.subtract(airResistance);
		velocity = velocity.multiply(PRECISION).truncate().divide(PRECISION);

//		System.out.println("Friction:  " + friction);
//		System.out.println("Normal:    " + netNormal);
//		System.out.println("Start Vel: " + startingVelocity);
//		System.out.println("Final Vel:  " + velocity);
//		System.out.println("Net Force: " + netForce);
//		System.out.println();
		
//		if(velocity.abs().z > top.z) top.z = velocity.abs().z;
//		System.out.println("Top Speed:  " + top.z);
		
		setPosition(getPosition().add(velocity));
		
//		Vector3f friction = netForce.normalize().multiply(-netNormal.length() * COF);
//		netForce = netForce.subtract(netNormal).subtract(friction).lock();
		
//		setPosition(getPosition().add(netForce));
		
		isOnGround = netNormal.y < 0;
		forces.clear();
	}
	
	private LinkedList<IntersectionResult> findIntersection() { return engine.findClosestIntersection(this); }
	
	public void setVelocity(Vector3f velocity) { this.velocity = velocity; }
	
	public IntersectionResult getIntersection() { return intersection; }
	public void setIntersection(IntersectionResult intersection) { this.intersection = intersection; }
	
	public Vector3f getVelocity() { return velocity; }
	public void addForce(Vector3f force) { forces.add(force); }
	public void addRotation(Vector3f add) { this.setRotation(getRotation().add(add)); }
	
	public void setPosition(Vector3f position) { super.setPosition(position); }
	public void setRotation(Vector3f rotation) { super.setRotation(rotation); }
	
	public ArrayList<Vector3f> getForces() { return forces; }
	
	public Vector3f getNetNormal() { return netNormal; }
	public Vector3f getNetForce() { return netForce; }
	
	public boolean isOnGround() { return isOnGround; }
	
	public void setPhysicsyEngine(PhysicsEngine engine) {
		if(this.engine == engine) return;
		
		if(this.engine != null) 
			this.engine.remove(this);
		
		engine.add(this);
		this.engine = engine;
		System.out.println("Engine Set!");
	}
}

//Vector3f netForce = new MutableVector3f();
//netNormal = new Vector3f();
//
//for(Vector3f force : forces) {
//	setVelocity(force);
//	IntersectionResult result = findIntersection();
//	netForce.add(force);
//	
//	if(result == null) continue;
//	
//	Plane plane = new Plane(result.getPoint(), result.getPoint().multiply(-1).normalize());
//
//	Vector3f resultSpaceForce = MatrixUtil.rotateScale(result.getResultSpace(), force);
//	Vector3f normalForce = plane.getNormal().multiply(plane.getNormal().dot(resultSpaceForce));
//	normalForce = MatrixUtil.rotateScale(result.getInverseResultSpace(), normalForce);
//	netNormal = netNormal.add(normalForce);
//}
//
//netForce = netForce.lock();
//Vector3f friction = netForce.normalize().multiply(-netNormal.length() * COF);
//netForce = netForce.subtract(netNormal).subtract(friction).lock();
//
//setPosition(getPosition().add(netForce));
//
//forces.clear();