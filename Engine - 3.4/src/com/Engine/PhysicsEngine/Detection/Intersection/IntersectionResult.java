package com.Engine.PhysicsEngine.Detection.Intersection;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.PhysicsEngine.Bodies.MovingBody;
import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.PhysicsEngine.Detection.Triangle;
import com.Engine.Util.Vectors.Vector3f;

public class IntersectionResult implements Comparable<IntersectionResult> {
	private Vector3f point;
	private float distance;
	
	private PhysicsBody intersected;
	private MovingBody source;
	
	private Matrix4f resultSpace;
	private Matrix4f inverseResultSpace;
	
	public Triangle triangle;
	public boolean wasEdge;
	
	public IntersectionResult(Vector3f point, float distance) {
		this.point = point;
		this.distance = distance;
	}

	public Vector3f getPoint() { return point; }
	public float getDistance() { return distance; }

	public void setIntersected(PhysicsBody intersected) { this.intersected = intersected; }
	public void setSource(MovingBody source) { this.source = source; }
	
	public PhysicsBody getIntersected() { return intersected; }
	public PhysicsBody getSource() { return source; }
	
	public void setResultSpace(Matrix4f resultSpace) { this.resultSpace = resultSpace; this.inverseResultSpace = null; }
	public Matrix4f getResultSpace() { return resultSpace; }
	
	public Matrix4f getInverseResultSpace() {
		if(inverseResultSpace == null) {
			inverseResultSpace = Matrix4f.invert(resultSpace, null);
		}
		
		return inverseResultSpace;
	}
	
	public String toString() {
		return "IntersectionResult [point=" + point + ", distance=" + distance + "]";
	}

	public int compareTo(IntersectionResult o) {
		if(!Float.isFinite(distance)) return 1;
		if(!Float.isFinite(o.distance)) return -1;
		
		if(Math.abs(distance - o.distance) < 0.0001f)
			return wasEdge ? -1 : 1;
		
		if(distance < o.distance) return -1;
		return  1;
	}
}
