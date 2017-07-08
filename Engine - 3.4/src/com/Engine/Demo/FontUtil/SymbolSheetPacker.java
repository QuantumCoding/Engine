package com.Engine.Demo.FontUtil;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.JOptionPane;

import com.Engine.Demo.FontUtil.FontCreater.ProgressNotify;
import com.Engine.Demo.FontUtil.SymbolFactory.FontMetadata;
import com.Engine.Demo.FontUtil.SymbolFactory.Symbol;
import com.Engine.Util.XML.XMLProperty;
import com.Engine.Util.XML.XMLTag;

public class SymbolSheetPacker {
	public static class SymbolSheetMetadata implements Serializable {
		private static final long serialVersionUID = -2147270999230301051L;
		
		public boolean useMultiChannel;
		public int width, height;
		public FontMetadata font;
	}
	
	public static class SymbolSheet {
		public BufferedImage[] pages;
		private Symbol[] symbols;
		public XMLTag symbolData;
	}
	
	public static SymbolSheet pack(SymbolSheetMetadata meta, ProgressNotify progress, Collection<Symbol> symbols) {
		return pack(meta, progress, symbols.toArray(new Symbol[0]));
	}
	
	public static SymbolSheet pack(SymbolSheetMetadata meta, ProgressNotify progress, Symbol... symbols) {
		SymbolSheet sheet = new SymbolSheet();
		sheet.symbols = symbols;
		
		Integer[] avalable = new Integer[symbols.length];
		for(int i = 0; i < symbols.length; i ++)
			avalable[i] = i;
		Arrays.sort(avalable, (prm, snd) -> {
			Symbol primary = symbols[prm], secondary = symbols[snd];
			return -Float.compare(primary.width + primary.height * 1.5f, secondary.width + secondary.height * 1.5f);
		});

		boolean noFit = false;
		int remaining = avalable.length, attempt = 0;
		progress.updateProgressBar("Packing Symbols, " + remaining + " remaining...",
				avalable.length - remaining, avalable.length, 0, 1);
		
		ArrayList<BufferedImage> pages = new ArrayList<>();
		PageDrawer drawer = new PageDrawer(meta);
		while(remaining > 0) {
			Symbol symbol = symbols[avalable[attempt]];
			
			if(drawer.draw(symbol)) {
				remaining --;
				avalable[attempt] = -1;
				symbol.sheetId = pages.size();
				noFit = false;

				progress.updateProgressBar("Packing Symbols, " + remaining + " remaining...",
						avalable.length - remaining, avalable.length, 0, 1);
				attempt = -1;
				if(remaining <= 0) break;
			}

			do {
				if(++ attempt >= symbols.length) {
					PageDrawer next = drawer.createNext();
					if(next != drawer) pages.add(drawer.img);
					drawer = next;
					attempt = 0;
					
					if(noFit) {
						JOptionPane.showMessageDialog(null, 
								"Sheet size too small to add any Characters.\n"
								+ "Try Increasing sheet size or Decreasing font size.", "Size Issue", 
								JOptionPane.ERROR_MESSAGE
							);
						
						return null;
					} else {
						noFit = true;
					}
				} 
			} while(avalable[attempt] == -1);
		}
		
//		for(Symbol symbol : symbols) {
//			if(!drawer.draw(symbol)) {
//				pages.add(drawer.img);
//				
//				drawer = new PageDrawer(meta);
//				drawer.draw(symbol);
//			}
//			
//			symbol.sheetId = pages.size();
//		}

		FontMetadata font = meta.font;
		FontMetrics metrics = font.metrics;
		int lineHeight = metrics.getAscent() + metrics.getDescent() + metrics.getLeading();
		
		char[] space = new char[] {' '};
		GlyphVector vector = font.font.layoutGlyphVector(font.context, space, 0, 1, Font.LAYOUT_LEFT_TO_RIGHT);
		int spaceWidth = (int) vector.getGlyphLogicalBounds(0).getBounds().getWidth();
		
		XMLTag root = sheet.symbolData = new XMLTag("symbol_sheet", 
				new XMLProperty("width", meta.width),
				new XMLProperty("height", meta.height),
				new XMLProperty("padding", meta.font.smeer * 2),
				new XMLProperty("usingMultiChanel", meta.useMultiChannel)
			);
		root.add(new XMLTag("lineData", 
				new XMLProperty("lineHeight", lineHeight),
				new XMLProperty("spaceWidth", spaceWidth)
				//https://github.com/libgdx/libgdx/blob/master/extensions/gdx-tools/src/com/badlogic/gdx/tools/hiero/unicodefont/UnicodeFont.java
				//https://www.dropbox.com/sh/kcqoizmat085t0v/AABZJ5qHcu4b2KzXTorLV3w5a?dl=0
			));
		root.add(new XMLTag("font", 
				new XMLProperty("name", meta.font.font.getName()),
				new XMLProperty("size", meta.font.font.getSize()),
				new XMLProperty("bold", (meta.font.font.getStyle() & Font.BOLD) != 0),
				new XMLProperty("italic", (meta.font.font.getStyle() & Font.ITALIC) != 0)
			));

		XMLTag symbolSetTag = new XMLTag("symbol_set");
		for(Symbol symbol : sheet.symbols) {
			symbolSetTag.add(new XMLTag("symbol", 
					new XMLProperty("id", symbol.charCode),

					new XMLProperty("sheetId", symbol.sheetId),
					new XMLProperty("colorChanel", symbol.colorChanel),
					
					new XMLProperty("sheetX", symbol.sheetX),
					new XMLProperty("sheetY", symbol.sheetY),

					new XMLProperty("width", symbol.width),
					new XMLProperty("height", symbol.height),

					new XMLProperty("xAdvance", symbol.xAdvance),
					new XMLProperty("yAdvance", symbol.yAdvance),

					new XMLProperty("xOffset", symbol.xOffset),
					new XMLProperty("yOffset", symbol.yOffset)
				));
		}
		
		root.add(symbolSetTag);
		
//		System.out.println(root.createTag(""));
		
		pages.add(drawer.img);
		sheet.pages = pages.toArray(new BufferedImage[0]);
		
		return sheet;
	}
	
	private static class PageDrawer {
		private SymbolSheetMetadata meta;
		private BufferedImage img;
		private int channel, shift;
		private int x, y;
		
		public PageDrawer(SymbolSheetMetadata meta) {
			this.meta = meta;
			img = new BufferedImage(meta.width, meta.height, meta.useMultiChannel ? BufferedImage.TYPE_INT_ARGB :
				BufferedImage.TYPE_BYTE_GRAY);
			shift = channel % 2 == 0 ? channel == 0 ? 2 * 8 : 0 : channel * 8;
		}
		
		public PageDrawer createNext() {
			if(!meta.useMultiChannel) return new PageDrawer(meta);
			x = 0; y = 0; channel ++; shift = channel % 2 == 0 ? channel == 0 ? 2 * 8 : 0 : channel * 8;
			if(channel > 3) return new PageDrawer(meta);
			else return this;
		}
		
		public boolean draw(Symbol symbol) {
			if(x + symbol.width > meta.width) {
				x = 0; y += meta.font.metrics.getAscent() + meta.font.smeer * 1.5;
			}
			
			if(y + symbol.height > meta.height) {
				return false;
			}

			symbol.sheetX = x; symbol.sheetY = y;
			symbol.colorChanel = meta.useMultiChannel ? channel : 0;
			
			for(int imgX = 0; imgX < symbol.image.getWidth();  imgX ++) {
			for(int imgY = 0; imgY < symbol.image.getHeight(); imgY ++) {
				if(imgX + x >= img.getWidth() || imgY + y >= img.getHeight()) continue;
				
				int value = symbol.image.getRGB(imgX, imgY) >> 24 & 0xFF;
			
				int rgb = meta.useMultiChannel ? img.getRGB(imgX + x, imgY + y) | value << shift:
					img.getRGB(imgX + x, imgY + y) | 0xFF << 24 | value << 16 | value << 8 | value;
				
				img.setRGB(imgX + x, imgY + y, rgb);
			}}

			x += symbol.width - meta.font.smeer / 2;
			return true;
		}
	}
}
