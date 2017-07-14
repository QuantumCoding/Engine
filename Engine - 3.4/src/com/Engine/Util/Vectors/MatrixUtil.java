package com.Engine.Util.Vectors;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;

public class MatrixUtil {
	
	public static Matrix4f initPerspectiveMatrix(float fov, float aspectRatio, float zNear, float zFar) {
		Matrix4f matrix = new Matrix4f();
		
		float scaleY = (float) (1.0f / Math.tan(Math.toRadians(fov / 2.0f)) * aspectRatio);
		float scaleX = scaleY / aspectRatio;
		float frustumLength = zFar - zNear;
		
		matrix.m00 = scaleX;
		matrix.m11 = scaleY;
		matrix.m22 = -((zFar + zNear) / frustumLength);
		matrix.m23 = -1;
		matrix.m32 = -((2 * zNear * zFar) / frustumLength);
		matrix.m33 = 0;
		
		return matrix;
	}

	public static Matrix4f initOrthographicMatrix(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f matrix = new Matrix4f();
		
		float width = right - left;
		float height = top - bottom;
		float depth = far - near;

		matrix.m00 =  2/width;
		matrix.m11 =  2/height;
		matrix.m22 = -2/depth;
		           
		matrix.m30 = -(right + left)/width; 
		matrix.m31 = -(top + bottom)/height;
		matrix.m32 = -(far + near)/depth;   
		matrix.m33 = 1;                     

		return matrix;
	}
	
	public static Matrix4f initRotationMatrix(Vector3f rotation) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		matrix.rotate((float) Math.toRadians(rotation.x), new org.lwjgl.util.vector.Vector3f(1, 0, 0));
		matrix.rotate((float) Math.toRadians(rotation.y), new org.lwjgl.util.vector.Vector3f(0, 1, 0));
		matrix.rotate((float) Math.toRadians(rotation.z), new org.lwjgl.util.vector.Vector3f(0, 0, 1));

		return matrix;
	}
	
	public static Matrix4f	initRotationMatrix(Vector4f quat) {
		Matrix4f matrix = new Matrix4f();
		
		float xy = quat.x * quat.y;
		float xz = quat.x * quat.z;
		float xw = quat.x * quat.w;
		float yz = quat.y * quat.z;
		float yw = quat.y * quat.w;
		float zw = quat.z * quat.w;
		
		float xSq = quat.x * quat.x;
		float ySq = quat.y * quat.y;
		float zSq = quat.z * quat.z;
		
		matrix.m00 = 1 - 2 * (ySq + zSq);
		matrix.m01 = 2 * (xy - zw);
		matrix.m02 = 2 * (xz + yw);
		
		matrix.m10 = 2 * (xy + zw);
		matrix.m11 = 1 - 2 * (xSq + zSq);
		matrix.m12 = 2 * (yz - xw);
		
		matrix.m20 = 2 * (xz - yw);
		matrix.m21 = 2 * (yz + xw);
		matrix.m22 = 1 - 2 * (xSq + ySq);
		
		return matrix;
	}
	
	public static Matrix4f initTransformationMatrix(Vector3f translation, Vector3f rotation, Vector3f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		matrix.translate(translation.toLWJGL());
		matrix.rotate((float) Math.toRadians(rotation.x), new org.lwjgl.util.vector.Vector3f(1, 0, 0));
		matrix.rotate((float) Math.toRadians(rotation.y), new org.lwjgl.util.vector.Vector3f(0, 1, 0));
		matrix.rotate((float) Math.toRadians(rotation.z), new org.lwjgl.util.vector.Vector3f(0, 0, 1));
		matrix.scale(scale.toLWJGL());
		
		return matrix;
	}
	
	public static Matrix4f initViewMatrix(Camera camera) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		matrix.scale(new org.lwjgl.util.vector.Vector3f(camera.getScale(), camera.getScale(), camera.getScale()));
		
		matrix.rotate((float) Math.toRadians(camera.getRotX()), new org.lwjgl.util.vector.Vector3f(1, 0, 0));
		matrix.rotate((float) Math.toRadians(camera.getRotY()), new org.lwjgl.util.vector.Vector3f(0, 1, 0));
		matrix.rotate((float) Math.toRadians(camera.getRotZ()), new org.lwjgl.util.vector.Vector3f(0, 0, 1));
		
		matrix.translate(new org.lwjgl.util.vector.Vector3f(-camera.getX(), -camera.getY(), -camera.getZ()));
		
		return matrix;
	}
	
	public static Matrix4f createModelViewMatrix(Transform transform, Matrix4f viewMatrix) {
		Matrix4f modelMatrix = new Matrix4f().translate(transform.getTranslation().toLWJGL());
		MatrixUtil.transposeRotation(viewMatrix, modelMatrix);
		
		modelMatrix.rotate((float) Math.toRadians(transform.getRotation().x), new org.lwjgl.util.vector.Vector3f(1, 0, 0));
		modelMatrix.rotate((float) Math.toRadians(transform.getRotation().y), new org.lwjgl.util.vector.Vector3f(0, 1, 0));
		modelMatrix.rotate((float) Math.toRadians(transform.getRotation().z), new org.lwjgl.util.vector.Vector3f(0, 0, 1));
		
		modelMatrix.scale(transform.getScale().toLWJGL());
		return Matrix4f.mul(viewMatrix, modelMatrix, null);
	}
	
	public static Matrix4f lookAt(Vector3f eye, Vector3f center, Vector3f up) {
		return lookAt(eye, center, up, false); }
	public static Matrix4f lookAt(Vector3f eye, Vector3f center, Vector3f up, boolean leftHanded) {
		Matrix4f result = (Matrix4f) new Matrix4f().setIdentity();
		Vector3f f = center.subtract(eye).normalize();
		
		Vector3f s, u;
		if(leftHanded) {
			s = up.crossProduct(f).normalize();
			u = f.crossProduct(s);
		} else {
			s = f.crossProduct(up).normalize();
			u = s.crossProduct(f);
		}

		result.m00 = s.x;
		result.m01 = s.y;
		result.m02 = s.z;
		
		result.m10 = u.x;
		result.m11 = u.y;
		result.m12 = u.z;
		
		result.m20 = f.x * (leftHanded ? 1 : -1);
		result.m21 = f.y * (leftHanded ? 1 : -1);
		result.m22 = f.z * (leftHanded ? 1 : -1);
		
		result.m30 = -eye.x; // -s.dot(eye);
		result.m31 = -eye.y; // -u.dot(eye);
		result.m32 = -eye.z; // -f.dot(eye) * (leftHanded ? 1 : -1);
		
		return result;
	}
	
	public static Matrix4f transposeRotation(Matrix4f matrix, Matrix4f dest) {
		if(dest == null) 
			dest = new Matrix4f();
		
		dest.m00 = matrix.m00;
		dest.m01 = matrix.m10;
		dest.m02 = matrix.m20;

		dest.m10 = matrix.m01;
		dest.m11 = matrix.m11;
		dest.m12 = matrix.m21;

		dest.m20 = matrix.m02;
		dest.m21 = matrix.m12;
		dest.m22 = matrix.m22;
		
		return dest;
	}
	
	public static Vector3f rotateScale(Matrix4f left, Vector3f right) {
		float x = left.m00 * right.x + left.m10 * right.y + left.m20 * right.z; 
		float y = left.m01 * right.x + left.m11 * right.y + left.m21 * right.z; 
		float z = left.m02 * right.x + left.m12 * right.y + left.m22 * right.z; 

		return new Vector3f(x, y, z);
	}
	
	public static Matrix4f copyScale(Vector3f vec, Matrix4f src, Matrix4f dest) {
		if(dest == null) dest = new Matrix4f();
		
		dest.m00 = src.m00 * vec.x;
		dest.m01 = src.m01 * vec.x;
		dest.m02 = src.m02 * vec.x;
		dest.m03 = src.m03 * vec.x;
		
		dest.m10 = src.m10 * vec.y;
		dest.m11 = src.m11 * vec.y;
		dest.m12 = src.m12 * vec.y;
		dest.m13 = src.m13 * vec.y;
		
		dest.m20 = src.m20 * vec.z;
		dest.m21 = src.m21 * vec.z;
		dest.m22 = src.m22 * vec.z;
		dest.m23 = src.m23 * vec.z;
		
		dest.m30 = src.m30;
		dest.m31 = src.m31;
		dest.m32 = src.m32;
		dest.m33 = src.m33;
		
		return dest;
	}
	
	public static Vector3f projectPoint(Vector2f screenPoint, boolean nearPlane){
		Vector4f in = new Vector4f(screenPoint.x * 2 - 1, screenPoint.y * 2 - 1, nearPlane ? 0 : 1, 1);
		Matrix4f inverse = Matrix4f.mul(Shader.getProjectionMatrix(), Shader.getViewMatrix(), null);
		inverse = (Matrix4f) inverse.invert();
		
		org.lwjgl.util.vector.Vector4f lwjglOut = Matrix4f.transform(inverse, in.toLWJGL(), null);
		Vector4f out = new Vector4f(lwjglOut);
		return new Vector3f(out.x / out.w, out.y / out.w, out.z / out.w);
	}
	
	public static Vector3f forward(Matrix4f viewMatrix) {
		return new Vector3f(viewMatrix.m02, viewMatrix.m12, viewMatrix.m22);
	}
	
	public static Vector3f up(Matrix4f viewMatrix) {
		return new Vector3f(viewMatrix.m01, viewMatrix.m11, viewMatrix.m21);
	}
	
	public static Vector3f right(Matrix4f viewMatrix) {
		return new Vector3f(viewMatrix.m00, viewMatrix.m10, viewMatrix.m20);
	}
}