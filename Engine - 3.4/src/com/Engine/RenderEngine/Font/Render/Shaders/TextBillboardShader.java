package com.Engine.RenderEngine.Font.Render.Shaders;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Font.Render.TextMesh;
import com.Engine.RenderEngine.Font.Render.TextRenderProperties;
import com.Engine.RenderEngine.Font.Render.TextRenderer;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformTexture;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformVec4;
import com.Engine.Util.Vectors.MatrixUtil;

public class TextBillboardShader extends TextShader {
	private static final String VERTEX_SHADER_LOC = "TextBillboardShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "TextShader.fsh";
	
	@Uniform UniformMat4 projectionMatrix;
	@Uniform UniformMat4 modelViewMatrix;

	@Uniform UniformVec4 colour;
	@Uniform UniformTexture texture0;
	
	public TextBillboardShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, TextRenderer.class);
	}
	
	public void prepOpenGL() {
		bind();
		projectionMatrix.load(getProjectionMatrix());
	}

	public void prep(TextMesh mesh, TextRenderProperties property) {
		Matrix4f modelView = MatrixUtil.createModelViewMatrix(property.getTransform(), Shader.getViewMatrix());
		colour.load(property.getColour());
		modelViewMatrix.load(modelView);
	}
}
