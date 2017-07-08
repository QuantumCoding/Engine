package com.Engine.RenderEngine.Instancing;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

import com.Engine.RenderEngine.Models.ModelData.Attribute;

public class InstanceUtil {
	public static int createEmptyVBO(int floatCount) {
		int vbo = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, floatCount * 4, GL_STREAM_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		return vbo;
	}
	
	public static void addInstanceAttribute(int vao, int vbo, Attribute attribute, int unitSize, int instanceLength, int offset, int renderStrid) {
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBindVertexArray(vao);
		
		for(int i = 0; i < attribute.getStride(); i ++) {
			int attribId = attribute.getId() + i;
			
			glEnableVertexAttribArray(attribId);
			glVertexAttribPointer(attribId, unitSize, GL_FLOAT, false, instanceLength * 4, offset * 4);
			glVertexAttribDivisor(attribId, renderStrid);
			glDisableVertexAttribArray(attribId);
			
			offset += unitSize;
		}

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
}
