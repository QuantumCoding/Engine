package com.Engine.RenderEngine.Font;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage;
import com.Engine.RenderEngine.Models.ModelData.VBOs.CashedVBO;
import com.Engine.RenderEngine.Models.ModelData.VBOs.DynVBO;
import com.Engine.RenderEngine.Models.ModelData.VBOs.IndexVBO;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector2f;

public class DynamicTextStitcher {
	private DynamicTextStitcher() { }
	
	public static final float DEFUALT_LINE_HEIGHT = 0.03f;
	
	private static final int POSITION_DATA_SIZE = 3 * 4;
	private static final int TEXCOORD_DATA_SIZE = 4 * 4;
	private static final int LETTER_DATA_SIZE = POSITION_DATA_SIZE + TEXCOORD_DATA_SIZE;
	private static final int[] INDEX_REMOVAL = new int[6];
	
	private static ModelData createVAO(String text, int extraBufferAmount) {
		ModelData mesh = new ModelData();
		
		DynVBO vbo = new CashedVBO(LETTER_DATA_SIZE);
		vbo.putEmpty(BufferUsage.Dynamic_Draw, text.length() + extraBufferAmount);
		
		mesh.attachAttribute(Shader.ATTRIBUTE_LOC_POSITIONS, vbo, 3, 0, 7);
		mesh.attachAttribute(Shader.ATTRIBUTE_LOC_TEXCOORDS, vbo, 4, 3, 7);
		
		IndexVBO indices = new IndexVBO();
		indices.putEmpty(BufferUsage.Dynamic_Draw, (text.length() + extraBufferAmount) * 6);
		mesh.loadIndices(indices);
		
		vbo.setExpansionAction(newVBO -> {
			mesh.attachAttribute(Shader.ATTRIBUTE_LOC_POSITIONS, newVBO, 3, 0, 7);
			mesh.attachAttribute(Shader.ATTRIBUTE_LOC_TEXCOORDS, newVBO, 4, 3, 7);
			
			indices.expand(newVBO.getSize() / LETTER_DATA_SIZE * 6);
		});
		
		return mesh;
	}
	
	private static ModelData createMask(IndexVBO indices) {
		ModelData mesh = new ModelData();
		
		DynVBO vbo = new CashedVBO(POSITION_DATA_SIZE);
		vbo.putEmpty(BufferUsage.Dynamic_Draw, indices.getSize() / 6);
		
		mesh.attachAttribute(Shader.ATTRIBUTE_LOC_POSITIONS, vbo, 3, 0, 3);
		mesh.loadIndices(indices);

		vbo.setExpansionAction(maskVBO -> {
			mesh.attachAttribute(Shader.ATTRIBUTE_LOC_POSITIONS, maskVBO, 3, 0, 3);
		});
		
		return mesh;
	}
	
	private static float[] calculateLetterMesh(int strIndex, Vector2f cursor, Character symbol, Vector2f scale) {
		return calculateLetterMesh(strIndex, cursor, symbol, scale, new float[LETTER_DATA_SIZE], 0); }

	private static float[] calculateLetterMesh(int strIndex, Vector2f cursor, Character symbol, Vector2f scale, float[] meshData, int offset) {
		Vector2f  upperLeft = cursor.add(symbol.getOffsetX(), symbol.getOffsetY());
		Vector2f lowerRight = upperLeft.add(symbol.getWidth(), symbol.getHeight());

		upperLeft = upperLeft.multiply(scale).multiply(1, -1).add(-1, 1);
		lowerRight = lowerRight.multiply(scale).multiply(1, -1).add(-1, 1);
		
		meshData[offset + 0] =  upperLeft.x; 	meshData[offset + 1] =  upperLeft.y; 	meshData[offset + 2] = strIndex;
		meshData[offset + 7] =  upperLeft.x; 	meshData[offset + 8] = lowerRight.y; 	meshData[offset + 9] = strIndex;
		meshData[offset +14] = lowerRight.x; 	meshData[offset +15] =  upperLeft.y; 	meshData[offset +16] = strIndex;
		meshData[offset +21] = lowerRight.x; 	meshData[offset +22] = lowerRight.y; 	meshData[offset +23] = strIndex;

		Vector2f  upperLeftT = new Vector2f(symbol.getSheetX(), symbol.getSheetY());
		Vector2f lowerRightT = upperLeftT.add(symbol.getTexWidth(), symbol.getTexHeight());
		
		meshData[offset + 3] =  upperLeftT.x; 	meshData[offset + 4] =  upperLeftT.y;
		meshData[offset +10] =  upperLeftT.x; 	meshData[offset +11] = lowerRightT.y;
		meshData[offset +17] = lowerRightT.x; 	meshData[offset +18] =  upperLeftT.y;
		meshData[offset +24] = lowerRightT.x; 	meshData[offset +25] = lowerRightT.y;
		
		meshData[offset + 5] = meshData[offset +12] = meshData[offset +19] = meshData[offset +26] = symbol.getSheetId();
		meshData[offset + 6] = meshData[offset +13] = meshData[offset +20] = meshData[offset +27] = symbol.getColorChanel();
		
		return meshData;
	}
	
	private static float[] calculateLetterMask(int strIndex, Vector2f cursor, Vector2f cursorPost, float lineHeight, Vector2f scale) {
		return calculateLetterMask(strIndex, cursor, cursorPost, lineHeight, scale, new float[POSITION_DATA_SIZE], 0); }
	
	private static float[] calculateLetterMask(int strIndex, Vector2f cursor, Vector2f cursorPost, float lineHeight, Vector2f scale, float[] maskData, int offset) {
		Vector2f  upperLeft = cursor;
		Vector2f lowerRight = cursorPost.add(0, lineHeight);

		upperLeft = upperLeft.multiply(scale).multiply(1, -1).add(-1, 1);
		lowerRight = lowerRight.multiply(scale).multiply(1, -1).add(-1, 1);
		
		maskData[offset + 0] =  upperLeft.x; 	maskData[offset + 1] =  upperLeft.y; 	maskData[offset + 2] = strIndex;
		maskData[offset + 3] =  upperLeft.x; 	maskData[offset + 4] = lowerRight.y; 	maskData[offset + 5] = strIndex;
		maskData[offset + 6] = lowerRight.x; 	maskData[offset + 7] =  upperLeft.y; 	maskData[offset + 8] = strIndex;
		maskData[offset + 9] = lowerRight.x; 	maskData[offset +10] = lowerRight.y; 	maskData[offset +11] = strIndex;
		
		return maskData;
	}
	
	private static int[] calculateIndices(int symbolOffset) { return calculateIndices(new int[6], 0, symbolOffset); }
	private static int[] calculateIndices(int[] indices, int offset, int symbolOffset) {
		indices[offset + 0] = symbolOffset * 4 + 0; 
		indices[offset + 1] = symbolOffset * 4 + 1; 
		indices[offset + 2] = symbolOffset * 4 + 2;

		indices[offset + 3] = symbolOffset * 4 + 2; 
		indices[offset + 4] = symbolOffset * 4 + 1; 
		indices[offset + 5] = symbolOffset * 4 + 3;
		
		return indices;
	}
	
	private static void insertSymbol(DynTextMesh text, int index, Character symbol) {
		if(symbol instanceof Character.CharacterReturn) {
			Vector2f cursor = text.getCursor(index - 1);
			float cursorHeight = cursor.getY() + symbol.getAdvanceY();
			text.addLetter(index, new Vector2f(0, cursorHeight), symbol, -1);
			
			insertBlankLine(text, index + 1);
			recalculateCursorHorizontal(text, index + 1, -1);
			return;
		}
		
		CashedVBO vbo = (CashedVBO) text.getVBO(); Vector2f cursor;
		int vboIndex = vbo.addInstance(calculateLetterMesh(index, cursor = text.getCursor(index - 1), symbol, text.getTextScale()));
		
		text.addLetter(index, cursor.add(symbol.getAdvanceX(), symbol.getAdvanceY()), symbol, vboIndex);
				
		CashedVBO maskVBO = (CashedVBO) text.getMaskVBO();
		maskVBO.addInstance(calculateLetterMask(index, cursor, text.getCursor(index), text.getFontLineHeight(), text.getTextScale()));
		
		IndexVBO indices = text.getMesh().getIndexVBO();
//		int vboSize = vbo.getSize() / vbo.getInstanceSize();
		
//		if(indices.getSize() / 6 < vboSize) {
//			indices.expand(vboSize * 6);
//			text.getMesh().loadIndices(indices);
//			text.getMask().loadIndices(indices);
//
//			text.getMesh().attachAttribute(Shader.ATTRIBUTE_LOC_POSITIONS, vbo, 3, 0, 7);
//			text.getMesh().attachAttribute(Shader.ATTRIBUTE_LOC_TEXCOORDS, vbo, 4, 3, 7);
//
//			text.getMask().attachAttribute(Shader.ATTRIBUTE_LOC_POSITIONS, maskVBO, 3, 0, 3);
//		}
		
		indices.update(vboIndex * 6, calculateIndices(vboIndex));
		if(cursor.x + symbol.getAdvanceX() > text.getLengthLimit()) 
			wrapLine(text, index);
		else if(isSplitSymbol(symbol)) 
			attemptRetroFix(text, index);
		recalculateCursorHorizontal(text, index + 1, -1);
		
		vbo.submit();
		maskVBO.submit();
	}
	
	private static void removeSymbol(DynTextMesh text, int index) {
		Character symbol = text.getSymbol(index);
		int vboIndex = text.removeLetter(index);
		
		if(symbol instanceof Character.CharacterReturn) {
			recalculateCursorHorizontal(text, index, -1);
			return;
		}
		
		CashedVBO vbo = (CashedVBO) text.getVBO();
		vbo.removeIndex(vboIndex);
		
		CashedVBO maskVBO = (CashedVBO) text.getMaskVBO();
		maskVBO.removeIndex(vboIndex);
		
		text.getMesh().getIndexVBO().update(vboIndex * 6, INDEX_REMOVAL);
//		shiftFollowing(text, index, new Vector2f(-symbol.getAdvanceX(), -symbol.getAdvanceY()));
		
		attemptRetroFix(text, index);
		recalculateCursorHorizontal(text, index, -1);
		
		vbo.submit();
		maskVBO.submit();
	}
	
	protected static void recalculateCursorHorizontal(DynTextMesh text, int index, int currentOverflow) {
		Vector2f cursor = text.getCursor(index - 1);
		int overflowIndex = currentOverflow, blankLineCheck = -1;
		
		for(int i = index, size = text.getText().length(); i < size; i ++) {
			Character symbol = text.getSymbol(i);
			if(symbol instanceof Character.CharacterReturn) {
				if(currentOverflow < 0 && cursor.x > text.getLengthLimit())
					insertBlankLine(text, i);
				
				else if(cursor.x < text.getLengthLimit())
					blankLineCheck = i;
				
				break;
			}
			
			if(text.isWrapPoint(i)) {
				float limit = text.getLengthLimit();
				float advance = cursor.x + symbol.getAdvanceX();
				
				if(overflowIndex < 0 && advance > limit) {
					overflowIndex = i;
					text.removeWrapPoint(i, overflowIndex);
				}
				
				else if(nextSplitWidth(text, i + 1) + advance < limit) {
					text.removeWrapPoint(i, overflowIndex);
				}
				
				else if(currentOverflow < 0)
					blankLineCheck = i;
				
				break;
			}
			
			cursor = text.setCursor(i, cursor.add(symbol.getAdvanceX(), 0));
			if(overflowIndex < 0 && cursor.x > text.getLengthLimit()) overflowIndex = i;
		}
		
		if(currentOverflow < 0 && overflowIndex > 0) wrapLine(text, overflowIndex);
		
		else if(blankLineCheck > 0) {
			float dy = text.getCursor(blankLineCheck).y - cursor.y;
			if(dy > 0 && dy / text.getFont().getLineHeight() > 1.9f)
				removeBlankLine(text, blankLineCheck);
		}
	}
	
	private static void insertBlankLine(DynTextMesh text, int index) {
		Vector2f shiftAmount = new Vector2f(0, text.getFont().getLineHeight());
		for(int i = index, size = text.getText().length(); i < size; i ++) {
			text.shiftCursor(i, shiftAmount);
		}
	}
	
	private static void removeBlankLine(DynTextMesh text, int index) { // TODO: WRITE METHOD
		Vector2f shiftAmount = new Vector2f(0, -text.getFont().getLineHeight());
		for(int i = index, size = text.getText().length(); i < size; i ++) {
			text.shiftCursor(i, shiftAmount);
		}
	}
	
	private static void attemptRetroFix(DynTextMesh text, int index) {
		for(int i = index - 1; i >= 0; i --) {
			if(text.isWrapPoint(i)) {
				float cursor = text.getCursor(i - 1).x + text.getSymbol(i).getAdvanceX();
				float nextWidth = nextSplitWidth(text, i);
				if(cursor + nextWidth < text.getLengthLimit())
					text.removeWrapPoint(i, -1);
				break;
			}
		}
	}
	
	private static float nextSplitWidth(DynTextMesh text, int index) {
		float sumWidth = 0;
		
		splitSearch:
		for(int i = index, size = text.getText().length(); i < size; i ++) {
			if(text.isWrapPoint(i)) break splitSearch;
			
			Character symbol = text.getSymbol(i);
			sumWidth += symbol.getAdvanceX();
			
			switch(symbol.getCharCode()) {
				case '-':
				case Character.CharacterSpace.ASCII_SPACE:
				case Character.CharacterReturn.ASCII_RETURN: // TODO: Might be problem...
					if(sumWidth > 0)
						return sumWidth;
			}
		}
		
		return sumWidth;
	}
	
	private static void wrapLine(DynTextMesh text, int index) {
		int splitPoint = index - 1; 
		
		splitSearch:
		for(int i = index; i >= 0; i --) { // TODO: Might be problem if Bounds is < size of Characters
			if(text.isWrapPoint(i)) break splitSearch;
			
			switch(text.getSymbol(i).getCharCode()) {
				case '-':
				case Character.CharacterSpace.ASCII_SPACE:
					splitPoint = i;
				
				case Character.CharacterReturn.ASCII_RETURN:
					break splitSearch;
			}
		}
			
		text.insertWrapPoint(splitPoint);
	}
	
	private static boolean isSplitSymbol(Character symbol) {
		switch(symbol.getCharCode()) {
			case '-':
			case Character.CharacterSpace.ASCII_SPACE:
				return true;
			
			default: return false;
		}
	}
	
//	private static void returnAdjustFollowing(DynTextMesh text, int index) {
//		CashedVBO vbo = (CashedVBO) text.getVBO();
//		CashedVBO maskVBO = (CashedVBO) text.getMaskVBO();
//		
//		for(int i = index, size = text.getText().length(); i < size; i ++) {
//			int vboIndex = text.getVBOIndex(i);
//			text.shiftCursor(i, shift);
//			
//			vbo.update(vboIndex * LETTER_DATA_SIZE, 
//					calculateLetterMesh(i, text.getCursor(i - 1), text.getSymbol(i), text.getTextScale()));
//
//			maskVBO.update(vboIndex * POSITION_DATA_SIZE, 
//					calculateLetterMask(i, text.getCursor(i - 1), text.getCursor(i), text.getLineHeight(), text.getTextScale()));
//		}
//		
//		vbo.submit();
//		maskVBO.submit();
//	}
	
	public static DynTextMesh createTextMesh(String text, int expansionBuffer, Font font, float fontSize, float aspectRatio) {
		return createTextMesh(text, expansionBuffer, font, fontSize, -1, aspectRatio);
	}
	public static DynTextMesh createTextMesh(String text, int expansionBuffer, Font font, float fontSize, float lengthLimit, float aspectRatio) {
		double yScale = (double) DEFUALT_LINE_HEIGHT / font.getLineHeight() * fontSize;
		Vector2f scale = new Vector2f(yScale * aspectRatio, yScale);
		lengthLimit = lengthLimit / scale.x;
		
		ModelData mesh = createVAO(text, expansionBuffer);
		ModelData mask = createMask(mesh.getIndexVBO());
		
		mesh.getIndexVBO().setExpansionAction(newIndices -> {
			mesh.loadIndices((IndexVBO) newIndices);
			mask.loadIndices((IndexVBO) newIndices);
		});
		
		DynTextMesh textMesh = new DynTextMesh(mesh, mask, font, fontSize, lengthLimit, scale);
		append(textMesh, text);
		
		return textMesh;
	}
	
	public static void append(DynTextMesh text, String str) { 
		insert(text, text.getText().length(), str); 
	}
	public static void insert(DynTextMesh text, int index, String str) {
		if(index < 0 || index > text.getText().length()) throw new IndexOutOfBoundsException("" + index);
		CashedVBO vbo = (CashedVBO) text.getVBO(); vbo.block(true);
		CashedVBO maskVBO = (CashedVBO) text.getMaskVBO(); maskVBO.block(true);
		
		Font font = text.getFont();
		for(int i = 0; i < str.length(); i ++) {
			Character symbol = font.getCharacter(str.charAt(i));
			insertSymbol(text, index + i, symbol);
		}
		
		remesh(text, index);

		vbo.block(false);
		maskVBO.block(false);
	}
	
	public static void remove(DynTextMesh text, int index) { 
		if(index < 0 || index >= text.getText().length()) throw new IndexOutOfBoundsException("" + index);
		removeSymbol(text, index); 
	}
	public static void remove(DynTextMesh text, int start, int end) {
		if(start > end) return;
//			throw new IllegalArgumentException("Start must be < End"); 
		if(start < 0 || end > text.getText().length()) throw new IndexOutOfBoundsException(
				"Range: " + start + " - " + end + " Text Length: " + text.getText().length());
		
		CashedVBO vbo = (CashedVBO) text.getVBO(); vbo.block(true);
		CashedVBO maskVBO = (CashedVBO) text.getMaskVBO(); maskVBO.block(true);
		
		for(int i = end; i >= start ; i --) 
			removeSymbol(text, i);
		remesh(text, start);
		
		vbo.block(false);
		maskVBO.block(false);
	}
	
	public static void setText(DynTextMesh mesh, String text) {
		CashedVBO vbo = (CashedVBO) mesh.getVBO();
		int bufferSize = Math.max(vbo.getSize() / vbo.getInstanceSize() - text.length(), 0);
		
		float aspectRatio = mesh.getTextScale().x / (DEFUALT_LINE_HEIGHT / mesh.getFont().getLineHeight() * mesh.getFontSize());
		DynTextMesh newMesh = createTextMesh(text, bufferSize, mesh.getFont(), mesh.getFontSize(), aspectRatio);
	
		mesh.copyFrom(newMesh);
	}
	
	private static void remesh(DynTextMesh text, int index) {
		CashedVBO vbo = (CashedVBO) text.getVBO();
		CashedVBO maskVBO = (CashedVBO) text.getMaskVBO();
		
//		for(; index >= 0; index --) 
//			if(!text.needsRemesh(index)) break;
//		index ++;
		
		for(int i = 0; i < text.getText().length(); i ++) {
			boolean needsRemesh = text.needsRemesh(i);
			
			text.remeshComplete(i);
			int vboIndex = text.getVBOIndex(i);
			if(vboIndex < 0) continue;
			
			maskVBO.update(vboIndex * POSITION_DATA_SIZE, 
					calculateLetterMask(i + 1, text.getCursor(i - 1), 
					text.isWrapPoint(i) ? text.getCursor(i - 1).add(text.getSymbol(i).getAdvanceX(), 0) : text.getCursor(i), 
					text.getFontLineHeight(), text.getTextScale()));
			
			if(!needsRemesh) continue;
			
			vbo.update(vboIndex * LETTER_DATA_SIZE, 
					calculateLetterMesh(i + 1, text.getCursor(i - 1), text.getSymbol(i), text.getTextScale()));
		}
		
		text.recalcSize();
	}
}
