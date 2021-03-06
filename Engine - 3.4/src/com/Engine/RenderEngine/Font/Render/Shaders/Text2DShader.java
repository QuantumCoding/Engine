package com.Engine.RenderEngine.Font.Render.Shaders;

import com.Engine.RenderEngine.Font.Render.TextMesh;
import com.Engine.RenderEngine.Font.Render.TextRenderProperties;
import com.Engine.RenderEngine.Font.Render.TextRenderer;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformFloat;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformTexture;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformVec4;

public class Text2DShader extends TextShader {
	private static final String VERTEX_SHADER_LOC = "Text2DShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "TextShader.fsh";
	
	@Uniform UniformMat4 orthographicMatrix;
	@Uniform UniformMat4 transformationMatrix;

	@Uniform UniformFloat lineHeight;

	@Uniform UniformVec4 colour;
	@Uniform UniformTexture texture0;
	
	public Text2DShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, TextRenderer.class);
	}

	public void prepOpenGL() {
		bind();
		orthographicMatrix.load(getOrthographicMatrix());
	}
	
	public void prep(TextMesh mesh, TextRenderProperties property) {
		transformationMatrix.load(property.getTransformMatrix());
		lineHeight.load(mesh.getLineHeight());
		colour.load(property.getColour());
	}
}
