package com.Engine.RenderEngine.Particles.Render;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Shaders.Shader;

public class ParticleShader extends Shader {
	private static final String VERTEX_SHADER_LOC = "ParticleShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "ParticleShader.fsh";

	public static final int ATTRIBUTE_LOC_OFFSET_1 = nextAttribId(ParticleShader.class);
	public static final int ATTRIBUTE_LOC_OFFSET_2 = nextAttribId(ParticleShader.class);

	public static final int ATTRIBUTE_LOC_DIVISOR = nextAttribId(ParticleShader.class);
	public static final int ATTRIBUTE_LOC_BLEND = nextAttribId(ParticleShader.class);
	
	public static final int ATTRIBUTE_LOC_MODEL_VIEW = nextAttribId(ParticleShader.class, 4);
	
    private int location_projectionMatrix;
    private int location_texture0;
	
	public ParticleShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, ParticleInstanceRenderer.class);
	}

	@Override
	protected void initUniformLocations() {
		bind();
		
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_texture0 = super.getUniformLocation("texture0");
	}

	@Override
	protected void bindAttributies() {
		bind();
		
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		super.bindAttribute(ATTRIBUTE_LOC_TEXCOORDS, "texCoord");

		super.bindAttribute(ATTRIBUTE_LOC_OFFSET_1, "texOffset1");
		super.bindAttribute(ATTRIBUTE_LOC_OFFSET_2, "texOffset2");

		super.bindAttribute(ATTRIBUTE_LOC_DIVISOR, "textureDivisor");
		super.bindAttribute(ATTRIBUTE_LOC_BLEND, "blendFactor");

		super.bindAttribute(ATTRIBUTE_LOC_MODEL_VIEW, "modelViewMatrix");
		
		super.loadInt(location_texture0, 0);
	}
	
    public void loadProjectionMatrix(Matrix4f projectionMatrix) {
        super.loadMatrix(location_projectionMatrix, projectionMatrix);
    }
}
