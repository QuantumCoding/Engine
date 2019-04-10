package com.Engine.RenderEngine.Particles.Render;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Models.ModelData.Attribute;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformTexture;

public class ParticleShader extends Shader {
	private static final String VERTEX_SHADER_LOC = "ParticleShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "ParticleShader.fsh";

	public static final Attribute ATTRIBUTE_LOC_OFFSET_1 = nextAttribId(ParticleShader.class);
	public static final Attribute ATTRIBUTE_LOC_OFFSET_2 = nextAttribId(ParticleShader.class);

	public static final Attribute ATTRIBUTE_LOC_DIVISOR = nextAttribId(ParticleShader.class);
	public static final Attribute ATTRIBUTE_LOC_BLEND = nextAttribId(ParticleShader.class);
	
	public static final Attribute ATTRIBUTE_LOC_MODEL_VIEW = nextAttribId(ParticleShader.class, 4);
	
	@Uniform UniformMat4 projectionMatrix;
	@Uniform UniformTexture texture0;
	
	public ParticleShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, ParticleInstanceRenderer.class);
	}

	@Override
	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		super.bindAttribute(ATTRIBUTE_LOC_TEXCOORDS, "texCoord");

		super.bindAttribute(ATTRIBUTE_LOC_OFFSET_1, "texOffset1");
		super.bindAttribute(ATTRIBUTE_LOC_OFFSET_2, "texOffset2");

		super.bindAttribute(ATTRIBUTE_LOC_DIVISOR, "textureDivisor");
		super.bindAttribute(ATTRIBUTE_LOC_BLEND, "blendFactor");

		super.bindAttribute(ATTRIBUTE_LOC_MODEL_VIEW, "modelViewMatrix");
	}
	
	public void loadProjectionMatrix(Matrix4f mat) { 
		projectionMatrix.load(mat); 
	}
}
