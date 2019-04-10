package com.Engine.Util.XML;

import static com.Engine.Util.XML.XMLRegistry.ESCAPE_CODES;
import static com.Engine.Util.XML.XMLRegistry.ESCAPE_REVERT;
import static com.Engine.Util.XML.XMLRegistry.PARSERS;
import static com.Engine.Util.XML.XMLRegistry.PARSERS_ORDER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser {
	static {
		DefaultXMLParsers.register();
	}
	
	public static void registerEscapeStr(char character, String str) { XMLRegistry.registerEscapeStr(character, str); }
	public static <E> void redgisterParser(Class<E> clazz, XMLTypeParser<E> parser) { XMLRegistry.redgisterParser(clazz, parser); }
	
	public static String escape(String str) {
		StringBuilder builder = new StringBuilder(str.length());
		for(int i = 0; i < str.length(); i ++) {
			char c = str.charAt(i);
			String replace = ESCAPE_CODES.get(c);
			builder.append(replace == null ? c : replace);
		}
		
		return builder.toString();
	}
	
	public static String revert(String str) {
		boolean inEscape = false; String escapeStr = "";
		StringBuilder builder = new StringBuilder(str.length());
		
		for(int i = 0; i < str.length(); i ++) {
			char c = str.charAt(i);
			
			if(!inEscape) {
				if(c == '&') {
					inEscape = true;
					escapeStr = "";
					continue;
				}
				
				builder.append(c);
			} else {
				if(c == ';') {
					inEscape = false;
					builder.append(ESCAPE_REVERT.get(escapeStr));
					continue;
				}
				
				escapeStr += c;
			}
		}
		
		return builder.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static <E> String encodeValue(E value) {
		XMLTypeParser<? super E> parser = null;
		Class<? super E> clazz = (Class<? super E>) value.getClass();
		do { parser = (XMLTypeParser<? super E>) PARSERS.get(clazz); }
		while(parser == null && (clazz = clazz.getSuperclass()) != Object.class);
		
		String raw = parser == null ? value.toString() : parser.encode(value);
		return escape(raw);
	}
	
	public static Object parseValue(String value) {
		for(XMLTypeParser<?> parser : PARSERS_ORDER) {
			try { return parser.parse(value); } 
			catch(XMLParserFormatException e) { }
		}
		
		return value;
	}
	
	static final Pattern START_TAG_PATTERN = Pattern.compile("<([^\\/]{1})(.*?)>");
	static final Pattern END_TAG_PATTERN = Pattern.compile("(</|/>)");
	static final Pattern PROPERTY_PATTERN = Pattern.compile(" (.+?)=\"(.*?)\"");
	static final Pattern TEXT_BLOCK_PATTERN = Pattern.compile(">([^<]+?)<");
	
	// http://files.zeroturnaround.com/pdf/zt_regular-expressions-cheat-sheet.pdf
	
	public static XMLTag[] read(InputStream in) throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(in));
		StringBuilder builder = new StringBuilder(in.available());
		String line = ""; while((line = read.readLine()) != null) { 
			String trimmed = line.trim(); if(trimmed.isEmpty()) continue;
			builder.append(trimmed.replace("\n", "").replace("\r", "")); 
		}
		
		String full = builder.toString();
		
		if(full.startsWith("<?")) {
			int index = full.indexOf("?>");
			full = full.substring(index + 2);
		}
		
		try {
			Matcher startMatcher = START_TAG_PATTERN.matcher(full);
			Matcher endMatcher = END_TAG_PATTERN.matcher(full);
			Matcher textMatcher = TEXT_BLOCK_PATTERN.matcher(full);
	
			ArrayList<XMLTag> rootTags = new ArrayList<>();
			ArrayDeque<XMLTag> tagStack = new ArrayDeque<>();
			
			MatchResult start = null, end = null, text = null;
	
			while(true) {
				if(start == null) {
					boolean found = startMatcher.find();
					if(found) start = startMatcher.toMatchResult();
				}
				
				if(end == null) {
					boolean found = endMatcher.find();
					if(found) end = endMatcher.toMatchResult();
				}
				
				if(text == null) {
					boolean found = textMatcher.find();
					if(found) text = textMatcher.toMatchResult();
				}
	
				if(text != null && end == null)
					throw new XMLException("Text Blocks must have a parent");
				if(start != null && end == null)
					throw new XMLException("Tag started without end");
				if(end == null) break;
				
				if(text != null && (start == null || text.start() < start.start()) && text.start() < end.start()) {
					if(tagStack.isEmpty())
						throw new XMLException("Text Blocks must have a parent");
					
					String textData = full.substring(text.start() + 1, text.end() - 1);
					XMLTextBlock textTag = new XMLTextBlock(revert(textData));
					tagStack.peek().add(textTag);
					
					text = null;
					
				} else if(start != null && start.start() < end.start()) {
					// Starting New Tag
					int endPoint = start.end() < end.start() ? start.end() : end.start() + 1;
					String tagData = full.substring(start.start() + 1, endPoint - 1);
					
					int spaceIndex = tagData.indexOf(' ');
					String name = tagData.substring(0, spaceIndex > 0 ? spaceIndex : tagData.length());
	
					Matcher propertyMatcher = PROPERTY_PATTERN.matcher(tagData);
					ArrayList<XMLProperty> properties = new ArrayList<>();
					while(propertyMatcher.find()) {
						String rawProperty = propertyMatcher.group().trim();
						String[] split = rawProperty.split("=", 2);
						
						String value = split[1].substring(1, split[1].length() - 1);
						properties.add(new XMLProperty(revert(split[0]), parseValue(revert(value))));
					}
					
					XMLTag tag = new XMLTag(revert(name), properties);
					if(tagStack.isEmpty()) rootTags.add(tag);
					else tagStack.peek().add(tag);
					
					if(start.end() < end.start())
						tagStack.push(tag);
					else end = null;
					
					start = null;
				
				} else {
					String tagName = full.substring(end.start() + 2, full.indexOf('>', end.start() + 2));
					tagName = revert(tagName);
					
					XMLTag tag = tagStack.pop();
					if(!tag.getName().equals(tagName))
						throw new XMLException("Tag closed before it was opened");
					
					end = null;
				}
			}
			
			if(!tagStack.isEmpty())
				throw new XMLException("Tags left unclosed");
			
			return rootTags.toArray(new XMLTag[0]);
			
		} catch(Exception e) {
			if(e instanceof XMLException) throw e;
			
			XMLException xmlException = new XMLException("Error occured while parsing");
			xmlException.initCause(e);
			throw xmlException;
		}
	}
}
