#version 330 core

in vec2 texCoord;
in vec3 lighting;

out vec4 colour_out;

uniform float exposure;
uniform sampler2D albedoMap;

void main(void) {
	vec4 albedoSample = texture(albedoMap, texCoord);
	if(albedoSample.a < .5) discard;					// Cannot have transparancy anyways
	
	vec3 color = vec3(albedoSample.rgb * lighting);
	
	// HDR Correction
	//color = color / (color + vec3(1.0));
	color = vec3(1.0) - exp(-color * exposure);
	color = pow(color, vec3(1.0 / 2.2)); 
	
	colour_out = vec4(color, 1);
}