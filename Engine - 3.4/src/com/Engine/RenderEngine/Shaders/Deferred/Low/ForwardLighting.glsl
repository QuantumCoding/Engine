#define MAX_LIGHTS 1

struct Light {
	vec4 attenuation;	// xyz == attenuation, w = radius
	vec3 position;
	vec4 color;			// xyz == rgb, w = type

	vec4 direction;		// xyz == direction, w = angle
};

uniform Light lights[MAX_LIGHTS];
uniform int lightCount;

vec3 perVertexLighting(Light light, vec3 cameraPos, vec3 vertexPos, vec3 normal) { // TODO: Different types in Lights
	vec3 unitNormal = normalize(normal);
	vec3 unitVectorToCamera = normalize(cameraPos - vertexPos);
	vec3 toLight = light.position - vertexPos;
	
	// Attinuation
	float lightDist = length(toLight);
	//float attFactor = light.attenuation.x + (light.attenuation.y * lightDist) + (light.attenuation.z * lightDist * lightDist);
	float attFactor = smoothstep(0, 1, 1 - lightDist / light.attenuation.x);
	
	// Diffuse Calculation
	vec3 unitLightVector = normalize(toLight);		
	float nDotl = dot(unitNormal, unitLightVector);
	float brightness = max(nDotl, 0.0);
	
	vec3 diffuse = brightness * light.color.rgb * attFactor;
	
	// Ambiant Lighting
	diffuse = max(diffuse, 0.1);
	return diffuse;
}