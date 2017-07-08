package com.Engine.Util.XML;

public class XMLProperty {
	private String name;
	private Object value;
	
	public XMLProperty(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String toString() {
		return XMLParser.escape(name) + "=\"" + XMLParser.encodeValue(value) + "\"";
	}

	public String getName() { return name; }
	public Object getValue() { return value; }
	public void setValue(Object value) { this.value = value; }
	public void setName(String name) { this.name = name; }
}
