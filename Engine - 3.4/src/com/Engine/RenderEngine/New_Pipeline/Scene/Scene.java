package com.Engine.RenderEngine.New_Pipeline.Scene;

import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import com.Engine.RenderEngine.Camera.Camera;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.Shaders.Render.Renderer;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.Util.Vectors.Vector3f;

public class Scene {
	public void render(Camera camera, Renderer<?, ?, ?>... renderers) {
		render(camera, Arrays.asList(renderers));
	}
	
	public void render(Camera camera, Collection<Renderer<?, ?, ?>> renderers) {
		Vector3f skyColour = Shader.getSkyColor();
		glClearColor(skyColour.x, skyColour.y, skyColour.z, 1);
		
		FBO.clear_bound();

		Shader.setProjectionMatrix(camera.getProjectionMatrix());
		Shader.setViewMatrix(camera.getViewMatrix());
		
		for(Iterator<Renderer<?, ?, ?>> iter = renderers.iterator(); iter.hasNext(); ) {
			Renderer<?, ?, ?> renderer = iter.next();
			renderer.render(camera);
		}
	}
	
//	private static List<List<Renderer<?, ?, ?>>> orderRenderers(Collection<Renderer<?, ?, ?>> renderers) {
//		
//	}
//	
//	public static class RenderOrder extends LinkedList<LinkedList<Renderer<?, ?, ?>>> {
//		private static final long serialVersionUID = -6249469509921047254L;
//		
//		
//	}
}
