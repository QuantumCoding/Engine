package com.Engine.RenderEngine.Font;

import java.util.ArrayList;
import java.util.ListIterator;

import com.Engine.RenderEngine.Font.Layout.Line;
import com.Engine.RenderEngine.Font.Layout.Word;
import com.Engine.RenderEngine.Font.Render.TextMesh;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Models.ModelData.VBO;
import com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class TextMeshStitcher {
	public static final float DEFUALT_LINE_HEIGHT = 0.03f;
	
	public static TextMesh createMesh(String message, Font font, float fontSize, float aspectRatio, Vector2f limit) {
		return createMesh(message, font, fontSize, aspectRatio, limit, -1, -1);
	}

	public static TextMesh createMesh(String message, Font font, float fontSize, float aspectRatio, Vector2f limit, int cStart, int cEnd) {
		if(limit == null) limit = new Vector2f(-1, -1);
		
		float[] meshData = new float[message.length() * (4 + 2) * 4];
		
		int[] indices = new int[message.length() * 3 * 2 * 1];
		Vector2f cursor = new Vector2f();
		
		double yScale = (double) DEFUALT_LINE_HEIGHT / font.getLineHeight() * fontSize;
		Vector2f scale = new Vector2f(yScale * aspectRatio, yScale);
		Vector2f maxDimentions = new Vector2f();
		
		float lineHeight = -(float) yScale * font.getLineHeight();
		ArrayList<Float> vert = new ArrayList<>();
		ArrayList<Integer> index = new ArrayList<>();
		int charIndex = 0, lineNum = 0;
		boolean inSelection = false, newLineFirst = false;
		
		ArrayList<Integer> lineBreaks = new ArrayList<>();
		ArrayList<Float> charBreaks = new ArrayList<>();
		
		int indiceIndex = 0, meshIndex = 0; // positionIndex = 0, texIndex = 0;
		for(ListIterator<Line> lineIter = Line.create(message, font, scale, limit); lineIter.hasNext(); ) {
			lineBreaks.add(charIndex);
			
			for(ListIterator<Word> wordIter = lineIter.next().iterate(); wordIter.hasNext(); ) {
			for(ListIterator<Character> iter = wordIter.next().iterate(); iter.hasNext(); ) {
				Character symbol = iter.next();
				charBreaks.add(cursor.x * scale.x);
				
				if(symbol instanceof Character.CharacterSpace) {
					cursor.x += symbol.getAdvanceX(); 
					if(maxDimentions.x < cursor.x * scale.x) maxDimentions.x = cursor.x * scale.x;

				} else {
					calculateIndices(indices, indiceIndex, meshIndex);
					calculateVertices(cursor, symbol, scale, meshData, meshIndex);
					
					if(maxDimentions.x < meshData[meshIndex +12]) maxDimentions.x = meshData[meshIndex +12];
					if(maxDimentions.y > meshData[meshIndex +19]) maxDimentions.y = meshData[meshIndex +19];

					cursor.x += symbol.getAdvanceX();
				}
				
				if(newLineFirst) {
					vert.add(meshData[meshIndex + 0]); vert.add(lineHeight * lineNum);
					vert.add(meshData[meshIndex + 0]); vert.add(lineHeight * (lineNum + 1));
					newLineFirst = false;
				}
				
				if(charIndex == cStart) {
					vert.add(meshData[meshIndex + 0]); vert.add(lineHeight * lineNum);
					vert.add(meshData[meshIndex + 0]); vert.add(lineHeight * (lineNum + 1));
					inSelection = true;
				}
				
				if(charIndex == cEnd) {
					vert.add(meshData[meshIndex + 12]); vert.add(lineHeight * lineNum);
					vert.add(meshData[meshIndex + 12]); vert.add(lineHeight * (lineNum + 1));
					
					int s = vert.size() / 2;
					index.add(s - 4); index.add(s - 3); index.add(s - 2);
					index.add(s - 2); index.add(s - 3); index.add(s - 1);
					
					inSelection = false;
				}
				
				if(!(symbol instanceof Character.CharacterSpace)) {
					indiceIndex += 6;
					meshIndex += 24;
				}

				charIndex ++;
			}}

			if(inSelection) {
				vert.add(meshData[meshIndex - 24 + 12]); vert.add(lineHeight * lineNum);
				vert.add(meshData[meshIndex - 24 + 12]); vert.add(lineHeight * (lineNum + 1));
				
				int s = vert.size() / 2;
				index.add(s - 4); index.add(s - 3); index.add(s - 2);
				index.add(s - 2); index.add(s - 3); index.add(s - 1);
				
				newLineFirst = true;
			}
			
			cursor.x  = 0;
			cursor.y += font.getLineHeight();

			lineNum ++;
		}
		
		ModelData data = new ModelData(1, 10, new Vector3f());
		
		VBO vbo = new VBO(); //new DynVBO(6);
		vbo.put(meshData);
		
		data.attachAttribute(Shader.ATTRIBUTE_LOC_POSITIONS, vbo, 2, 0, 6);
		data.attachAttribute(Shader.ATTRIBUTE_LOC_TEXCOORDS, vbo, 4, 2, 6);
		
		data.loadIndices(indices); 
		
		ModelData sectionModel = null;
		
		if(!vert.isEmpty()) {
			float[] vertF = new float[vert.size()];
			for(int i = 0; i < vert.size(); i ++) vertF[i] = vert.get(i);
	
			int[] indexI = new int[index.size()];
			for(int i = 0; i < index.size(); i ++) indexI[i] = index.get(i);
			
			float[] textD = new float[vertF.length * 2];
			for(int i = 3; i < textD.length; i += 4) textD[i] = 3;
			
			sectionModel = new ModelData();
			sectionModel.storeDataInAttributeList(Shader.ATTRIBUTE_LOC_POSITIONS, 2, vertF, BufferUsage.Static_Draw);
			sectionModel.storeDataInAttributeList(Shader.ATTRIBUTE_LOC_TEXCOORDS, 4, textD, BufferUsage.Static_Draw);
			sectionModel.loadIndices(indexI);
		}
		
		return new TextMesh(message, font, fontSize, font.getLineHeight() * scale.y * 2, 
				limit, maxDimentions.multiply(1, -1), data, sectionModel, charBreaks, lineBreaks);
	}
	
	private static void calculateIndices(int[] indices, int offset, int dataOffset) {
		indices[offset + 0] = dataOffset / 6 + 0; 
		indices[offset + 1] = dataOffset / 6 + 1; 
		indices[offset + 2] = dataOffset / 6 + 2;

		indices[offset + 3] = dataOffset / 6 + 2; 
		indices[offset + 4] = dataOffset / 6 + 1; 
		indices[offset + 5] = dataOffset / 6 + 3;
	}
	
	private static void calculateVertices(Vector2f cursor, Character symbol, Vector2f scale, float[] meshData, int offset) {
		Vector2f  upperLeft = cursor.add(symbol.getOffsetX(), symbol.getOffsetY());
		Vector2f lowerRight = upperLeft.add(symbol.getWidth(), symbol.getHeight());

		upperLeft = upperLeft.multiply(scale).multiply(1, -1).add(-1, 1);
		lowerRight = lowerRight.multiply(scale).multiply(1, -1).add(-1, 1);
		
		meshData[offset + 0] =  upperLeft.x; 	meshData[offset + 1] =  upperLeft.y;
		meshData[offset + 6] =  upperLeft.x; 	meshData[offset + 7] = lowerRight.y;
		meshData[offset +12] = lowerRight.x; 	meshData[offset +13] =  upperLeft.y;
		meshData[offset +18] = lowerRight.x; 	meshData[offset +19] = lowerRight.y;
		
		Vector2f  upperLeftT = new Vector2f(symbol.getSheetX(), symbol.getSheetY());
		Vector2f lowerRightT = upperLeftT.add(symbol.getTexWidth(), symbol.getTexHeight());
		
		meshData[offset + 2] =  upperLeftT.x; 	meshData[offset + 3] =  upperLeftT.y;
		meshData[offset + 8] =  upperLeftT.x; 	meshData[offset + 9] = lowerRightT.y;
		meshData[offset +14] = lowerRightT.x; 	meshData[offset +15] =  upperLeftT.y;
		meshData[offset +20] = lowerRightT.x; 	meshData[offset +21] = lowerRightT.y;
		
		meshData[offset + 4] = meshData[offset +10] = meshData[offset +16] = meshData[offset +22] = symbol.getSheetId();
		meshData[offset + 5] = meshData[offset +11] = meshData[offset +17] = meshData[offset +23] = symbol.getColorChanel();
	}
}
