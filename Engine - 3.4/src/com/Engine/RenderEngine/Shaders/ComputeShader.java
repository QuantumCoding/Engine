package com.Engine.RenderEngine.Shaders;

import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL20.glGetProgram;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_WORK_GROUP_SIZE;
import static org.lwjgl.opengl.GL43.GL_MAX_COMPUTE_WORK_GROUP_COUNT;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public abstract class ComputeShader extends RawShader {
	private static final int MAX_GROUPS_X, MAX_GROUPS_Y, MAX_GROUPS_Z; static {
		
//		IntBuffer buffer = BufferUtils.createIntBuffer(16);
//		glGetInteger(GL_MAX_COMPUTE_WORK_GROUP_COUNT, buffer);
		
		MAX_GROUPS_X = 65536;//buffer.get(0);
		MAX_GROUPS_Y = 65536;//buffer.get(1);
		MAX_GROUPS_Z = 65536;//buffer.get(2);
	}
	
	private int workGroupSizeX, workGroupSizeY, workGroupSizeZ;
	
	public ComputeShader(String fileName) {
		super.loadShaderFile(fileName, ShaderTypes.ComputeShader);
		link();
	}

	protected final void bindAttributies() {
//		IntBuffer workGroupSize = BufferUtils.createIntBuffer(3);
//		glGetProgram(getProgramId(), GL_COMPUTE_WORK_GROUP_SIZE, workGroupSize);
		
		workGroupSizeX = 16;// workGroupSize.get(0);
		workGroupSizeY = 16;// workGroupSize.get(1);
		workGroupSizeZ = 1;// workGroupSize.get(2);
	}
	
	public abstract void execute(int sizeX, int sizeY, int sizeZ);
	
	protected void disbatch(int problemSizeX, int problemSizeY, int problemSizeZ) {
		int numGroupsX = (problemSizeX + workGroupSizeX - 1) / workGroupSizeX;
		int numGroupsY = (problemSizeY + workGroupSizeY - 1) / workGroupSizeY;
		int numGroupsZ = (problemSizeZ + workGroupSizeZ - 1) / workGroupSizeZ;
		
		if(numGroupsX >= MAX_GROUPS_X || numGroupsY >= MAX_GROUPS_Y || numGroupsZ >= MAX_GROUPS_Z) {
			throw new IllegalArgumentException("Problem is too Large for Compute Shader!\n"
					+ "\t Needed X: " + numGroupsX + ", Max: " + MAX_GROUPS_X
					+ "\t Needed Y: " + numGroupsY + ", Max: " + MAX_GROUPS_Y
					+ "\t Needed Z: " + numGroupsZ + ", Max: " + MAX_GROUPS_Z
				);
		}
		
		bind();
			glDispatchCompute(numGroupsX, numGroupsY, numGroupsZ);
	}
}
