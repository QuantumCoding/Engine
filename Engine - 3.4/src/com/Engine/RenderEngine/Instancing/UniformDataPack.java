package com.Engine.RenderEngine.Instancing;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;
import com.Engine.Util.Vectors.Vector4f;

public class UniformDataPack {
	private float[] dataPool;
	private int poolIndex;
	
	public UniformDataPack(Class<?>... objects) {
		int sum = 0;
		for(Class<?> obj : objects) {
			if(obj.equals(Matrix4f.class)) sum += 16;
			
			else if(obj.equals(Vector2f.class)) sum += 2;
			else if(obj.equals(Vector3f.class)) sum += 3;
			else if(obj.equals(Vector4f.class)) sum += 4;
			
			else if(obj.equals(Float.class)) 	sum += 1;
			else if(obj.equals(Boolean.class))  sum += 1;
			
			else throw new IllegalArgumentException("Type " + obj.getSimpleName() + " is not accepeted");
		}
		
		_init(sum);
	}
	
	public UniformDataPack(int size) { _init(size); }
	
	private void _init(int size) {
		dataPool = new float[size];
		poolIndex = 0;
	}
	
	public int getSize() { return dataPool.length; }
	
	public void reset() { poolIndex = 0; }
	public float[] getData() { return dataPool; }
	
	public void putAll(Object... objects) {
		for(Object obj : objects) {
			if(obj instanceof Matrix4f) putMatrix4f((Matrix4f) obj);
			
			else if(obj instanceof Vector2f) putVector2f((Vector2f) obj);
			else if(obj instanceof Vector3f) putVector3f((Vector3f) obj);
			else if(obj instanceof Vector4f) putVector4f((Vector4f) obj);
			
			else if(obj instanceof Float) 	putFloat((Float) obj);
			else if(obj instanceof Boolean) putBoolean((Boolean) obj);
			
			else throw new IllegalArgumentException("Type " + obj.getClass().getSimpleName() + "is not accepeted");
		}
	}
	
	public void putMatrix4f(Matrix4f matrix) {
		dataPool[poolIndex ++] = matrix.m00;	dataPool[poolIndex ++] = matrix.m01;
		dataPool[poolIndex ++] = matrix.m02;	dataPool[poolIndex ++] = matrix.m03;
		
		dataPool[poolIndex ++] = matrix.m10;	dataPool[poolIndex ++] = matrix.m11;
		dataPool[poolIndex ++] = matrix.m12;	dataPool[poolIndex ++] = matrix.m13;
		
		dataPool[poolIndex ++] = matrix.m20;	dataPool[poolIndex ++] = matrix.m21;
		dataPool[poolIndex ++] = matrix.m22;	dataPool[poolIndex ++] = matrix.m23;
		
		dataPool[poolIndex ++] = matrix.m30;	dataPool[poolIndex ++] = matrix.m31;
		dataPool[poolIndex ++] = matrix.m32;	dataPool[poolIndex ++] = matrix.m33;
	}
	
	public void putVector4f(Vector4f vector) {
		dataPool[poolIndex ++] = vector.x;	dataPool[poolIndex ++] = vector.y;
		dataPool[poolIndex ++] = vector.z;	dataPool[poolIndex ++] = vector.w;
	}
	
	public void putVector3f(Vector3f vector) {
		dataPool[poolIndex ++] = vector.x;	dataPool[poolIndex ++] = vector.y;
		dataPool[poolIndex ++] = vector.z;
	}
	
	public void putVector2f(Vector2f vector) {
		dataPool[poolIndex ++] = vector.x;	dataPool[poolIndex ++] = vector.y;
	}
	
	public void putFloat(float value) {
		dataPool[poolIndex ++] = value;
	}
	
	public void putBoolean(boolean bool) {
		dataPool[poolIndex ++] = bool ? 1 : 0;
	}
}
