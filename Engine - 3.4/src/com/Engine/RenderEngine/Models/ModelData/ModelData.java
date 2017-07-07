package com.Engine.RenderEngine.Models.ModelData;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
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

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.Engine.Util.Vectors.Vector3f;

public class ModelData {
	protected static final int MAX_ATTRIBUTIES = 16;
	
	protected int vaoId;
	protected int[] attribIds;
	protected int indiciesId;
	
	private int indiceCount;
	private int largestAttribute;
	
	private float renderRadius;
	private float renderDistance;
	private Vector3f modelCenter;

//	protected ModelData() {}
	
	public ModelData() { this(0, 0, new Vector3f()); }
	public ModelData(float renderRadius, float renderDistance, Vector3f modelCenter) {
		this.renderRadius = renderRadius;
		this.renderDistance = renderDistance;
		this.modelCenter = modelCenter;
		
		vaoId = glGenVertexArrays();
		attribIds = new int[MAX_ATTRIBUTIES];
	}
	
	public void storeDataInAttributeList(int attributeNumber, int size, float[] data, boolean dynamic) {
		int vboID = attribIds[attributeNumber];
		if(vboID == 0) vboID = glGenBuffers();
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data); buffer.flip();
		
		glBindVertexArray(vaoId);
		
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer, dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);		
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
		
		attribIds[attributeNumber] = vboID;
		largestAttribute = Math.max(largestAttribute, attributeNumber);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
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
	
	public float getRadius() { return renderRadius; }
	public float getRenderDistance() { return renderDistance; }
	public Vector3f getCenter() { return modelCenter; }
	
	public int getVAOId() { return vaoId; }
	public int[] getAttribIds() { return attribIds; }
	public int getIndiceCount() { return indiceCount; }
	public int getLargestAttribute() { return largestAttribute; }
	
	public void setLargestAttribute(int largestAttribute) {
		this.largestAttribute = largestAttribute;
	}
	
	public void cleanUp() {
		glDeleteVertexArrays(vaoId);
		glDeleteBuffers(indiciesId);
		
		for(int vboId : attribIds) {
			if(vboId != 0) glDeleteBuffers(vboId);
		}
	}
}
