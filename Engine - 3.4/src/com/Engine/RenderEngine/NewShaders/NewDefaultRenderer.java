package com.Engine.RenderEngine.NewShaders;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Default.DefaultRenderProperties;
import com.Engine.RenderEngine.Textures.Texture2D;

public class NewDefaultRenderer extends NewRenderer<NewTestModel, DefaultRenderProperties, NewDefaultShader> {
	
	public NewDefaultRenderer(NewShader shader) {
		super(shader);
	}

	public void prepareOpenGL() {
		shader.bind();
		
		shader.loadFogValues(0.007f, 1.5f);
		shader.loadSkyColor(Shader.getSkyColor());
		
		shader.loadProjectionMatrix(Shader.getProjectionMatrix());
		shader.loadViewMatrix(Shader.getViewMatrix());
	}
	
	public void bindModel(NewTestModel model) {
		model.bind();
		model.getTexture().bind(0);
	}

	public void renderModel(NewTestModel model, DefaultRenderProperties property) {
		Texture2D texture = model.getTexture();
		
		shader.loadTransformationMatrix(property.getTransformMatrix());
		shader.loadShineVariables(property.getShineDamper(), property.getReflectivity());
		shader.loadTextureAtlasIndex(texture.getNumberOfRows(), 
				texture.getOffset(property.getTextureAtlasIndex()));

		glDrawElements(GL_TRIANGLES, model.getIndiceCount(), GL_UNSIGNED_INT, 0);
	}

	public void unbindModel(NewTestModel model) {
		Texture2D.unbind_2D(0);
		model.unbind();
	}
	
	public void revertOpenGL() {
		Shader.unbind();
	}
}
