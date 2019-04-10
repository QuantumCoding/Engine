#version 330 core

in vec2 texCoord;
out vec4 out_colour;

struct Light {
	vec3 attenuation;
	vec3 position;
	vec3 color;
};

uniform vec3  cameraPos;
uniform Light lightIN;

uniform sampler2D positionMap;
uniform sampler2D normalMap;
uniform sampler2D albedoMap;
uniform sampler2D pbrDataMap;

#import /com/Engine/RenderEngine/Shaders/Deferred/LightingCalculations.glsl

void main(void) {
	vec3 worldPos = texture(positionMap, texCoord).rgb;
	vec3 albedo = texture(albedoMap, texCoord).rgb;
	vec3 normal = texture(normalMap, texCoord).xyz;
	
	vec4 pbrData = texture(pbrDataMap, texCoord);
	float occlusion = pbrData.r;
	float metallic  = pbrData.g;
	float roughness = pbrData.b;
	
	vec3 toView = normalize(cameraPos - worldPos);
	
	vec3 F0 = vec3(0.04); 
	F0 = mix(F0, albedo, metallic);
	           
	// reflectance equation
	vec3 luminanceOut = vec3(0.0);
	luminanceOut += calculateLightInfluence(lightIN, worldPos, toView, normal, albedo, metallic, roughness, F0);
	
	vec3 ambient = vec3(0.03) * albedo * occlusion;
	vec3 color = ambient + luminanceOut;
	
	// HDR Correction
	//color = color / (color + vec3(1.0));
	//color = pow(color, vec3(1.0 / 2.2));  
	
	out_colour = vec4(color, 1.0);
}  