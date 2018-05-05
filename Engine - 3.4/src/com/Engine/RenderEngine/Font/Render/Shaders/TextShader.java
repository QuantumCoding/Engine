package com.Engine.RenderEngine.Font.Render.Shaders;

import com.Engine.RenderEngine.Font.Render.TextMesh;
import com.Engine.RenderEngine.Font.Render.TextRenderProperties;
import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;

public abstract class TextShader extends Shader {
	public TextShader(String vsFilePath, String fsFilePath, Class<? extends Renderer<?, ?, ?>> rendererClass) {
		super(vsFilePath, fsFilePath, rendererClass);
	}
	
	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		super.bindAttribute(ATTRIBUTE_LOC_TEXCOORDS, "texCoord_in");
	}

	public abstract void prepOpenGL();
	public abstract void prep(TextMesh mesh, TextRenderProperties properties);
}
