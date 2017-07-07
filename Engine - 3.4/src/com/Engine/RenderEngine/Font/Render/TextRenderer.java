package com.Engine.RenderEngine.Font.Render;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import com.Engine.RenderEngine.RenderEngine;
import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.MatrixUtil;

public class TextRenderer extends Renderer<TextMesh, TextRenderProperties> {

	public TextRenderer(Shader shader) {
		super(shader);
		usingFrustumCulling(false);
	}

	public void renderModels() {
		for(TextMesh mesh : renders.keySet()) {
			TextShader shader = mesh.getShader();
			shader.bind();
			
			shader.loadProjectionMatrix(Shader.getProjectionMatrix());
			mesh.getFont().getTexture().bind(0);
			glBindVertexArray(mesh.getVAO());
			
			glEnableVertexAttribArray(TextShader.ATTRIBUTE_LOC_POSITIONS);
			glEnableVertexAttribArray(TextShader.ATTRIBUTE_LOC_TEXCOORDS);
			
			for(TextRenderProperties properties : renders.get(mesh)) {
				shader.loadColour(properties.getColour());
				shader.loadModelViewMatrix(MatrixUtil.createModelViewMatrix(properties.getTransform(), Shader.getViewMatrix()));
//				shader.loadModelViewMatrix(Matrix4f.mul(properties.getTransformMatrix(), Shader.getViewMatrix(), null));
				
				glDrawElements(GL_TRIANGLES, mesh.getIndiceCount(), GL_UNSIGNED_INT, 0);
			}

			glDisableVertexAttribArray(TextShader.ATTRIBUTE_LOC_POSITIONS);
			glDisableVertexAttribArray(TextShader.ATTRIBUTE_LOC_TEXCOORDS);
		}
	}

	public int getRenderStage() { return RenderEngine.RENDER_STEP_PRIMARY; }
}
