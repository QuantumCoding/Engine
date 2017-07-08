package com.Engine.RenderEngine.Font.Render;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.MatrixUtil;

public class TextRenderer extends Renderer<TextMesh, TextRenderProperties, TextShader> {
	public TextRenderer(Shader shader) {
		super(shader);
		usingFrustumCulling(false);
	}
	
	public void prepareOpenGL() {
		shader.bind();
		shader.projectionMatrix.load(Shader.getProjectionMatrix());
	}
	
	public void bindModel(TextMesh mesh) {
		mesh.bind();
		mesh.getFont().getTexture().bind(0);
	}

	public void renderModel(TextMesh mesh, TextRenderProperties property) {
		Matrix4f modelView = MatrixUtil.createModelViewMatrix(property.getTransform(), Shader.getViewMatrix());
		shader.colour.load(property.getColour());
		shader.modelViewMatrix.load(modelView);
		
		glDrawElements(GL_TRIANGLES, mesh.getIndiceCount(), GL_UNSIGNED_INT, 0);
	}

	public void unbindModel(TextMesh mesh) {
		Texture2D.unbind_2D(0);
		mesh.unbind();
	}
	
	public void revertOpenGL() {
		Shader.unbind();
	}
}
