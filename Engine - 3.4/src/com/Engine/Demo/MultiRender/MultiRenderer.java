package com.Engine.Demo.MultiRender;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.RenderEngine;
import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.MatrixUtil;

public class MultiRenderer extends Renderer<MultiModel, MultiRenderProperties> {

	public MultiRenderer(Shader shader) {
		super(shader);
	}

	public void renderModels() {
		for(MultiModel model : renders.keySet()) {
			MultiShader shader = (MultiShader) model.getShader();
			shader.bind();
			
			shader.loadProjectionMatrix(Shader.getProjectionMatrix());

			glBindVertexArray(model.getVAOId());
			glEnableVertexAttribArray(MultiShader.ATTRIBUTE_LOC_POSITIONS);
			glEnableVertexAttribArray(MultiShader.ATTRIBUTE_LOC_TEXCOORDS);
			
			for(MultiRenderProperties properties : renders.get(model)) {
				properties.getTexture0().bind(1);
				properties.getTexture1().bind(2);

				Matrix4f modelView = MatrixUtil.createModelViewMatrix(properties.getTransform(), Shader.getViewMatrix());
				shader.loadModelViewMatrix(modelView);
				
				glDrawElements(GL_TRIANGLES, model.getIndiceCount(), GL_UNSIGNED_INT, 0);
			}
			
			glDisableVertexAttribArray(MultiShader.ATTRIBUTE_LOC_POSITIONS);
			glDisableVertexAttribArray(MultiShader.ATTRIBUTE_LOC_TEXCOORDS);
			
			Texture2D.unbind_2D(0);
			Texture2D.unbind_2D(1);
			Shader.unbind();
		}
	}

	public boolean isAcceptedShader(Shader shader) { return shader == super.shader; }
	public int getRenderStage() { return RenderEngine.RENDER_STEP_PRIMARY; }
}
