package com.Engine.RenderEngine.Particles.Render;

import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

import java.util.Iterator;

import com.Engine.RenderEngine.Instancing.InstanceRenderer;
import com.Engine.RenderEngine.Instancing.InstanceVBO;
import com.Engine.RenderEngine.Particles.Texture.ParticleTexture;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.MatrixUtil;

public class ParticleInstanceRenderer extends InstanceRenderer<ParticleInstanceRender, ParticleRenderProperties, ParticleShader> {
	public ParticleInstanceRenderer(Shader shader) {
		super(shader);
		usingFrustumCulling(false);
	}
	
	protected void prepareOpenGL() {
		shader.bind();
		shader.projectionMatrix.load(Shader.getProjectionMatrix());
		glDepthMask(false);
	}
	
	public void prepInstanceVBO(InstanceVBO vbo, ParticleInstanceRender model) {
		for(Iterator<ParticleRenderProperties> iter = renders.get(model).iterator(); iter.hasNext();) {
			ParticleRenderProperties property = iter.next();
			
			if(property.usingAdditive()) glBlendFunc(GL_SRC_ALPHA, GL_ONE);
			else glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
			vbo.putAll(
					property.getOffset1(), property.getOffset2(),
					property.getTextureDivisor(), property.getBlend(),
					MatrixUtil.createModelViewMatrix(property.getTransform(), Shader.getViewMatrix())
				);
		}
	}
	
	public void bindModel(ParticleInstanceRender model) {
		model.bind(); 

		ParticleShader.ATTRIBUTE_LOC_OFFSET_1.enable();
		ParticleShader.ATTRIBUTE_LOC_OFFSET_2.enable();
		ParticleShader.ATTRIBUTE_LOC_BLEND.enable();
		ParticleShader.ATTRIBUTE_LOC_DIVISOR.enable();
		ParticleShader.ATTRIBUTE_LOC_MODEL_VIEW.enable();
	}

	public void renderInstance(InstanceVBO vbo, ParticleInstanceRender model) {
		ParticleTexture.getRegistry().getTextureMap().bind(0);
		glDrawElementsInstanced(GL_TRIANGLES, model.getIndiceCount(), GL_UNSIGNED_INT, 0, vbo.getRenderCount());
	}
	
	public void unbindModel(ParticleInstanceRender model) { 
		ParticleShader.ATTRIBUTE_LOC_OFFSET_1.disable();
		ParticleShader.ATTRIBUTE_LOC_OFFSET_2.disable();
		ParticleShader.ATTRIBUTE_LOC_BLEND.disable();
		ParticleShader.ATTRIBUTE_LOC_DIVISOR.disable();
		ParticleShader.ATTRIBUTE_LOC_MODEL_VIEW.disable();
		
		model.unbind(); 
	}
	
	protected void revertOpenGL() {
		Texture2D.unbind_2D(0);
		
		glDepthMask(true);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Shader.unbind();
	}
}
