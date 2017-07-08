package com.Engine.PhysicsEngine.Render;

import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glPolygonMode;

import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Default.Model;

public class PhysicsRenderer extends Renderer<Model, PhysicsRenderProperties, PhysicsShader> {
	public PhysicsRenderer(Shader shader) {
		super(shader);
		usingFrustumCulling(false);
	}

	public void prepareOpenGL() {
		shader.bind();
		shader.projectionMatrix.load(Shader.getProjectionMatrix());
		shader.viewMatrix.load(Shader.getViewMatrix());
	}
	
	public void bindModel(Model model) {
		model.bind();
	}

	public void renderModel(Model model, PhysicsRenderProperties property) {
		shader.transformationMatrix.load(property.getTransformMatrix());
		shader.color.load(property.getColor());

		if(!property.isSolid()) glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

		if(property.useQuadRender())
			glDrawElements(GL_LINES, model.getIndiceCount(), GL_UNSIGNED_INT, 0);
		else
			glDrawElements(GL_TRIANGLES, model.getIndiceCount(), GL_UNSIGNED_INT, 0);
		
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}

	public void unbindModel(Model model) {
		model.unbind();
	}
	
	public void revertOpenGL() {
		Shader.unbind();
	}
}
