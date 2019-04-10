package com.Engine.Demo.Skybox;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage;
import com.Engine.RenderEngine.Shaders.Render.IRenderable;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.System.RenderingSystem;
import com.Engine.RenderEngine.System.RenderingSystem.ModelInstance;
import com.Engine.RenderEngine.Textures.TextureCubeMap;

public class Skybox implements IRenderable<SkyboxRenderProperties> {
	private static final ModelData CUBE_MODEL = new ModelData(); static {
//		CUBE_MODEL.storeDataInAttributeList(Shader.ATTRIBUTE_LOC_POSITIONS, 3, new float[] {
//			-1, -1, -1,		+1, -1, -1,		
//			-1, +1, -1,		+1, +1, -1,		-1, -1, +1,		+1, -1, +1,
//											-1, +1, +1,		+1, +1, +1,
//		}, BufferUsage.Static_Draw);
//		
//		CUBE_MODEL.loadIndices(new int[] {
//				0, 1, 2,	2, 1, 3,		// Front
//				5, 4, 6,	5, 6, 7,		// Back
//				               
//				2, 6, 4,	2, 4, 0,		// Left
//				7, 3, 5,	5, 3, 1,		// Right
//                               
//				7, 6, 2,	7, 2, 3,		// Top
//				4, 5, 0,	0, 5, 1,		// Bottom
//		});
		
		CUBE_MODEL.storeDataInAttributeList(Shader.ATTRIBUTE_LOC_POSITIONS, 3, new float[] {
			-1,  1, -1,
			-1, -1, -1,
			 1, -1, -1,
			 1, -1, -1,
			 1,  1, -1,
			-1,  1, -1,
			
			-1, -1,  1,
			-1, -1, -1,
			-1,  1, -1,
			-1,  1, -1,
			-1,  1,  1,
			-1, -1,  1,
			
			 1, -1, -1,
			 1, -1,  1,
			 1,  1,  1,
			 1,  1,  1,
			 1,  1, -1,
			 1, -1, -1,
			
			-1, -1,  1,
			-1,  1,  1,
			 1,  1,  1,
			 1,  1,  1,
			 1, -1,  1,
			-1, -1,  1,
			
			-1,  1, -1,
			 1,  1, -1,
			 1,  1,  1,
			 1,  1,  1,
			-1,  1,  1,
			-1,  1, -1,
			
			-1, -1, -1,
			-1, -1,  1,
			 1, -1, -1,
			 1, -1, -1,
			-1, -1,  1,
			 1, -1,  1
		}, BufferUsage.Static_Draw);
	}
	
	private RenderingSystem<Skybox, SkyboxRenderProperties> shader;
	private TextureCubeMap cubeMap;
	
	public ModelInstance<Skybox, SkyboxRenderProperties> render(SkyboxRenderProperties properties) {
		return shader.addModel(this, properties);
	}

	public void setShader(RenderingSystem<Skybox, SkyboxRenderProperties> shader) { this.shader = shader; }
	public void setTexture(TextureCubeMap cubeMap) { this.cubeMap = cubeMap; }
	
	public TextureCubeMap getTexture() { return cubeMap; }
	
	public RenderingSystem<Skybox, SkyboxRenderProperties> getShader() { return shader; }
	public ModelData getModelData() { return CUBE_MODEL; }
}
