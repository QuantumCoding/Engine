package com.Engine.Demo.MultiRender;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Particles.Render.ParticleShader;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.Util.Vectors.Vector3f;

public class MultiModel extends Model {
	private static final float[] VERTICES = {
		-0.5f,  0.5f,	-0.5f, -0.5f,
		 0.5f, -0.5f,	 0.5f,  0.5f,
	};
	
	private static final float[] TEX_COORDS = {
		0.0f, 0.0f,		0.0f, 1.0f,
		1.0f, 1.0f,		1.0f, 0.0f,
	};
	
	private static final int[] INDICES = {
		0, 1, 2,	0, 2, 3 
	};
	
	private static final ModelData MULTI_MODEL_DATA; static {
		MULTI_MODEL_DATA = new ModelData(1, 1000, new Vector3f());
		
		MULTI_MODEL_DATA.storeDataInAttributeList(ParticleShader.ATTRIBUTE_LOC_POSITIONS, 2, VERTICES, false);
		MULTI_MODEL_DATA.storeDataInAttributeList(ParticleShader.ATTRIBUTE_LOC_TEXCOORDS, 2, TEX_COORDS, false);
		
		MULTI_MODEL_DATA.loadIndicies(INDICES);
	}
	
	public MultiModel() {
		super(MULTI_MODEL_DATA);
	}
}
