package com.Engine.RenderEngine.Util;

import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Default.Model;

public class NullRenderer extends Renderer<Model, NullRenderProperties, Shader> {

	public NullRenderer(Shader shader) {
		super(shader);
	}
	
	public boolean addModel(Model model, NullRenderProperties property) { return false; }
	public void render(Camera camera) { } 
	
	protected void prepareOpenGL() { }
	protected void bindModel(Model model) { }
	protected void renderModel(Model model, NullRenderProperties properties) { }
	protected void unbindModel(Model model) { }
	protected void revertOpenGL() { }
}
