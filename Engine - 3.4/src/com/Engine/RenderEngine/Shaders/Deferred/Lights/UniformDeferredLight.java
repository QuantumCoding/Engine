package com.Engine.RenderEngine.Shaders.Deferred.Lights;

import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformStruct;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformVec3;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformVec4;

public class UniformDeferredLight extends UniformStruct<Light> {
	public UniformDeferredLight(String name) { super(name); }

	@Uniform UniformVec4 color;			// xyz == rgb, w = type
	@Uniform UniformVec3 position;
	@Uniform UniformVec4 attenuation;	// xyz == attenuation, w = radius
	
	@Uniform UniformVec4 direction;		// xyz == direction, w = angle
//	@Uniform UniformFloat radius;
	
	public void load(Light light) {
		if(light == null) {
			color.load(0, 0, 0, -1);
			position.load(0, 0, 0);
			attenuation.load(1, 0, 0, 0);
//			radius.load(0f);
			
		} else {
			color.load(light.getColor(), light.type());
			
			switch(light.type()) {
				case Light.SPOT_LIGHT: 
					SpotLight spot = (SpotLight) light;
					direction.load(spot.getDirection(), spot.getAngle());
					
				case Light.POINT_LIGHT: 
					PointLight point = (PointLight) light;
					
					position.load(point.getPosition());
					attenuation.load(point.getAttenuation(), point.getRadius());
				break;

				case Light.DIRECTION_LIGHT: 
					DirectionalLight direct = (DirectionalLight) light;

					attenuation.load(0, 0, 0, direct.getRadius());
					direction.load(direct.getDirection(), 0);
				break;
			}
		}
	}
}
