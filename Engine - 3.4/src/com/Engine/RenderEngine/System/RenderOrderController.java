package com.Engine.RenderEngine.System;

import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import com.Engine.RenderEngine.Camera.Camera;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.System.RenderingSystem.RenderLevel;
import com.Engine.Util.Vectors.Vector3f;

public class RenderOrderController {
	private static Camera REFERENCE_CAMERA;
	
	private static final Comparator<RenderingSystem<?, ?>> DEFAULT_SORTER = new Comparator<RenderingSystem<?, ?>>() {
		public int compare(RenderingSystem<?, ?> sys0, RenderingSystem<?, ?> sys1) {
			if(sys0.supportsTransparency() != sys1.supportsTransparency())
				return sys0.supportsTransparency() ? 1 : -1;
			
			if(sys0.supportsTransparency()) {
				if(sys0.usesAdditiveBlending() != sys1.usesAdditiveBlending())
					return sys0.usesAdditiveBlending() ? 1 : -1;
			}
			
			return Float.compare(sys0.getZLevel(REFERENCE_CAMERA), sys1.getZLevel(REFERENCE_CAMERA));
		}
	};

	private LinkedList<RenderingSystem<?, ?>> systems;
	
	public RenderOrderController() {
		systems = new LinkedList<>();
	}
	
	public void addRenderSystem(RenderingSystem<?, ?> system) {
//		for(ListIterator<RenderingSystem<?, ?>> iter = systems.listIterator(); iter.hasNext(); ) {
//			if(DEFAULT_SORTER.compare(system, iter.next()) < 0) { // FiFo: <	LiFo: <=
//				systems.add(iter.previousIndex(), system);
//				return;
//			}
//		}
		
		systems.add(system);
	}
	
	public void render(Camera camera, RenderLevel level) {
		render(new RenderSceneInfo(this, level, camera));
	}
	
	public void render(RenderSceneInfo sceneInfo) {
		Camera camera = sceneInfo.getCamera();
		RenderLevel level = sceneInfo.getLevel();
		
		REFERENCE_CAMERA = camera;
		systems.sort(DEFAULT_SORTER);
		
		Vector3f skyColour = Shader.getSkyColor();
		glClearColor(skyColour.x, skyColour.y, skyColour.z, 1);
		
		FBO.clear_bound();

		Shader.setProjectionMatrix(camera.getProjectionMatrix());
		Shader.setViewMatrix(camera.getViewMatrix());
		
		FBO target = FBO.currentDraw();
		
		for(Iterator<RenderingSystem<?, ?>> iter = systems.iterator(); iter.hasNext(); ) {
			target.bindDraw();
				iter.next().render(level, camera);
		}
	}
}
