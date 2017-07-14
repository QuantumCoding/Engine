package com.Engine.Demo.OrthoTesting;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;

public class OrthoRenderer extends Renderer<OrthoModel, OrthoRenderProperties, OthoShader> {

	public OrthoRenderer(Shader shader) {
		super(shader);
		usingFrustumCulling(false);
	}

	public void prepareOpenGL() {
		shader.bind();
		shader.othographicMatrix.load(Shader.getOrthographicMatrix());
	}
	
	public void bindModel(OrthoModel model) {
		model.bind();
	}

	public void renderModel(OrthoModel model, OrthoRenderProperties properties) {
		shader.transformationMatrix.load(properties.getTransformMatrix());
		glDrawElements(GL_TRIANGLES, model.getIndiceCount(), GL_UNSIGNED_INT, 0);
	}

	public void unbindModel(OrthoModel model) {
		model.unbind();
	}
	
	public void revertOpenGL() {
		Shader.unbind();
	}
}
