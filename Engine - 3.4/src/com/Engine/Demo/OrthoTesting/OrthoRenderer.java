package com.Engine.Demo.OrthoTesting;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glDrawArrays;

import com.Engine.RenderEngine.Shaders.Render.Renderer;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Textures.Texture2D;

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
		shader.texture0.load(model.getTexture());
	}

	public void renderModel(OrthoModel model, OrthoRenderProperties properties) {
		shader.transformationMatrix.load(properties.getTransformMatrix());
//		glDrawElements(GL_TRIANGLES, model.getIndiceCount(), GL_UNSIGNED_INT, 0);
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
	}

	public void unbindModel(OrthoModel model) {
		Texture2D.unbind_2D(0);
		model.unbind();
	}
	
	public void revertOpenGL() {
		Shader.unbind();
	}
}
