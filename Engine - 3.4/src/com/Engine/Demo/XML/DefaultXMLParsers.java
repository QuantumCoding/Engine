package com.Engine.Demo.XML;

public class DefaultXMLParsers {
	public static void register() {
		IntegerParser.register();
		FloatParser.register();
		DoubleParser.register();
		LongParser.register();
		
		BooleanParser.register();
	}
	
	private static class IntegerParser implements XMLTypeParser<Integer> {
		private static final IntegerParser INSTANCE = new IntegerParser();
		public static void register() { XMLParser.redgisterParser(Integer.class, INSTANCE); }

		public String encode(Integer e) { return e.toString(); }
		public Integer parse(String s) {
			if(s.endsWith("l") || s.endsWith("L")) throw new XMLParserFormatException();
			
			try {
				int radix =  10;
				if(s.length() > 1 && s.startsWith("0")) {
					switch(s.charAt(1)) {
						case 'x': radix = 16; s = s.substring(2); break;
						case 'b': radix = 2; s = s.substring(2); break;
						
						default: if(Character.isDigit(s.charAt(1))) { 
							radix = 8; s = s.substring(1); break; 
						}
					}
				}
				
				int index = s.indexOf('e');
				if(index > 0) {
					int base = Integer.parseInt(s.substring(0, index));
					return (int) (Math.pow(10, Integer.parseInt(s.substring(index + 1))) * base);
				}
				
				return Integer.valueOf(s, radix);
				
			} catch(NumberFormatException e) {
				throw new XMLParserFormatException();
			}
		}
	}
	
	private static class FloatParser implements XMLTypeParser<Float> {
		private static final FloatParser INSTANCE = new FloatParser();
		public static void register() { XMLParser.redgisterParser(Float.class, INSTANCE); }

		public String encode(Float e) { return e.toString(); }
		public Float parse(String s) { 
			try {
				if(s.endsWith("d") || s.endsWith("D")) throw new XMLParserFormatException();
				return Float.parseFloat(s);
			} catch(NumberFormatException e) { throw new XMLParserFormatException(); }
		}
	}
	
	private static class DoubleParser implements XMLTypeParser<Double> {
		private static final DoubleParser INSTANCE = new DoubleParser();
		public static void register() { XMLParser.redgisterParser(Double.class, INSTANCE); }

		public String encode(Double e) { return e.toString() + "d"; }
		public Double parse(String s) { 
			try {
				if(!s.endsWith("d") || !s.endsWith("D")) throw new XMLParserFormatException();
				return Double.parseDouble(s); 
			} catch(NumberFormatException e) { throw new XMLParserFormatException(); }
		}
	}
	
	private static class LongParser implements XMLTypeParser<Long> {
		private static final LongParser INSTANCE = new LongParser();
		public static void register() { XMLParser.redgisterParser(Long.class, INSTANCE); }

		public String encode(Long e) { return e.toString() + "l"; }
		public Long parse(String s) {
			if(!s.endsWith("l") || !s.endsWith("L")) throw new XMLParserFormatException();
			long returnValue = 0;
			
			try {
				int radix =  10;
				if(s.length() > 1 && s.startsWith("0")) {
					switch(s.charAt(1)) {
						case 'x': radix = 16; s = s.substring(2); break;
						case 'b': radix = 2; s = s.substring(2); break;
						
						default: if(Character.isDigit(s.charAt(1))) { 
							radix = 8; s = s.substring(1); break; 
						}
					}
				}
				
				int index = s.indexOf('e');
				if(index > 0) {
					long base = Long.parseLong(s.substring(0, index));
					return returnValue = (long) (Math.pow(10, Long.parseLong(s.substring(index + 1))) * base);
				}
				
				return returnValue = Long.valueOf(s, radix);
				
			} catch(NumberFormatException e) {
				throw new XMLParserFormatException();
				
			} finally {
				if(returnValue < Integer.MAX_VALUE && returnValue > Integer.MIN_VALUE)
					throw new XMLParserFormatException();
			}
		}
	}
	
	private static class BooleanParser implements XMLTypeParser<Boolean> {
		private static final BooleanParser INSTANCE = new BooleanParser();
		public static void register() { XMLParser.redgisterParser(Boolean.class, INSTANCE); }

		public String encode(Boolean e) { return e.toString(); }
		public Boolean parse(String s) { 
			if(!(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"))) throw new XMLParserFormatException();
			return Boolean.parseBoolean(s); 
		}
	}
}
