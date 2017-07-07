package com.Engine.PhysicsEngine.Render.Vector;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.PhysicsEngine.Render.PhysicsRenderProperties;
import com.Engine.PhysicsEngine.Render.PhysicsShader;
import com.Engine.RenderEngine.Models.ModelLoader;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector3f;

public class VectorModel {
	private static Model body, top;
	
	public static void init(PhysicsShader shader) {
		body = new Model(ModelLoader.loadOBJ("res/models/physics/VectorBody.obj"));
		top = new Model(ModelLoader.loadOBJ("res/models/physics/VectorTop.obj"));
		
		body.setShader(shader);
		top.setShader(shader);
	}
	
	public static void renderVector(Vector3f vector, Vector3f translation, Vector3f color, Camera camera) {
		Vector3f vectorOrig = vector.clone();
		
		float magnitude = vector.length();
		vector = new Vector3f(vector.y, vector.x, vector.z);
		
		Vector3f angle = new Vector3f(
				Math.asin(vector.z / magnitude), 0,
				Math.atan2(vector.y, vector.x)
			).toDegrees();
		
		Vector3f rot = new Vector3f();
		if(vector.x != 0 || vector.y != 0) {
			rot.z = (float) Math.atan2(vector.y, vector.x);
			rot.y = (float) Math.atan2(Math.sqrt(vector.x * vector.x + vector.y * vector.y), vector.z);
		} else if(vector.z < 0) {
			rot.x = 180;
		}
		
		Matrix4f lookAt = MatrixUtil.initRotationMatrix(rot);
		
//		translation = new Vector3f();
//		Matrix4f lookAt = MatrixUtil.lookAt(new Vector3f(-5, 0, -5), vectorOrig.add(-5, 0, -5), new Vector3f(0, 0, 1));
//		lookAt = lookAt.translate(new Vector3f(0, 5, 0).toLWJGL());
//		lookAt = lookAt.scale(new Vector3f(1, vector.length(), 1).toLWJGL());
		Transform transform = new MatrixTransform(lookAt);
		
		body.render(new PhysicsRenderProperties(transform,
//				new Transform(translation, angle, new Vector3f(1, magnitude, 1)), 
				color, true), camera);
		
		top.render(new PhysicsRenderProperties(
				new Transform(new Vector3f(0, magnitude, 0).rotate(angle).add(translation), angle, new Vector3f(1)), 
				color.subtract(0.1f).capMin(0), true), camera);
	}
	
	private static class MatrixTransform extends Transform {
		private Matrix4f transform;

		public MatrixTransform(Matrix4f transform) {
			this.transform = transform;
		}
		
		public Matrix4f getTransformMatrix() { return transform; }
	}
}
