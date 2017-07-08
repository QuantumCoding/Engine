package com.Engine.RenderEngine.Models.ModelData;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.BufferUtils;

import com.Engine.Util.Vectors.Vector3f;

public class ModelData {
	protected int vaoId;
	protected int indiciesId;
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
	}
	
	public void storeDataInAttributeList(Attribute attribute, int size, float[] data, boolean dynamic) {
		VBO vbo = attributes.get(attribute); 
		if(vbo == null) attributes.put(attribute, vbo = new VBO());
		vbo.hold(true);
		
		_bind();
			vbo.put(dynamic, data);	
			glVertexAttribPointer(attribute.getId(), size, GL_FLOAT, false, 0, 0);
			vbo.unbind();
		_unbind();
	}
	
	public void loadIndicies(int[] indicies) {
		int vboID = indiciesId;
		if(vboID == 0) vboID = glGenBuffers();
		
		IntBuffer buffer = BufferUtils.createIntBuffer(indicies.length);
		buffer.put(indicies); buffer.flip();

		glBindVertexArray(vaoId);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		
		indiceCount = indicies.length;
		indiciesId = vboID;
	}
	
	private void _bind() { glBindVertexArray(vaoId); }
	private void _unbind() { glBindVertexArray(0); }
	
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
	
	public float getRadius() { return renderRadius; }
	public float getRenderDistance() { return renderDistance; }
	public Vector3f getCenter() { return modelCenter; }
	
	public int getVAOId() { return vaoId; }
	public int getIndiceCount() { return indiceCount; }
	
	public void cleanUp() {
		glDeleteVertexArrays(vaoId);
		glDeleteBuffers(indiciesId);
		
		for(Entry<Attribute, VBO> entry : attributes.entrySet()) {
			entry.getValue().cleanUp();
		}
	}
}
