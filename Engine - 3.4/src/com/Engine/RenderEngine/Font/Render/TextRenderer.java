package com.Engine.RenderEngine.Font.Render;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import com.Engine.RenderEngine.Font.Render.Shaders.TextShader;
import com.Engine.RenderEngine.GLFunctions.DepthTest;
import com.Engine.RenderEngine.Shaders.Render.Renderer;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Textures.Texture2D;

public class TextRenderer extends Renderer<TextMesh, TextRenderProperties, TextShader> {
	private static final DepthTest DEPTH_TEST = DepthTest.checkOnly();
	
	public TextRenderer(Shader shader) {
		super(shader);
		usingFrustumCulling(false);
	}
	
	DepthTest oldDepthTest;
	
	public void prepareOpenGL() {
//		shader.bind();
//		shader.projectionMatrix.load(Shader.getProjectionMatrix());
		shader.prepOpenGL();
		
		oldDepthTest = DepthTest.current();
		DEPTH_TEST.push();
	}
	
	public void bindModel(TextMesh mesh) {
		mesh.bind();
		mesh.getFont().getTexture().bind(0);
	}

	public void renderModel(TextMesh mesh, TextRenderProperties property) {
		shader.prep(mesh, property);
		glDrawElements(GL_TRIANGLES, mesh.getIndiceCount(), GL_UNSIGNED_INT, 0);
	}

	public void unbindModel(TextMesh mesh) {
		Texture2D.unbind_2D(0);
		mesh.unbind();
	}
	
	public void revertOpenGL() {
		Shader.unbind();
		oldDepthTest.push();
	}
}
