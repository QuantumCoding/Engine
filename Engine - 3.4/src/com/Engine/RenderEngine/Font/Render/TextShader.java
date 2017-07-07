package com.Engine.RenderEngine.Font.Render;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector4f;

public class TextShader extends Shader {
	private static final String VERTEX_SHADER_LOC = "FontShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "FontShader.fsh";
	
	private int location_projectionMatrix;
	private int location_modelViewMatrix;

	private int location_colour;
	private int location_texture0;
	
	public TextShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, TextRenderer.class);
	}

	@Override
	protected void initUniformLocations() {
		bind();
		
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_modelViewMatrix =  super.getUniformLocation("modelViewMatrix");

		location_colour = super.getUniformLocation("colour");
		location_texture0 = super.getUniformLocation("texture0");
		
		super.loadInt(location_texture0, 0);
	}

	@Override
	protected void bindAttributies() {
		bind();
		
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		super.bindAttribute(ATTRIBUTE_LOC_TEXCOORDS, "texCoord_in");
	}
	
	public void loadModelViewMatrix(Matrix4f modelViewMatrix) {
		super.loadMatrix(location_modelViewMatrix, modelViewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
	
	public void loadColour(Vector4f colour) {
		super.loadVector4f(location_colour, colour);
	}
}
