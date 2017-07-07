package com.Engine.PhysicsEngine.Render;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector3f;

public class PhysicsShader extends Shader {
	private static final String VERTEX_SHADER_LOC = "PhysicsSphereShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "PhysicsSphereShader.fsh";
	
	private int location_color;
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
	public PhysicsShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, PhysicsRenderer.class);
	}

	protected void initUniformLocations() {
		bind();
		
		location_color = super.getUniformLocation("color");

		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix =  super.getUniformLocation("viewMatrix");
	}

	protected void bindAttributies() {
		bind();
		
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
	}
	
	public void loadTransformationMatrix(Matrix4f transformationMatrix) {
		super.loadMatrix(location_transformationMatrix, transformationMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix) {
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadColor(Vector3f color) {
		super.loadVector3f(location_color, color);
	}
}
