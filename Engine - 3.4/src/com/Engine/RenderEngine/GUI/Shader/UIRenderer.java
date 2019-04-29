package com.Engine.RenderEngine.GUI.Shader;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glDrawArrays;

import com.Engine.RenderEngine.GUI.Components.SubComponents.QuadComponent;
import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;

public class UIRenderer extends Renderer<UIGraphics, QuadComponent, UIShader> {
	public UIRenderer(Shader shader) {
		super(shader);
		usingFrustumCulling(false);
	}

	public void prepareOpenGL() {
		shader.bind();
		shader.othographicMatrix.load(Shader.getOrthographicMatrix());
	}
	
	public void bindModel(UIGraphics model) {
		model.bind();
	}

	public void renderModel(UIGraphics model, QuadComponent properties) {
		shader.transformationMatrix.load(properties.getTransformMatrix());
		shader.textureMatrix.load(properties.getTextureMatrix());
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 8);
	}

	public void unbindModel(UIGraphics model) {
		model.unbind();
	}
	
	public void revertOpenGL() {
		Shader.unbind();
	}
}
