package com.Engine.PhysicsEngine.Render.Vector;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.PhysicsEngine.Render.PhysicsRenderProperties;
import com.Engine.RenderEngine.Models.ModelLoader;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Shaders.Render.RenderProperties;
import com.Engine.RenderEngine.System.RenderingSystem;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector3f;

public class VectorModel {
	private static final Vector3f MODEL_DEFAULT = new Vector3f(0, 1, 0);
	private static Model body, top;
	
	@SuppressWarnings("unchecked")
	public static void init(RenderingSystem<Model, ? super PhysicsRenderProperties> shader) {
		body = new Model(ModelLoader.loadOBJ("res/models/physics/VectorBody.obj"));
		top = new Model(ModelLoader.loadOBJ("res/models/physics/VectorTop.obj"));
		
		body.setShader((RenderingSystem<Model, RenderProperties>) shader);
		top.setShader((RenderingSystem<Model, RenderProperties>) shader);
	}
	
	public static void renderVector(Vector3f vector, Vector3f translation, Vector3f color) {
		float magnitude = vector.length();
		
		Matrix4f bodyMatrix, headMatrix;
		
		if(Math.abs(magnitude - Math.abs(vector.y)) <= 0.001) {
			bodyMatrix = new Matrix4f().translate(translation.toLWJGL()).scale(new Vector3f(1, magnitude * .9 * (vector.y > 0 ? 1 : -1), 1).toLWJGL());
			headMatrix = new Matrix4f().translate(translation.add(0, .9f * magnitude * (vector.y > 0 ? 1 : -1), 0).toLWJGL());
//			System.out.println(MODEL_DEFAULT.transform(fullMatrix).multiply(1000).round().divide(1000));
		
		} else {
		
			Vector3f axis = vector.crossProduct(MODEL_DEFAULT).normalize();
			float angle = (float) Math.toDegrees(Math.acos(MODEL_DEFAULT.dot(vector) / magnitude));

			Matrix4f magMatrix = new Matrix4f().scale(new Vector3f(1, magnitude * .9, 1).toLWJGL());
			Matrix4f rotMatrix = MatrixUtil.initRotationMatrix(angle, axis);
			
			bodyMatrix = Matrix4f.mul(rotMatrix, magMatrix, null);
			headMatrix = rotMatrix.translate(translation.toLWJGL());
//			Matrix4f.mul(rotMatrix, new Matrix4f().translate(translation.add(0, .9f * magnitude, 0).toLWJGL()), null);
			
//			fullMatrix.scale(new Vector3f(magnitude, magnitude, magnitude).toLWJGL());
//			System.out.println(MODEL_DEFAULT.transform(fullMatrix).multiply(1000).round().divide(1000));
		}
		
		bodyMatrix.m30 = translation.x;
		bodyMatrix.m31 = translation.y;
		bodyMatrix.m32 = translation.z;
		bodyMatrix.m33 = 1;
		
//		bodyMatrix = bodyMatrix.translate(translation.toLWJGL());
//		headMatrix = headMatrix.translate(translation.toLWJGL());
		
		Transform transform = new MatrixTransform(bodyMatrix);
		body.render(new PhysicsRenderProperties(transform, color, true));

		Transform transformHead = new MatrixTransform(headMatrix);
		top.render(new PhysicsRenderProperties(transformHead, color.subtract(0.1f).capMin(0), true));
	}
	
//	public static void renderVector(Vector3f vector, Vector3f translation, Vector3f color) {
//		float magnitude = vector.length();
//		
//		Matrix4f fullMatrix;
//		
//		if(Math.abs(magnitude - Math.abs(vector.y)) <= 0.001) {
//			fullMatrix = new Matrix4f().scale(new Vector3f(magnitude * (vector.y > 0 ? 1 : -1)).toLWJGL());
//			System.out.println(MODEL_DEFAULT.transform(fullMatrix).multiply(1000).round().divide(1000));
//		
//		} else {
//		
//			Vector3f axis = vector.crossProduct(MODEL_DEFAULT).normalize();
//			float angle = (float) Math.toDegrees(Math.acos(MODEL_DEFAULT.dot(vector) / magnitude));
//
//			float x = axis.x, y = axis.y, z = axis.z;
//			float xSq = x * x, ySq = y * y, zSq = z * z;
//			float cos = (float) Math.cos(angle), sin = (float) Math.sin(angle);
//			
//			fullMatrix = new Matrix4f();
//			fullMatrix.m00 = xSq + (ySq * zSq) * cos; 		fullMatrix.m10 = y * x * (1 - cos) - z * sin; 	fullMatrix.m20 = z * x * (1 - cos) + y * sin;
//			fullMatrix.m01 = x * y * (1 - cos) + z * sin; 	fullMatrix.m11 = ySq + (xSq * zSq) * cos; 		fullMatrix.m21 = z * y * (1 - cos) - x * sin;
//			fullMatrix.m02 = x * z * (1 - cos) - y * sin; 	fullMatrix.m12 = y * z * (1 - cos) + x * sin; 	fullMatrix.m22 = zSq + (ySq * zSq) * cos;
//			
//			fullMatrix.scale(new Vector3f(magnitude, magnitude, magnitude).toLWJGL());
//			System.out.println(MODEL_DEFAULT.transform(fullMatrix).multiply(1000).round().divide(1000));
//		}
//		
//		fullMatrix.m30 = translation.x;
//		fullMatrix.m31 = translation.y;
//		fullMatrix.m32 = translation.z;
//		fullMatrix.m33 = 1;
//		
////		Transform transform = new MatrixTransform(fullMatrix);
////		body.render(new PhysicsRenderProperties(transform, color, true));
////
////		Transform transformHead = new MatrixTransform(Matrix4f.translate(new Vector3f(0, magnitude, 0).toLWJGL(), fullMatrix, 
////				Matrix4f.mul(new Matrix4f(), fullMatrix, null)));
////		top.render(new PhysicsRenderProperties(transformHead, color.subtract(0.1f).capMin(0), true));
//	}
	
//	public static void renderVector(Vector3f vector, Vector3f translation, Vector3f color) {
//		float magnitude = vector.length();
//		
//		Matrix4f fullMatrix;
//		
//		if(Math.abs(magnitude - Math.abs(vector.y)) <= 0.001) {
//			fullMatrix = new Matrix4f().scale(new Vector3f(magnitude * (vector.y > 0 ? 1 : -1)).toLWJGL());
//			System.out.println(MODEL_DEFAULT.transform(fullMatrix).multiply(1000).round().divide(1000));
//		
//		} else {
//		
//			Vector3f axis = vector.crossProduct(MODEL_DEFAULT).normalize();
//			float angle = (float) Math.toDegrees(Math.acos(MODEL_DEFAULT.dot(vector) / magnitude));
//	
//			float baseLength = (float) Math.sqrt(axis.x*axis.x + axis.z*axis.z);
//			float baseAngle  = (float) Math.asin(axis.z / baseLength);
//			
//			float planeAngle = (float) Math.asin(axis.y);
//			
//			Vector3f rotation = new Vector3f(Math.PI / 2 - planeAngle, Math.PI / 2 - baseAngle, 0).toDegrees();
//			
//	//		System.out.println(baseAngle + ", " + planeAngle);
//	//		System.out.println(axis + " ==== " + axis.rotate(rotation));
//	//		System.out.println(angle + " ---- " + MODEL_DEFAULT
//	//				.rotate(rotation)
//	//				.rotate(new Vector3f(0, angle, 0))
//	//				.invertRotate(rotation).multiply(magnitude));
//			
//			Matrix4f rotMatrix = MatrixUtil.initRotationMatrix(rotation);
//			Matrix4f invMatrix = (Matrix4f) Matrix4f.invert(rotMatrix, null);
//			Matrix4f angMatrix = MatrixUtil.initRotationMatrix(new Vector3f(0, angle, 0));
//			
//			fullMatrix = Matrix4f.mul(Matrix4f.mul(invMatrix, angMatrix, null), rotMatrix, null);
//			fullMatrix.scale(new Vector3f(magnitude, magnitude, magnitude).toLWJGL());
//			System.out.println(MODEL_DEFAULT.transform(fullMatrix).multiply(1000).round().divide(1000));
//		}
//		
//		Transform transform = new MatrixTransform(fullMatrix);
//		body.render(new PhysicsRenderProperties(transform, color, true));
//				
//
//		Transform transformHead = new MatrixTransform(Matrix4f.translate(new Vector3f(0, magnitude, 0).toLWJGL(), fullMatrix, 
//				Matrix4f.mul(new Matrix4f(), fullMatrix, null)));
//		top.render(new PhysicsRenderProperties(transformHead, color.subtract(0.1f).capMin(0), true));
//	}
	
	private static class MatrixTransform extends Transform {
		private Matrix4f transform;

		public MatrixTransform(Matrix4f transform) {
			this.transform = transform;
		}
		
		public Matrix4f getTransformMatrix() { return transform; }
	}
}
