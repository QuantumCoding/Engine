#version 330 core

in vec3 fragWorldPos;
in vec2 texCoord;

in mat3 tanToWorld;

out vec4 position;
out vec4 normal;
out vec4 albedo;
out vec4 pbrData;

uniform int environmentLayer;

uniform sampler2D albedoMap;
uniform sampler2D normalMap;
uniform sampler2D occlusionMap;
uniform sampler2D metalnessMap;
uniform sampler2D roughnessMap;

void main(void) {
	vec4 albedoSample = texture(albedoMap, texCoord);
	if(albedoSample.a < .5) discard;					// Cannot have transparancy anyways
	
	vec3 normalTangentSpace = texture(normalMap, texCoord).xyz * 2 - 1; 
	normal = vec4(tanToWorld * normalTangentSpace, 1);	// Convert to World Space
	normal.w = environmentLayer;
	
	position = vec4(fragWorldPos, 1 - gl_FragCoord.z);
	
	pbrData.r = texture(occlusionMap, texCoord).r;
	pbrData.g = texture(metalnessMap, texCoord).r;
	pbrData.b = texture(roughnessMap, texCoord).r;
	pbrData.a = 1;
	
	albedo = vec4(albedoSample.rgb, 1);
}