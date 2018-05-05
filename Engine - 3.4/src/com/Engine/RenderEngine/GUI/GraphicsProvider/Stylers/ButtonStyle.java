package com.Engine.RenderEngine.GUI.GraphicsProvider.Stylers;

import com.Engine.RenderEngine.Font.Font;
import com.Engine.RenderEngine.GUI.Components.UIButton;
import com.Engine.RenderEngine.GUI.Components.SubComponents.ISubUIComponent;
import com.Engine.RenderEngine.GUI.Components.SubComponents.TextComponet;
import com.Engine.RenderEngine.GUI.Components.SubComponents.QuadComponent;
import com.Engine.RenderEngine.GUI.GraphicsProvider.GraphicsProvidor;
import com.Engine.RenderEngine.GUI.GraphicsProvider.Style;
import com.Engine.Util.Vectors.Vector2f;

public class ButtonStyle extends Style<UIButton> {
	private Font font;
	
	private Vector2f shift;
	private Vector2f offset;
	private Vector2f dimentions;
	
	public ButtonStyle(Vector2f offset, Vector2f dimentions, Vector2f shift) {
		super(UIButton.class);
		
		this.shift = shift;
		this.offset = offset;
		this.dimentions = dimentions;
	}

	public ISubUIComponent[] updateUI(UIButton b) { return updateUI(b, StyleProperty.All); }
	public ISubUIComponent[] updateUI(UIButton b, StyleProperty property, int value) {
		ISubUIComponent[] parts = b.getParts();
		if(parts == null) {
			property = StyleProperty.All;
			parts = new ISubUIComponent[] {
				new QuadComponent(), new TextComponet(b.getText(), font == null ? providor.getDefaultFont() : font, 10, null)
			};
		}
		
		switch(property) {
			case All:
			case Dimentions:
				
			if(property != StyleProperty.All) break;
			
			case Location: 
				for(ISubUIComponent component : parts) component.translate(amount);
			if(property != StyleProperty.All) break;
			
			case State: 
			if(property != StyleProperty.All) break;
			
			case Text: 
			if(property != StyleProperty.All) break;

			case Other: case Value: 
			default: throw new IllegalArgumentException(property + " is not a valid Property for " + getClass().getSimpleName());
		}
		
		QuadComponent mainBody = (QuadComponent) parts[0];
		applyTextureCoords(mainBody, b.getState().ordinal()).updateBounds(b.getX(), b.getY(), b.getWidth(), b.getHeight());
		return parts;
	}
	
	// Dimensions
			// Scale
			
			// When more space is provided then needed:
				// Screen is Bigger:
					// Scale Area:
						// Scale UI
						// Scale Dimensions
			
					// Preserve Area:
						// No Change
			
				// Screen is Smaller:
					// Same as Bigger, except Reduction
					// Cannot perform Preserve Area

	public QuadComponent applyTextureCoords(QuadComponent properties, int state) {
		if(properties == null) throw new NullPointerException();
		properties.updateTextureBounds(offset.x + shift.x * state, offset.y, dimentions.x, dimentions.y);
		return properties;
	}
}
