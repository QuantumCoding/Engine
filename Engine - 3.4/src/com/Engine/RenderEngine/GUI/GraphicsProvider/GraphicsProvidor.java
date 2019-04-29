package com.Engine.RenderEngine.GUI.GraphicsProvider;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.Engine.RenderEngine.Font.Font;
import com.Engine.RenderEngine.GUI.UIBase;
import com.Engine.RenderEngine.GUI.Components.UIButton;
import com.Engine.RenderEngine.GUI.GraphicsProvider.Stylers.ButtonStyle;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;

public class GraphicsProvidor {
	private static GraphicsProvidor DEFAULT_GRAPHICS_PROVIDOR;
	public static GraphicsProvidor defaultGraphicsProvidor() { return DEFAULT_GRAPHICS_PROVIDOR; }
	
	static {
		DEFAULT_GRAPHICS_PROVIDOR = new GraphicsProvidor(null, Font.loadFont(GraphicsProvidor.class.getResourceAsStream("Serif.qFnt")));
		DEFAULT_GRAPHICS_PROVIDOR.setDefault(UIButton.class, new ButtonStyle(new Vector2f(0, 68).divide(256), new Vector2f(82,32).divide(256), new Vector2f(84/256f, 0)));
	}
	
	private static class StylePackage<T extends UIBase> {
		private static final Object DEFAULT = new Object();
		
		private HashMap<Object, Style<T>> styles = new HashMap<>();
		private StylePackage(Style<T> defaultStyle) { setDefault(defaultStyle); }
		
		public Style<T> setDefault(Style<T> style) { return addStyle(DEFAULT, style); }
		public Style<T> addStyle(Object key, Style<T> style) { return styles.put(key, style); }
		
		public Style<T> getDefaultStyle() { return styles.get(DEFAULT); }
		
		public Style<T> getStyle(Object key) {
			Style<T> style = getStyle(key);
			return style == null ? styles.get(DEFAULT) : style;
		}
		
		public List<Style<T>> getAvailableStyles() {
			return (List<Style<T>>) styles.entrySet().stream()
					.map(t -> t.getValue())
					.distinct()
					.collect(Collectors.toList());
		}
	}
	
	private HashMap<Class<? extends UIBase>, StylePackage<? extends UIBase>> styles;
	private Texture2D texture;
	private Font defaultFont;
	
	public GraphicsProvidor(Texture2D texture, Font font) {
		styles = new HashMap<>();
		this.texture = texture;
		this.defaultFont = font;
	}
	
	public Texture2D getTexture() { return texture; }
	public Font getDefaultFont() { return defaultFont; }
	
	@SuppressWarnings("unchecked")
	private <T extends UIBase> StylePackage<T> pullPack(Class<T> uiType) { 
		return (StylePackage<T>) styles.get(uiType); }

	@SuppressWarnings("unchecked")
	private <T extends UIBase> StylePackage<T> pushPack(Class<T> uiType, Style<T> style) { 
		if(!style.doesTypeMatch(uiType)) throw new IllegalArgumentException(
				"Style's UI Type must macth that of the UI Type provided");

		StylePackage<T> pack = (StylePackage<T>) styles.get(uiType);
		if(pack == null) styles.put(uiType, pack = new StylePackage<>(style));
		
		return pack;
	}

	public <T extends UIBase> void setDefault(Class<T> uiType, Style<T> style) { pushPack(uiType, style).setDefault(style); }
	public <T extends UIBase> void addStyle(Class<T> uiType, Object altKey, Style<T> style) {
		pushPack(uiType, style).addStyle(altKey, style); style.setGraphicProvidor(this);
	}

	public <T extends UIBase> Style<T> getStyle(Class<T> uiType, Object key) { return pullPack(uiType).getStyle(key); }
	public <T extends UIBase> Style<T> getDefaultStyle(Class<T> uiType) { return pullPack(uiType).getDefaultStyle(); }
	public <T extends UIBase> List<Style<T>> getAvailableStyles(Class<T> uiType) { 
		return pullPack(uiType).getAvailableStyles(); }
}
