package com.Engine.RenderEngine.GUI.Components;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.Engine.RenderEngine.GUI.IUIState;
import com.Engine.RenderEngine.GUI.UIBase;
import com.Engine.RenderEngine.GUI.Components.SubComponents.TextComponet;
import com.Engine.RenderEngine.GUI.GraphicsProvider.GraphicsProvidor;
import com.Engine.RenderEngine.GUI.GraphicsProvider.Style;
import com.Engine.RenderEngine.GUI.GraphicsProvider.Style.StyleProperty;
import com.Engine.RenderEngine.GUI.GraphicsProvider.Stylers.ButtonStyle;
import com.Engine.RenderEngine.GUI.Shader.UIGraphics;
import com.Engine.Util.Time;
import com.Engine.Util.Vectors.Vector2f;

public class UIButton extends UIBase {
	private TextComponet text;

	protected ButtonStates state;
	protected Style<UIButton> style;
	
	public UIButton() { this(""); }
	public UIButton(String text) {
		super();
		this.state = ButtonStates.Normal;
		this.text = new TextComponet(text, null, 10, null);
		setStyle(GraphicsProvidor.defaultGraphicsProvidor().getDefaultStyle(UIButton.class));
	}

	double last, sum;
	
	public void render() {
		BLEND.enable();

		UIGraphics.drawRect(parts);
		
//		sum += Time.getSystemTime() - last;
//		last = Time.getSystemTime();
//		
//		state = (ButtonStates) getStates()[(int) (sum / Time.SECONDS / 5 % getStates().length)];
		
		if(Mouse.getX() > x && Mouse.getX() < x + width
		&& Display.getHeight() - Mouse.getY() > y && Display.getHeight() - Mouse.getY() < y + height) {
			state = Mouse.isButtonDown(0) ? ButtonStates.Armed : ButtonStates.Hover;
			updateUI();
		} else {
			state = ButtonStates.Normal;
			updateUI();
		}
		
//		Vector2f textPosition = getDimentions().subtract(text.getSize())
//				.divide(2).add(getPosition()).addY(text.getSize().y);
//		text.draw(textPosition.x, textPosition.y);
	}
	
	public String getText() { return text.getText(); }

	public void updateUI() { updateUI(StyleProperty.All); }
	public void updateUI(StyleProperty property) { parts = style.updateUI(this, property); }
	public void updateUI(StyleProperty property, int value) { parts = style.updateUI(this, property, value); }
	
	public void updateUI(Style<UIButton> style) { setStyle(style); }
	public void updateUI(GraphicsProvidor graphics, Object key) { setStyle(graphics.getStyle(UIButton.class, key)); }
	public void setStyle(GraphicsProvidor graphics, Object key) { setStyle(graphics.getStyle(UIButton.class, key)); }
	public void setStyle(Style<UIButton> style) { this.style = style; updateUI(); }

	public Style<? extends UIBase> getStyle() { return style; }
	public ButtonStates getState() { return state; }
	public IUIState[] getStates() { return ButtonStates.values(); }
	
	public static enum ButtonStates implements IUIState {
		Normal, Hover, Armed, Disabled;
	}
}
