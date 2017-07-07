package com.Engine.RenderEngine.Util;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class RenderStructs {
	private RenderStructs() {}
	
	public static class Transform {
		private Vector3f translation;
		private Vector3f rotation;
		private Vector3f scale;
		
		private Matrix4f transformMatrix;
		
		public Transform() { this(null, null, null); }
		
		public Transform(Vector3f translation, Vector3f rotation, Vector3f scale) {
			this.translation = translation == null ? new Vector3f(0) : translation;
			this.rotation = rotation == null ? new Vector3f(0) : rotation;
			this.scale = scale == null ? new Vector3f(1) : scale;
		}

		public Matrix4f getTransformMatrix() {
			if(transformMatrix == null ) 
				transformMatrix = MatrixUtil.initTransformationMatrix(translation, rotation, scale);
			return transformMatrix;
		}
		
		public Vector3f getTranslation() { return translation; }
		public Vector3f getRotation() { return rotation; }
		public Vector3f getScale() { return scale; }

		public void translate(Vector3f amount) { translation = translation.add(amount); transformMatrix = null; }
		public void rotate(Vector3f amount) { rotation = rotation.add(amount); transformMatrix = null; }
		public void scale(Vector3f amount) { scale = scale.add(amount); transformMatrix = null; }
		
		public void setTranslation(Vector3f translation) {
			this.translation = translation;
			transformMatrix = null; 
		}

		public void setRotation(Vector3f rotation) {
			this.rotation = rotation;
			transformMatrix = null; 
		}

		public void setScale(Vector3f scale) {
			this.scale = scale;
			transformMatrix = null; 
		}	
		
		public void transformBy(Transform other) {
			translate(other.getTranslation());
			rotate(other.getRotation());
			scale(other.getScale());
		}
		
		public Transform clone() {
			return new Transform(
					translation.clone(), 
					rotation.clone(), 
					scale.clone()
				);
		}

		public Transform reset() {
			this.translation.reset();
			this.rotation.reset();
			this.scale.reset();

			transformMatrix = null; 
			return this;
		}

		public Transform weight(float weight) {
			translation = translation.multiply(weight);
			rotation = rotation.multiply(weight);
			scale = scale.multiply(weight);
			
			transformMatrix = null; 
			return this;
		}
	}
	
	public static class Vertex {
		private	Vector3f position;
		private	Vector2f texCoord;
		private Vector3f normal;
		
		public Vertex(Vector3f position, Vector2f texCoord, Vector3f normal) {
			this.position = position;
			this.texCoord = texCoord;
			this.normal = normal;
		}

		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((normal == null) ? 0 : normal.hashCode());
			result = prime * result
					+ ((position == null) ? 0 : position.hashCode());
			result = prime * result
					+ ((texCoord == null) ? 0 : texCoord.hashCode());
			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (!(obj instanceof Vertex)) return false;
			
			Vertex other = (Vertex) obj;
			if (normal == null) {
				if (other.normal != null) return false;
			} else if (!normal.equals(other.normal)) return false;
			
			if (position == null) {
				if (other.position != null) return false;
			} else if (!position.equals(other.position)) return false;
			
			if (texCoord == null) {
				if (other.texCoord != null) return false;
			} else if (!texCoord.equals(other.texCoord)) return false;
			
			return true;
		}

		public Vertex clone() {
			return new Vertex(position.clone(), texCoord.clone(), normal.clone());
		}
		
		public Vector3f getPosition() { return position; }
		public Vector2f getTexCoord() { return texCoord; }
		public Vector3f getNormal() { return normal; }
		
		public void addTo(int index, float[] positions, float[] texCoords, float[] normals) {
			positions[index * 3 + 0] = getPosition().x;
			positions[index * 3 + 1] = getPosition().y;
			positions[index * 3 + 2] = getPosition().z;
		
			texCoords[index * 2 + 0] = getTexCoord().x;
			texCoords[index * 2 + 1] = getTexCoord().y;
		
			normals[index * 3 + 0] = getNormal().x;
			normals[index * 3 + 1] = getNormal().y;
			normals[index * 3 + 2] = getNormal().z;
		}

		public String toString() {
			return "Vertex [position=" + position + ", texCoord=" + texCoord + ", normal=" + normal + "]";
		}
	}
}
