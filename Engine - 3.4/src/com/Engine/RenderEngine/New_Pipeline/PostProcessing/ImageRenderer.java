package com.Engine.RenderEngine.New_Pipeline.PostProcessing;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Textures.Texture;

public class ImageRenderer {
	private static final float[] VERTICIES = { 
		-1,1,	-1,-1,	
		 1,1,	 1,-1
	};
	
	private static final ModelData MODEL = new ModelData();
	static { MODEL.storeDataInAttributeList(Shader.ATTRIBUTE_LOC_POSITIONS, 2, VERTICIES, BufferUsage.Static_Draw); }
	
	public static void prepOpenGL() {
		glDisable(GL_DEPTH_TEST);
		MODEL.bind();
	}
	
	public static void revertOpenGL() {
		MODEL.unbind();
		glEnable(GL_DEPTH_TEST);
	}
	
	public static void drawImage(Texture texture) { drawImage(texture, false); }
	public static void drawImage(Texture texture, boolean prebound) {
		if(!prebound) texture.bind(0);
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
	}
	
	public static void cleanUp() {
		MODEL.cleanUp();
	}
}
