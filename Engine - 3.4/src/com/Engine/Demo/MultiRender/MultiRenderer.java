package com.Engine.Demo.MultiRender;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.MatrixUtil;

public class MultiRenderer extends Renderer<MultiModel, MultiRenderProperties, MultiShader> {

	public MultiRenderer(Shader shader) {
		super(shader);
	}

	public void prepareOpenGL() {
		shader.bind();
		shader.projectionMatrix.load(Shader.getProjectionMatrix());
	}
	
	public void bindModel(MultiModel model) {
		model.bind();
	}

	public void renderModel(MultiModel model, MultiRenderProperties property) {
		property.getTexture0().bind(0);
		property.getTexture1().bind(1);

//		Matrix4f modelView = MatrixUtil.createModelViewMatrix(property.getTransform(), Shader.getViewMatrix());
		Matrix4f modelView = Matrix4f.mul(Shader.getViewMatrix(), property.getTransformMatrix(), null);
		shader.modelViewMatrix.load(modelView);
		
		glDrawElements(GL_TRIANGLES, model.getIndiceCount(), GL_UNSIGNED_INT, 0);
	}

	public void unbindModel(MultiModel model) {
		Texture2D.unbind_2D(0);
		Texture2D.unbind_2D(1);
		model.unbind();
	}
	
	public void revertOpenGL() {
		Shader.unbind();
	}
}
