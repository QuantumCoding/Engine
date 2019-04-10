package com.Engine.RenderEngine.Shaders.Deferred;

import java.util.Collection;
import java.util.HashSet;

import com.Engine.RenderEngine.Camera.Camera;
import com.Engine.RenderEngine.Environment.Reflection.ReflectionProbe;
import com.Engine.RenderEngine.Shaders.Deferred.High.DeferredRenderer;
import com.Engine.RenderEngine.Shaders.Deferred.High.DeferredShader;
import com.Engine.RenderEngine.Shaders.Deferred.Lights.Light;
import com.Engine.RenderEngine.Shaders.Deferred.Low.LowForwardRenderer;
import com.Engine.RenderEngine.Shaders.Deferred.Low.LowForwardShader;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.System.RenderingSystem;
import com.Engine.RenderEngine.System.Dependencies.IRenderDependency;
import com.Engine.RenderEngine.Textures.TextureCubeMapArray;
import com.Engine.Util.Tracker;

public class DeferredRenderingSystem extends RenderingSystem<PBRModel, DeferredRenderProperties> {
	private static final DeferredShader HIGH = new DeferredShader();
	private static final LowForwardShader LOW = new LowForwardShader();
	
	private Collection<IRenderDependency> reflectionProbes = new HashSet<>();
	
	public void addReflectionProbe(ReflectionProbe probe) { this.reflectionProbes.add(probe); }
	public void removeReflectionProbe(ReflectionProbe probe) { this.reflectionProbes.remove(probe); }
	
	public Shader selectShader(RenderLevel level) {
		switch(level) {
			case High: 	 return HIGH;
			
			case Medium:
			case Low:	 return LOW;
			
			default: throw new IllegalArgumentException(level + " is not a Valid RenderLevel");
		}
	}
	
	private Tracker<Float> exposure = new Tracker<>(RenderLevel.values().length);
	private Tracker<Collection<Light>> lights = new Tracker<>(RenderLevel.values().length);
	private Tracker<TextureCubeMapArray> environmentMap = new Tracker<>(RenderLevel.values().length);
	private Tracker<Integer> environmentMapCap = new Tracker<>(RenderLevel.values().length);
	
	public void setExposure(float exposure) { this.exposure.set(exposure); }
	public void setLights(Collection<Light> lights) { this.lights.set(lights); }
	public void setEnvironmentMap(TextureCubeMapArray map) { this.environmentMap.set(map); }
	public void setEnvironmentMapCap(int cap) { this.environmentMapCap.set(cap); }
	
	protected void prepareRender(RenderLevel level) {
		switch(level) {
			case High: HIGH.bind();
				DeferredRenderer highRenderer = (DeferredRenderer) HIGH.getRenderer();
				int ordinal = RenderLevel.High.ordinal();
				
				if(lights.changed(ordinal)) highRenderer.setLights(lights.get(ordinal));
				if(exposure.changed(ordinal)) highRenderer.setExposure(exposure.get(ordinal));
				if(environmentMap.changed(ordinal)) highRenderer.setEnvironmentMap(environmentMap.get(ordinal));
				if(environmentMapCap.changed(ordinal)) highRenderer.setEnvironmentMapCap(environmentMapCap.get(ordinal));
			break;
			
			case Medium:
			case Low: LOW.bind();
				LowForwardRenderer lowRenderer = (LowForwardRenderer) LOW.getRenderer();
				ordinal = RenderLevel.Low.ordinal();
				
				if(lights.changed(ordinal)) lowRenderer.setLights(lights.get(ordinal));
				if(exposure.changed(ordinal)) lowRenderer.setExposure(exposure.get(ordinal));
			break;
			
			default: throw new IllegalArgumentException(level + " is not a Valid RenderLevel");
		}
	}
	
	protected Collection<IRenderDependency> getDependencies(RenderLevel level) {
		switch(level) {
			case High: return reflectionProbes;
			
			case Medium: case Low: 
				return null;
			
			default: throw new IllegalArgumentException(level + " is not a Valid RenderLevel");
		}
	}
	
	public float getZLevel(Camera camera) { return -10000; }
}
