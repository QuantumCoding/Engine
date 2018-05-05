package com.Engine.RenderEngine.GUI.GraphicsProvider;

import com.Engine.RenderEngine.GUI.UIBase;
import com.Engine.RenderEngine.GUI.Components.SubComponents.ISubUIComponent;
import com.Engine.RenderEngine.GUI.Components.SubComponents.QuadComponent;

public abstract class Style<T extends UIBase> {
	private Class<T> uiType;
	protected GraphicsProvidor providor;
	
	public Style(Class<T> clazz) {
		this.uiType = clazz;
	}
	
	public boolean doesTypeMatch(T obj) { return doesTypeMatch(obj.getClass()); }
	public boolean doesTypeMatch(Class<?> obj) { return obj.equals(uiType); }
	
	public ISubUIComponent[] updateUI(T component, StyleProperty property) { return updateUI(component, property, -1); }
	public abstract ISubUIComponent[] updateUI(T component, StyleProperty property, int value);
	
	public static enum StyleProperty {
		Location, Dimentions, Text, Value, State, Other, All;
	}
	
	protected void setGraphicProvidor(GraphicsProvidor providor) { this.providor = providor; }
	
	protected QuadComponent[] generateUIPartSet(int size) {
		QuadComponent[] parts = new QuadComponent[size];
		for(int i = 0; i < size; i ++)
			parts[i] = new QuadComponent();
		return parts;
	} 
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uiType == null) ? 0 : uiType.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof Style))
			return false;
		
		@SuppressWarnings("unchecked")
		Style<? extends UIBase> other = (Style<? extends UIBase>) obj;
		
		if(uiType == null) {
			if(other.uiType != null)
				return false;
		} else if(!uiType.equals(other.uiType))
			return false;
		
		return true;
	}
}
