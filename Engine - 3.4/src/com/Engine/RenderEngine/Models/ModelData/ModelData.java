package com.Engine.RenderEngine.Models.ModelData;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.HashMap;
import java.util.Map.Entry;

import com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage;
import com.Engine.RenderEngine.Models.ModelData.VBOs.IndexVBO;
import com.Engine.Util.Vectors.Vector3f;

public class ModelData {
	protected int vaoId;
	protected IndexVBO indexVBO;
	protected HashMap<Attribute, VBO> attributes;

	private int indiceCount;
	
	private float renderRadius;
	private float renderDistance;
	private Vector3f modelCenter;

	public ModelData() { this(0, 0, new Vector3f()); }
	public ModelData(float renderRadius, float renderDistance, Vector3f modelCenter) {
		this.renderRadius = renderRadius;
		this.renderDistance = renderDistance;
		this.modelCenter = modelCenter;
		
		vaoId = glGenVertexArrays();
		attributes = new HashMap<>();
		
		indiceCount = -1;
	}
	
	public void storeDataInAttributeList(Attribute attribute, int size, float[] data, BufferUsage usage) {
		VBO vbo = attributes.get(attribute); 
		if(vbo == null) attributes.put(attribute, vbo = new VBO());
		vbo.hold(true);
		
		vbo.put(usage, data);	
		attachAttribute(attribute, vbo, size, 0, 0);
	}
	
	public void attachAttribute(Attribute attribute, VBO vbo, int dataSize, int offset, int packFloatCount) {
		attributes.put(attribute, vbo);
		
		_bind();
			vbo.bind();
			glVertexAttribPointer(attribute.getId(), dataSize, GL_FLOAT, false, packFloatCount * 4, offset * 4);
			vbo.unbind();
		_unbind();
	}
	
	public void loadIndices(int[] indices) {
		if(indexVBO == null) indexVBO = new IndexVBO();

		_bind();
			indexVBO.hold(true);
			indexVBO.put(indices);
		_unbind();
	}
	
	public void loadIndices(IndexVBO vbo) {
		this.indexVBO = vbo;

		_bind();
			vbo.bind();
		_unbind();
	}
	
	public IndexVBO getIndexVBO() { return indexVBO; }
	public void setIndiceCount(int count) { this.indiceCount = count; }
	
	protected void _bind() { glBindVertexArray(vaoId); }
	protected void _unbind() { glBindVertexArray(0); }
	
	public void bind() {
		_bind();
		
		for(Attribute attribute : attributes.keySet())
			attribute.enable();
	}
	
	public void unbind() {
		for(Attribute attribute : attributes.keySet())
			attribute.disable();
		
		_unbind();
	}
	
	public VBO getAttributeVBO(Attribute attribute) { return attributes.get(attribute); }
	
	public float getRadius() { return renderRadius; }
	public float getRenderDistance() { return renderDistance; }
	public Vector3f getCenter() { return modelCenter; }
	
	public int getVAOId() { return vaoId; }
	public int getIndiceCount() { return indiceCount == -1 ? indexVBO.getSize() : indiceCount; }
	
	public void cleanUp() {
		glDeleteVertexArrays(vaoId);
		indexVBO.cleanUp();
		
		for(Entry<Attribute, VBO> entry : attributes.entrySet()) {
			entry.getValue().cleanUp();
		}
	}
}
