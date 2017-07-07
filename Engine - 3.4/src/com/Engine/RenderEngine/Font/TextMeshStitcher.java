package com.Engine.RenderEngine.Font;

import java.util.ListIterator;

import com.Engine.RenderEngine.Font.Layout.Line;
import com.Engine.RenderEngine.Font.Layout.Word;
import com.Engine.RenderEngine.Font.Render.TextMesh;
import com.Engine.RenderEngine.Font.Render.TextShader;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class TextMeshStitcher {
	public static final float DEFUALT_LINE_HEIGHT = 0.03f;
	
	public static TextMesh createMesh(String message, Font font, float fontSize, float aspectRatio, Vector2f limit) {
		float[] positions = new float[message.length() * 4 * 2];
		float[] texCoords = new float[message.length() * 4 * 4];
		
		int[] indices = new int[message.length() * 3 * 2 * 1];
		Vector2f cursor = new Vector2f();
		
		float yScale = DEFUALT_LINE_HEIGHT / font.getLineHeight();
		Vector2f scale = new Vector2f(yScale * aspectRatio, yScale).multiply(fontSize);
		
		int indiceIndex = 0, positionIndex = 0, texIndex = 0;
		for(ListIterator<Line> lineIter = Line.create(message, font, scale, limit); lineIter.hasNext(); ) {
			for(ListIterator<Word> wordIter = lineIter.next().iterate(); wordIter.hasNext(); ) {
			for(ListIterator<Character> iter = wordIter.next().iterate(); iter.hasNext(); ) {
				Character symbol = iter.next();
				
				if(symbol instanceof Character.CharacterSpace) {
					cursor.x += symbol.getAdvanceX();
					continue;
				}

				calculateIndices(indices, indiceIndex, positionIndex);
				calculateVertices(cursor, symbol, scale, positions, texCoords, positionIndex, texIndex);
				
				indiceIndex += 6;
				positionIndex += 8;
				texIndex += 16;
				
				cursor.x += symbol.getAdvanceX();
			}}
			
			cursor.x  = 0;
			cursor.y += font.getLineHeight();
		}
		
		ModelData data = new ModelData(1, 10, new Vector3f());
		
		data.storeDataInAttributeList(TextShader.ATTRIBUTE_LOC_POSITIONS, 2, positions, false);
		data.storeDataInAttributeList(TextShader.ATTRIBUTE_LOC_TEXCOORDS, 4, texCoords, false);
		
		data.loadIndicies(indices);
		return new TextMesh(font, data);
	}
	
	private static void calculateIndices(int[] indices, int offset, int verticesOffset) {
		indices[offset + 0] = verticesOffset / 2 + 0; 
		indices[offset + 1] = verticesOffset / 2 + 1; 
		indices[offset + 2] = verticesOffset / 2 + 2;

		indices[offset + 3] = verticesOffset / 2 + 2; 
		indices[offset + 4] = verticesOffset / 2 + 1; 
		indices[offset + 5] = verticesOffset / 2 + 3;
	}
	
	private static void calculateVertices(Vector2f cursor, Character symbol, Vector2f scale, float[] positions, float[] texCoords, int offset, int offsetTex) {
		Vector2f  upperLeft = cursor.add(symbol.getOffsetX(), symbol.getOffsetY());
		Vector2f lowerRight = upperLeft.add(symbol.getWidth(), symbol.getHeight());

		upperLeft = upperLeft.multiply(scale).multiply(2, -2).add(-1, 1);
		lowerRight = lowerRight.multiply(scale).multiply(2, -2).add(-1, 1);
		
		positions[offset + 0] =  upperLeft.x; 	positions[offset + 1] =  upperLeft.y;
		positions[offset + 2] =  upperLeft.x; 	positions[offset + 3] = lowerRight.y;
		positions[offset + 4] = lowerRight.x; 	positions[offset + 5] =  upperLeft.y;
		positions[offset + 6] = lowerRight.x; 	positions[offset + 7] = lowerRight.y;
		
		Vector2f  upperLeftT = new Vector2f(symbol.getSheetX(), symbol.getSheetY());
		Vector2f lowerRightT = upperLeftT.add(symbol.getTexWidth(), symbol.getTexHeight());
		
		texCoords[offsetTex + 0] =  upperLeftT.x; 	texCoords[offsetTex + 1] =  upperLeftT.y;
		texCoords[offsetTex + 4] =  upperLeftT.x; 	texCoords[offsetTex + 5] = lowerRightT.y;
		texCoords[offsetTex + 8] = lowerRightT.x; 	texCoords[offsetTex + 9] =  upperLeftT.y;
		texCoords[offsetTex +12] = lowerRightT.x; 	texCoords[offsetTex +13] = lowerRightT.y;
		
		texCoords[offsetTex + 2] = texCoords[offsetTex + 6] = texCoords[offsetTex +10] = texCoords[offsetTex +14] = symbol.getSheetId();
		texCoords[offsetTex + 3] = texCoords[offsetTex + 7] = texCoords[offsetTex +11] = texCoords[offsetTex +15] = symbol.getColorChanel();
	}
}
