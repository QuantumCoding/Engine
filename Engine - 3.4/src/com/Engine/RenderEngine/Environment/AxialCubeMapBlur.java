package com.Engine.RenderEngine.Environment;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Environment.CubeCamera.CameraDirection;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.Attachment;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderTexture;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Textures.TextureCubeMap;
import com.Engine.RenderEngine.Textures.TextureCubeMap.CubeMapSide;
import com.Engine.Util.Vectors.MatrixUtil;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class AxialCubeMapBlur {
	private static final AxialBlurShader BLUR_SHADER = new AxialBlurShader();
	
	private static final ModelData CUBE_MODEL = new ModelData(); static {
		CUBE_MODEL.storeDataInAttributeList(Shader.ATTRIBUTE_LOC_POSITIONS, 3, new float[] {
			-1, -1, -1,		+1, -1, -1,		
			-1, +1, -1,		+1, +1, -1,		-1, -1, +1,		+1, -1, +1,
											-1, +1, +1,		+1, +1, +1,
		}, BufferUsage.Static_Draw);
		
		CUBE_MODEL.loadIndices(new int[] {
				0, 1, 2,	2, 1, 3,		// Front
				5, 4, 6,	5, 6, 7,		// Back

				2, 6, 4,	2, 4, 0,		// Left
				7, 3, 5,	5, 3, 1,		// Right

				7, 6, 2,	7, 2, 3,		// Top
				4, 5, 0,	0, 5, 1,		// Bottom
		});
	}

	private static final Matrix4f[] FACE_ROTATION; static {
		FACE_ROTATION = new Matrix4f[6];
		Matrix4f projection = MatrixUtil.initPerspectiveMatrix(90, 1, .3f, 1000);
		
		FACE_ROTATION[CubeMapSide.Right	.ordinal()] = Matrix4f.mul(projection, MatrixUtil.initRotationMatrix(CameraDirection.Right.rotation), null);
		FACE_ROTATION[CubeMapSide.Left	.ordinal()] = Matrix4f.mul(projection, MatrixUtil.initRotationMatrix(CameraDirection.Left.rotation), null);
		
		FACE_ROTATION[CubeMapSide.Top	.ordinal()] = Matrix4f.mul(projection, MatrixUtil.initRotationMatrix(CameraDirection.Up.rotation), null);
		FACE_ROTATION[CubeMapSide.Bottom.ordinal()] = Matrix4f.mul(projection, MatrixUtil.initRotationMatrix(CameraDirection.Down.rotation), null);
		
		FACE_ROTATION[CubeMapSide.Front	.ordinal()] = Matrix4f.mul(projection, MatrixUtil.initRotationMatrix(CameraDirection.Forward.rotation), null);
		FACE_ROTATION[CubeMapSide.Back	.ordinal()] = Matrix4f.mul(projection, MatrixUtil.initRotationMatrix(CameraDirection.Backward.rotation), null);
	}
	
	private static final float ROTATION_ANGLE = (float) Math.toRadians(6);
	private static final Matrix3f[] ROTATION_MATRIX; static {
		ROTATION_MATRIX = new Matrix3f[3 * 2];
		
		for(int i = 0; i < 3; i ++) { // Only need to Calculate Matrix for +X, +Y, and +Z
			Vector3f axis = CubeMapSide.values()[i].axis();
			
			ROTATION_MATRIX[i * 2 + 0] = calcRotationMatrix(axis,  ROTATION_ANGLE);
			ROTATION_MATRIX[i * 2 + 1] = calcRotationMatrix(axis, -ROTATION_ANGLE);
		}
	}
	
	private static Matrix3f calcRotationMatrix(Vector3f axis, float angle) {
		Matrix4f blank = new Matrix4f();
		blank = blank.rotate(ROTATION_ANGLE, axis.toLWJGL());
		
		Matrix3f ret = new Matrix3f();
		ret.m00 = blank.m00;	ret.m10 = blank.m10;	ret.m20 = blank.m20;
		ret.m01 = blank.m01;	ret.m11 = blank.m11;	ret.m21 = blank.m21;
		ret.m02 = blank.m02;	ret.m12 = blank.m12;	ret.m22 = blank.m22;
		
		return ret;
	}
	
	private static final int BLUR_LEVELS = 4;
	
	private FBO[] targets;
	private TextureCubeMap[] results;
	
	public AxialCubeMapBlur(int width, int height) {
		targets = new FBO[CubeMapSide.values().length];    
		results = new TextureCubeMap[BLUR_LEVELS * 2];	
		
		for(int i = 0; i < BLUR_LEVELS * 2; i ++) {
			results[i] = new TextureCubeMap(new Vector2f(width, height));
		}
		
		for(CubeMapSide side : CubeMapSide.values()) {
			FBO target = targets[side.ordinal()] = new FBO(width, height);
			
			for(int i = 0; i < BLUR_LEVELS; i ++) {
				target.attach(new RenderTexture(width, height, results[i], side.gl(), 0), Attachment.ColourBuffer, i);
				
				int next = i + BLUR_LEVELS;
				target.attach(new RenderTexture(width, height, results[next], side.gl(), 0), Attachment.ColourBuffer, next);
			}
		}
	}
	
	public TextureCubeMap[] apply(TextureCubeMap cubeMap) {
		BLUR_SHADER.bind();
		CUBE_MODEL.bind();
			BLUR_SHADER.texture0.load(cubeMap);
			renderCube(1);

			BLUR_SHADER.texture0.load(results[3]);
			renderCube(2);
		CUBE_MODEL.unbind();
		Shader.unbind();

		return results;
	}
	
	private void renderCube(int shift) {
		for(CubeMapSide side : CubeMapSide.values()) {
			BLUR_SHADER.faceRotation.load(FACE_ROTATION[side.ordinal()]);
			
			int rotSide = (side.ordinal() + shift) % 3;
			BLUR_SHADER.forwardRot .load(ROTATION_MATRIX[rotSide * 2 + 0]);
			BLUR_SHADER.backwardRot.load(ROTATION_MATRIX[rotSide * 2 + 1]);
			
			int baseTarget = (shift - 1) * BLUR_LEVELS;
			targets[side.ordinal()].bindDraw(baseTarget + 0, baseTarget + 1, baseTarget + 2, baseTarget + 3);
			targets[side.ordinal()].clear();
				glDrawElements(GL_TRIANGLES, CUBE_MODEL.getIndiceCount(), GL_UNSIGNED_INT, 0);
		}
	}
}
