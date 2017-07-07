package com.Engine.Demo.FontUtil;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class SymbolFactory {
	public static class FontMetadata implements Serializable {
		private static final long serialVersionUID = -8843632021482237346L;
		
		public Font font;
		public transient FontMetrics metrics;
		public transient FontRenderContext context;
		
		public int upscale;
		public float smeer;
		
		public boolean equals(Object obj) {
			if(this == obj) return true;
			if(obj == null) return false;
			if(!(obj instanceof FontMetadata))
				return false;
			
			FontMetadata other = (FontMetadata) obj;
			if(context == null) {
				if(other.context != null)
					return false;
			} else if(!context.equals(other.context))
				return false;
			
			if(font == null) {
				if(other.font != null)
					return false;
			} else if(!font.equals(other.font))
				return false;
			
			if(Float.floatToIntBits(smeer) != Float.floatToIntBits(other.smeer)) return false;
			if(upscale != other.upscale) return false;
			
			return true;
		}
	}
	
	public static class Symbol {
		// Sheet Creation Info
		public BufferedImage image;
		int charCode;
		
		// Render Info
		float xAdvance, yAdvance;
		float xOffset, yOffset;

		// Sheet Data
		float sheetX, sheetY;
		float width, height;
		int sheetId, colorChanel;
	}
	
	public static Symbol createSymbol(int code, FontMetadata meta) {
		Symbol symbol = new Symbol();
		symbol.charCode = code;
		
		GlyphVector vector = meta.font.createGlyphVector(meta.context, (char) code + "");
		GlyphMetrics metrics = vector.getGlyphMetrics(0);
		Rectangle2D bounds = metrics.getBounds2D();
		
		BufferedImage img = new BufferedImage((int)((bounds.getWidth() + 2 * meta.smeer) * meta.upscale), 
				(int)((bounds.getHeight() + 2 * meta.smeer) * meta.upscale), BufferedImage.TYPE_BYTE_GRAY);

		float centerX = (float) (img.getWidth() / meta.upscale - bounds.getWidth()) / 2 - (float) bounds.getX();
		float centerY = (float) (img.getHeight() / meta.upscale - bounds.getHeight()) / 2 - (float) bounds.getY();
		Shape outline = vector.getGlyphOutline(0, centerX, centerY);
		
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.scale(meta.upscale, meta.upscale);
		g.fill(outline); g.dispose();
		
		symbol.image = smeer(img, meta);
		float padding = meta.smeer * 2;
		
		float lsb = Math.min(metrics.getLSB(), 0);
		float rsb = Math.min(metrics.getRSB(), 0);
		float glyphWidth = (float) bounds.getWidth() - lsb - rsb;
		float glyphHeight = (float) bounds.getHeight();
		
		if (glyphWidth > 0 && glyphHeight > 0) {
			symbol.width = glyphWidth + padding;
			symbol.height = glyphHeight + padding;
			symbol.yOffset = meta.metrics.getAscent() + (float) bounds.getY() - padding/2;
		}
		
//		g = symbol.image.createGraphics();
//		g.setColor(Color.WHITE);
//		g.drawRect((int)(padding/2-3), (int)(padding/2-3), (int)(symbol.width-padding+6), (int)(symbol.height-padding+6));
//		g.dispose();
		
		char[] chars = Character.toChars(code);
		GlyphVector charVector = meta.font.layoutGlyphVector(meta.context, chars, 0, chars.length, Font.LAYOUT_LEFT_TO_RIGHT);
		symbol.xOffset = charVector.getGlyphPixelBounds(0, meta.context, 0, 0).x - padding/2;
		symbol.xAdvance = (int)(metrics.getAdvanceX() + padding);
		
		return symbol;
	}
	
	private static BufferedImage smeer(BufferedImage src, FontMetadata meta) {
		boolean[][] binary = new boolean[src.getWidth()][src.getHeight()];
		for(int x = 0; x < src.getWidth();  x ++) {
		for(int y = 0; y < src.getHeight(); y ++) {
			binary[x][y] = (src.getRGB(x, y) & 0xFF) > 0x7F;
		}}
		
		BufferedImage dst = new BufferedImage(src.getWidth() / meta.upscale, 
				src.getHeight() / meta.upscale, BufferedImage.TYPE_4BYTE_ABGR);

		float smeer = meta.smeer * meta.upscale;
		for(int x = 0; x < dst.getWidth();  x ++) {
		for(int y = 0; y < dst.getHeight(); y ++) {
			int midX = x * meta.upscale + meta.upscale / 2;
			int midY = y * meta.upscale + meta.upscale / 2;
			
//			float shortestDistance = findSignedDistance(midX, midY, binary, meta);
			float shortestDistance = calculateShortestDistance(midX, midY, smeer, binary);
			shortestDistance /= smeer * 2; shortestDistance += .5f; shortestDistance *= 0xFF;
			int value = (int) shortestDistance;
			
			dst.setRGB(x, y, value << 24 | 0xFF << 16 | 0xFF << 8 | 0xFF);
		}}

		return dst;
	}
	
	private static float calculateShortestDistance(int centerX, int centerY, float radius, boolean[][] img) {
		int scan = (int) Math.ceil(radius);
		float shortestSqDist = scan * scan;
		
		int xLower = Math.max(0, centerX - scan), xUpper = Math.min(centerX + scan, img.length - 1);
		int yLower = Math.max(0, centerY - scan), yUpper = Math.min(centerY + scan, img[0].length - 1);
		
		for(int x = xLower; x <= xUpper; x ++) {
		for(int y = yLower; y <= yUpper; y ++) {
			if(img[x][y] != img[centerX][centerY]) {
				float changeX = x - centerX; changeX *= changeX;
				float changeY = y - centerY; changeY *= changeY;
				float sqDist = (float) changeX + changeY;
				
				if(sqDist < shortestSqDist) shortestSqDist = sqDist;
			}
		}}
		
		float shortestDist = (float) Math.min(radius, Math.sqrt(shortestSqDist));
		return shortestDist * (img[centerX][centerY] ? 1 : -1);
	} 
}
