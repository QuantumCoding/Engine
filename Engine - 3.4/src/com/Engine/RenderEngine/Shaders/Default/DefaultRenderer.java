package com.Engine.RenderEngine.Shaders.Default;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import com.Engine.RenderEngine.RenderEngine;
import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Textures.Texture2D;

public class DefaultRenderer extends Renderer<Model, DefaultRenderProperties> {
	
	public DefaultRenderer(Shader shader) {
		super(shader);
	}

	public void renderModels() {
		for(Model model : renders.keySet()) {
			
				
			DefaultShader shader = (DefaultShader) model.getShader();
			shader.bind();
			
			shader.loadFogValues(0.007f, 1.5f);
			shader.loadSkyColor(Shader.getSkyColor());
			
			shader.loadProjectionMatrix(Shader.getProjectionMatrix());
			shader.loadViewMatrix(Shader.getViewMatrix());
			
			Texture2D texture = model.getTexture();
			texture.bind(0);
			
			glBindVertexArray(model.getVAOId());
			for(int i = 0; i < model.getLargestAttribute()+1; i++)
				glEnableVertexAttribArray(i);
			
			for(DefaultRenderProperties property : renders.get(model)) {
				
				shader.loadTransformationMatrix(property.getTransformMatrix());
				shader.loadShineVariables(property.getShineDamper(), property.getReflectivity());
				shader.loadTextureAtlasIndex(texture.getNumberOfRows(), 
						texture.getOffset(property.getTextureAtlasIndex()));
				
				
				glDrawElements(GL_TRIANGLES, model.getIndiceCount(), GL_UNSIGNED_INT, 0);
				
			}

			for(int i = 0; i < model.getLargestAttribute()+1; i++)
				glDisableVertexAttribArray(i);
			
			Shader.unbind();
		}
	}

	public boolean isAcceptedShader(Shader shader) {
		return shader == this.shader;
	}
	
	public int getRenderStage() {
		return RenderEngine.RENDER_STEP_PRIMARY;
	}
}
