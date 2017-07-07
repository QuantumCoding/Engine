package com.Engine.Demo.MultiRender;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Shaders.Shader;

public class MultiShader extends Shader {
	
	private static final String VERTEX_SHADER_LOC = "MultiShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "MultiShader.fsh";

    private int location_projectionMatrix;
    private int location_modelViewMatrix;
    private int location_texture0;
    private int location_texture1;
	
	public MultiShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, MultiRenderer.class);
	}

	@Override
	protected void initUniformLocations() {
		bind();
		
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_modelViewMatrix = super.getUniformLocation("modelViewMatrix");
        
        location_texture0 = super.getUniformLocation("texture0");
        location_texture1 = super.getUniformLocation("texture1");

		super.loadInt(location_texture0, 1);
		super.loadInt(location_texture1, 2);
	}

	@Override
	protected void bindAttributies() {
		bind();
		
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		super.bindAttribute(ATTRIBUTE_LOC_TEXCOORDS, "texCoord");
	}
	
    public void loadProjectionMatrix(Matrix4f projectionMatrix) {
        super.loadMatrix(location_projectionMatrix, projectionMatrix);
    }
    
    public void loadModelViewMatrix(Matrix4f modelViewMatrix) {
        super.loadMatrix(location_modelViewMatrix, modelViewMatrix);
    }
}
