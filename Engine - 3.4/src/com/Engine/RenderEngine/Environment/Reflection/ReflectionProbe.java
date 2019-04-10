package com.Engine.RenderEngine.Environment.Reflection;

import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.opengl.GL43.GL_TEXTURE_CUBE_MAP;

import com.Engine.RenderEngine.Environment.CubeCamera;
import com.Engine.RenderEngine.Environment.CubeCamera.CameraDirection;
import com.Engine.RenderEngine.System.RenderSceneInfo;
import com.Engine.RenderEngine.System.RenderingSystem.RenderLevel;
import com.Engine.RenderEngine.System.Dependencies.IRenderDependency;
import com.Engine.RenderEngine.System.Dependencies.IRenderDependencyBuilder;
import com.Engine.RenderEngine.Textures.TextureCubeMap;
import com.Engine.RenderEngine.Textures.TextureCubeMapArray;
import com.Engine.Util.Vectors.Vector3f;

public class ReflectionProbe implements IRenderDependency {
	private CubeCamera cubeCamera;
	private TextureCubeMap reflectionMap;
	
	public ReflectionProbe(int resolution, float zNear, float zFar) {
		cubeCamera = new CubeCamera(resolution, zNear, zFar);
		reflectionMap = cubeCamera.getTexture();
	}
	
	public ReflectionProbe(TextureCubeMapArray textureMap, int level, int resolution, float zNear, float zFar) {
		cubeCamera = new CubeCamera(textureMap, level, resolution, zNear, zFar);
		reflectionMap = cubeCamera.getTexture();
	}
	
	public void setPosition(Vector3f pos) { cubeCamera.setPosition(pos); }
//	public void setRotation(Vector3f rot) { cubeCamera.setRotation(rot); }
	
	public TextureCubeMap getReflection() { return reflectionMap; }

	public boolean render(RenderSceneInfo sceneInfo) {
		for(CameraDirection dir : CameraDirection.values()) {
			cubeCamera.renderToFace(dir);
			cubeCamera.getFBO().clear();
			
			sceneInfo.getRenderController().render(cubeCamera, RenderLevel.Low);
		}
		
		if(reflectionMap != null) {
			reflectionMap.bind();
			glGenerateMipmap(GL_TEXTURE_CUBE_MAP);
			glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		}
		
		return true;
	}

	public int getMaxCycleDepth() { return 1; }
	
	public static class ReflectionProbeBuilder implements IRenderDependencyBuilder<ReflectionProbe> {
		private Vector3f position;
		private float zNear, zFar;
		private int resolution;
		
		private ReflectionProbeBuilder() {}
		public static ReflectionProbeBuilder create() { return new ReflectionProbeBuilder(); }
		
		public Class<ReflectionProbeSceneController> getControllerType() { return ReflectionProbeSceneController.class; }
		
		public ReflectionProbeBuilder setPosition(Vector3f position) { this.position = position; return this; }
		public ReflectionProbeBuilder setClipPlanes(float zNear, float zFar) { this.zNear = zNear; return this; }
		public ReflectionProbeBuilder setResolution(int resolution)  { this.resolution = resolution; return this; }

		public ReflectionProbe build() {
			ReflectionProbe probe = new ReflectionProbe(resolution, zNear, zFar);
			probe.cubeCamera.setPosition(position);
			return probe;
		}
		
		public Vector3f getPosition() { return position; }
		
		public float getNear() { return zNear; }
		public float getFar() { return zFar; }
		
		public int getResolution() { return resolution; }
	}
}
