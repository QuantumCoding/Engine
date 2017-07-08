package com.Engine.Util.XML;

import java.util.HashMap;

public class XMLRegistry {
	private XMLRegistry() { }
	
	static final HashMap<Class<?>, XMLTypeParser<?>> PARSERS = new HashMap<>();
	static final HashMap<Character, String> ESCAPE_CODES = new HashMap<>();
	static final HashMap<String, Character> ESCAPE_REVERT = new HashMap<>();
	
	static {
		registerEscapeStr('"', "quot");
		registerEscapeStr('&', "amp");
		registerEscapeStr('\'', "apos");
		registerEscapeStr('<', "lt");
		registerEscapeStr('>', "gt");
		registerEscapeStr('\n', "nl");
	}
	
	public static void registerEscapeStr(char character, String str) {
		ESCAPE_CODES.put(character, "&" + str + ";");
		ESCAPE_REVERT.put(str, character);
	}
	
	public static <E> void redgisterParser(Class<E> clazz, XMLTypeParser<E> parser) {
		PARSERS.put(clazz, parser);
	}
}
