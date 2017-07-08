package com.Engine.Util.XML;

public class XMLTextBlock extends XMLTag {
	private String text;
	
	public XMLTextBlock(String text) {
		super("Text");
		this.text = text;
	}
	
	public void add(XMLTag e) { throw new IllegalAccessError("Illegal Operation! XMLTextBlocks cannot have Children"); }
	public void add(XMLProperty e) { throw new IllegalAccessError("Illegal Operation! XMLTextBlocks do not have Properties"); }

	public String createTag(String level) {
		return level + XMLParser.escape(text.replace("\r", "").replace("\n", "&nl_txblock_rplace;"))
			.replace("&nl_txblock_rplace;", "&nl;\n" + level) + "\n";
	}
}
