package com.Engine.Demo.XML;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class XMLTag {
	private static final char WHITESPACE = '\t';
	
	private ArrayList<XMLTag> children;
	private ArrayList<XMLProperty> properties;

	private HashMap<String, LinkedList<XMLTag>> childrenLookup;
	private HashMap<String, LinkedList<Object>> propertiesLookup;
	
	private String name;
	
	public XMLTag(String name, Collection<XMLProperty> properties) {
		this.name = name;
		this.properties = new ArrayList<>(properties);
		this.children = new ArrayList<>();
		
		initLookup();
	}
	
	public XMLTag(String name, XMLProperty... properties) {
		this.name = name;
		
		this.properties = new ArrayList<>();
		for(XMLProperty property : properties) 
			if(property != null) this.properties.add(property);
		
		this.children = new ArrayList<>();
		
		initLookup();
	}
	
	private void initLookup() {
		this.childrenLookup = new HashMap<>();
		this.propertiesLookup = new HashMap<>();
	
		for(XMLProperty property : properties) addLookup(property); 
		for(XMLTag child : children) addLookup(child);
	}
	
	private void addLookup(XMLProperty property) {
		LinkedList<Object> props = propertiesLookup.get(property.getName());
		if(props == null) propertiesLookup.put(property.getName(), props = new LinkedList<>());
		props.add(property.getValue());
	}
	
	private void addLookup(XMLTag child) {
		LinkedList<XMLTag> tags = childrenLookup.get(child.getName());
		if(tags == null) childrenLookup.put(child.getName(), tags = new LinkedList<>());
		tags.add(child);
	}
	
	public Object getProperty(String name) { return propertiesLookup.get(name).get(0); }
	public XMLTag getChild(String name) { return childrenLookup.get(name).get(0); }
	
	public Collection<Object> getAllProperty(String name) { return propertiesLookup.get(name); }
	public Collection<XMLTag> getAllChildren(String name) { return childrenLookup.get(name); }

	public Collection<XMLProperty> getAllProperty() { return properties; }
	public Collection<XMLTag> getAllChildren() { return children; }

	public String getName() { return name; }
	public ArrayList<XMLProperty> getProperties() { return properties; }
	
	public void add(XMLTag e) { children.add(e); addLookup(e); }
	public void add(XMLProperty e) { properties.add(e); addLookup(e); }

	public String createTag(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix);
		builder.append(createHeaderTag());
		
		if(children.isEmpty()) {
			builder.append("/>");
			builder.append("\n");
			return builder.toString();
		}
		
		builder.append(">");
		builder.append("\n");
		for(XMLTag child : children) {
			builder.append(child.createTag(prefix + WHITESPACE));
		}
		
		builder.append(prefix);
		builder.append(createClosingTag());
		builder.append("\n");
		return builder.toString();
	}
	
	private String createHeaderTag() {
		StringBuilder builder = new StringBuilder();
		builder.append("<"); builder.append(XMLParser.escape(name));
		
		for(XMLProperty property : properties) {
			builder.append(" ");
			builder.append(property);
		}
		
		return builder.toString();
	}
	
	private String createClosingTag() {
		return "</" + XMLParser.escape(name) + ">";
	}
}
