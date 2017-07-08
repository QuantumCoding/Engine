package com.Engine.Util.XML;

import java.io.FileInputStream;
import java.io.IOException;

import com.Engine.RenderEngine.Font.Character;

public class XMLTester {
	public static void main(String[] args) {
//		System.out.println(IntegerParser.INSTANCE.parse("0xff"));
//		System.out.println(IntegerParser.INSTANCE.parse("0b11"));
//		System.out.println(IntegerParser.INSTANCE.parse("010"));
//		System.out.println(IntegerParser.INSTANCE.parse("1e3"));
//		System.out.println(IntegerParser.INSTANCE.parse("50"));
	
//		int i = 10000000000;
//		System.out.println(Integer.parseInt("10000000000"));
		
//		IntegerParser.register();
		
//		XMLTag tag = new XMLTag("Test", new XMLProperty("Prop_1", "Strang"));
//		XMLTag tag2 = new XMLTag("Child", new XMLProperty("Prop_3", "hi"), new XMLProperty("Proper", 5));
//		XMLTag tagT = new XMLTextBlock("Hello I am a Text Block");
//		
//		tag.add(tag2);
//		tag.add(tagT);
//		System.out.println(tag.createTag(""));
		
		try {
			XMLTag[] tags = XMLParser.read(new FileInputStream("C:\\Users\\Joshua\\Desktop\\Cube Animation\\Test.xml"));
			
//			for(XMLTag tag : tags) {
//				if(!tag.getName().equals("symbol_sheet")) continue;
//				
//				for(XMLTag symbol : tag.getChild("symbol_set").getAllChildren()) {
//					new Character(symbol);
//				}
//			}
//			
//			for(XMLTag tag : tags) System.out.println(tag.createTag(""));
//			for(XMLTag tag : tags) { 
//				System.out.println(tag.getName() + ": "); 
//				for(XMLProperty property : tag.getProperties())
//					System.out.println("\t" + property.getName() + ".." + property.getValue() + " -> " + property.getValue().getClass().getSimpleName());
//			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
