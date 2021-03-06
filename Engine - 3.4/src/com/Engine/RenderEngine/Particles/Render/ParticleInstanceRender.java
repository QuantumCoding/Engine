package com.Engine.RenderEngine.Particles.Render;

import com.Engine.RenderEngine.Instancing.IRenderableInstance;
import com.Engine.RenderEngine.Instancing.InstanceVBO;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage;
import com.Engine.RenderEngine.System.RenderingSystem;
import com.Engine.RenderEngine.System.RenderingSystem.ModelInstance;
import com.Engine.Util.Vectors.Vector3f;

public class ParticleInstanceRender implements IRenderableInstance<ParticleRenderProperties> {

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
	
	private static final ModelData PARTICLE_MODEL_DATA; static {
		PARTICLE_MODEL_DATA = new ModelData(.5f, 1000, new Vector3f());
		
		PARTICLE_MODEL_DATA.storeDataInAttributeList(ParticleShader.ATTRIBUTE_LOC_POSITIONS, 2, VERTICES, BufferUsage.Static_Draw);
		PARTICLE_MODEL_DATA.storeDataInAttributeList(ParticleShader.ATTRIBUTE_LOC_TEXCOORDS, 2, TEX_COORDS, BufferUsage.Static_Draw);
		
		PARTICLE_MODEL_DATA.loadIndices(INDICES);
	}
	
	public static final int MAX_PARTICLE_COUNT = 5000;
	public static final int INSTANCE_DATA_LENGTH = 22;
	
	private RenderingSystem<ParticleInstanceRender, ParticleRenderProperties> shader;
	
	public ParticleInstanceRender(RenderingSystem<ParticleInstanceRender, ParticleRenderProperties> shader) {
		this.shader = shader;
	}
	
	public void addInstanceAttributes(InstanceVBO vbo) {
		int vao = PARTICLE_MODEL_DATA.getVAOId();
		
		vbo.nextAttribute(vao, ParticleShader.ATTRIBUTE_LOC_OFFSET_1, 2, 1);
		vbo.nextAttribute(vao, ParticleShader.ATTRIBUTE_LOC_OFFSET_2, 2, 1);

		vbo.nextAttribute(vao, ParticleShader.ATTRIBUTE_LOC_DIVISOR, 1, 1);
		vbo.nextAttribute(vao, ParticleShader.ATTRIBUTE_LOC_BLEND, 1, 1);

		vbo.nextAttribute(vao, ParticleShader.ATTRIBUTE_LOC_MODEL_VIEW, 4, 1);
	}
	
	public ModelInstance<ParticleInstanceRender, ParticleRenderProperties> render(ParticleRenderProperties properties) {
		return shader.addModel(this, properties);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shader == null) ? 0 : shader.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof ParticleInstanceRender)) return false;
		
		ParticleInstanceRender other = (ParticleInstanceRender) obj;
		if(shader == null) {
			if(other.shader != null) return false;
		} else if(!shader.equals(other.shader)) return false;
		
		return true;
	}

	public RenderingSystem<ParticleInstanceRender, ParticleRenderProperties> getShader() { return shader; }
	public ModelData getModelData() { return PARTICLE_MODEL_DATA; }
	
	public int getInstanceLength() { return INSTANCE_DATA_LENGTH; }
	public int getInstanceCount() { return MAX_PARTICLE_COUNT; }
}
