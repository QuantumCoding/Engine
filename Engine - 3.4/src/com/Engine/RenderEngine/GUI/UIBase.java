package com.Engine.RenderEngine.GUI;

import com.Engine.RenderEngine.GLFunctions.BlendFunc;
import com.Engine.RenderEngine.GUI.Components.SubComponents.ISubUIComponent;
import com.Engine.RenderEngine.GUI.Components.SubComponents.QuadComponent;
import com.Engine.RenderEngine.GUI.Events.KeyEvent;
import com.Engine.RenderEngine.GUI.Events.MouseEvent;
import com.Engine.RenderEngine.GUI.GraphicsProvider.Style;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public abstract class UIBase {
	protected static final BlendFunc BLEND = BlendFunc.normal();
	
	protected float x, y;
	protected float width, height;
	
	private float prefX, prefY;
	private float prefWidth, prefHeight;
	
	private float weightX, weightY;
	
	private boolean isFocusable;
	protected boolean hasFocus;
	
	private String toolTip;
	// TODO: Create Tool Tip UIComponent
	
	private boolean isVisible;
	private boolean isEnabled;
	
	// TODO: Add Parent element
//	private UIContainer parent;
	
	protected ISubUIComponent[] parts;
	
	public UIBase() {
	}
	
	public abstract void render();
	public abstract void updateUI();
	
	public abstract Style<? extends UIBase> getStyle();
	public abstract IUIState[] getStates();
	
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
	public void mouseClicked(MouseEvent e) { }
	
	public void keyPressed(KeyEvent e) { }
	public void keyReleased(KeyEvent e) { }
	public void keyTyped(KeyEvent e) { }

	public float getX() { return x; }
	public float getY() { return y; }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	
	public Vector2f getPosition() { return new Vector2f(x, y); }
	public Vector2f getDimentions() { return new Vector2f(width, height); }

	public float getPrefX() { return prefX; }
	public float getPrefY() { return prefY; }
	public float getPrefWidth() { return prefWidth; }
	public float getPrefHeight() { return prefHeight; }
	
	public float getWeightX() { return weightX; }
	public float getWeightY() { return weightY; }

	public boolean hasFocus() { return hasFocus; }
	public boolean isFocusable() { return isFocusable; }
	
	public boolean isVisible() { return isVisible; }
	public boolean isEnabled() { return isEnabled; }
	
	public String getToolTip() { return toolTip; }

	public ISubUIComponent[] getParts() { return parts; }
	
	public UIBase setX(float x) { updateUI(); this.x = x; return this; }
	public UIBase setY(float y) { translateParts(0, y - this.y); this.y = y; return this; }
	public UIBase setWidth(float width) { this.width = width; return this; }
	public UIBase setHeight(float height) { this.height = height; return this; }
	
	public UIBase setPrefX(float prefX) { this.prefX = prefX; return this; }
	public UIBase setPrefY(float prefY) { this.prefY = prefY; return this; }
	public UIBase setPrefWidth(float prefWidth) { this.prefWidth = prefWidth; return this; }
	public UIBase setPrefHeight(float prefHeight) { this.prefHeight = prefHeight; return this; }
	
	public UIBase setWeightX(float weightX) { this.weightX = weightX; return this; }
	public UIBase setWeightY(float weightY) { this.weightY = weightY; return this; }
	
	public UIBase setFocusable(boolean isFocusable) { this.isFocusable = isFocusable; return this; }
	public UIBase setToolTip(String toolTip) { this.toolTip = toolTip; return this; }
	
	public UIBase setVisible(boolean isVisible) { this.isVisible = isVisible; return this; }
	public UIBase setEnabled(boolean isEnabled) { this.isEnabled = isEnabled; return this; }
}
