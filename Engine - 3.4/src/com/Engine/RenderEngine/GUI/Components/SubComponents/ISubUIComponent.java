package com.Engine.RenderEngine.GUI.Components.SubComponents;

import com.Engine.Util.Vectors.Vector3f;

public interface ISubUIComponent {
	public void render(); 
	public ISubUIComponent updateBounds(float x, float y, float width, float height);
	
	public void translate(Vector3f amount);
}
