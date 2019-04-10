package com.Engine.Demo.Skybox;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.GLFunctions.DepthTest;
import com.Engine.RenderEngine.Shaders.Render.Renderer;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Textures.TextureCubeMap;
import com.Engine.Util.Vectors.Vector3f;

public class SkyboxRenderer extends Renderer<Skybox, SkyboxRenderProperties, SkyboxShader> {
	public SkyboxRenderer(Shader shader) {
		super(shader);
		usingFrustumCulling(false);
	}
	
	private static final org.lwjgl.util.vector.Vector3f SKYBOX_SCALE = new Vector3f(500).toLWJGL();
	private static final DepthTest DEPTH_CHECK = DepthTest.checkOnly();
	
	private DepthTest oldDepthText;
	
	protected void prepareOpenGL() {
		oldDepthText = DepthTest.current();
		DEPTH_CHECK.push();
		
		shader.bind();
		shader.projectionMatrix.load(Shader.getProjectionMatrix());
		
		Matrix4f view = Matrix4f.scale(SKYBOX_SCALE, Shader.getViewMatrix(), null);
		view.m30 = view.m31 = view.m32 = 0;
		shader.viewMatrix.load(view);
	}

	protected void bindModel(Skybox model) {
		model.bind();
		shader.skyTexture.load(model.getTexture());
	}

	protected void renderModel(Skybox model, SkyboxRenderProperties properties) {
//		glDrawElements(GL_TRIANGLES, model.getIndiceCount(), GL_UNSIGNED_INT, 0);
		glDrawArrays(GL_TRIANGLES, 0, 36);
	}

	protected void unbindModel(Skybox model) {
		model.unbind();
	}

	protected void revertOpenGL() {
		TextureCubeMap.unbind_Cube(0);
		Shader.unbind();
		
		oldDepthText.push();
	}
}
