package com.Engine.PhysicsEngine.Render;

import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformVec3;

public class PhysicsShader extends Shader {
	private static final String VERTEX_SHADER_LOC = "PhysicsSphereShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "PhysicsSphereShader.fsh";
	
	@Uniform UniformVec3 color;
	
	@Uniform UniformMat4 transformationMatrix;
	@Uniform UniformMat4 projectionMatrix;
	@Uniform UniformMat4 viewMatrix;
	
	public PhysicsShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, PhysicsRenderer.class);
	}

	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
	}
}
