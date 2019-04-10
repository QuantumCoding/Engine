package com.Engine.RenderEngine.Shaders.Deferred.High.Compute;

import com.Engine.RenderEngine.Shaders.Deferred.Lights.UniformDeferredLight;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformBuffer;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformInt;

public class UniformBufferLights extends UniformBuffer {
	private static final int MAX_LIGHT_COUNT = 16;

	public @Uniform(size = MAX_LIGHT_COUNT) UniformDeferredLight lights[];
	public @Uniform UniformInt numActiveLights;
}
