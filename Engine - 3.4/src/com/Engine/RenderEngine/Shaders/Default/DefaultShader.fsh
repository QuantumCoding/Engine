#version 400 core
#define LIGHT_COUNT 4

in vec2 texCoordPass;
in vec3 surfaceNormal;

in vec3 toLightVector[LIGHT_COUNT];
in vec3 toCameraVector;
in float visibility;

out vec4 color_out;

uniform sampler2D texture0;

struct Light {
	vec3 color;
	vec3 psoition;
	vec3 attenuation;
};

uniform Light lights[LIGHT_COUNT];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

void main(void) {
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitVectorToCamera = normalize(toCameraVector);
	
	vec3 totalDiffuse = vec3(0);
	vec3 totalSpecular = vec3(0);
	
	for(int i = 0; i < LIGHT_COUNT; i ++) {
		float distanceToLight = length(toLightVector[i]);
		float attFactor = lights[i].attenuation.x + (lights[i].attenuation.y * distanceToLight) + (lights[i].attenuation.z * distanceToLight * distanceToLight);
		
		vec3 unitLightVector = normalize(toLightVector[i]);		
		float nDotl = dot(unitNormal, unitLightVector);
		float brightness = max(nDotl, 0.0);
		
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor, 0.0);
		float dampedFactor = pow(specularFactor, shineDamper);
		
		totalDiffuse = totalDiffuse + (brightness * lights[i].color) / attFactor;
		totalSpecular = totalSpecular + (dampedFactor * reflectivity * lights[i].color) / attFactor;
	}
	
	totalDiffuse = max(totalDiffuse, 0.1);
	
	color_out = vec4(totalDiffuse, 1) * texture(texture0, texCoordPass) + vec4(totalSpecular, 1);// * texture(texture0, texCoordPass)
	color_out = mix(vec4(skyColor, 1.0), color_out, visibility);
}