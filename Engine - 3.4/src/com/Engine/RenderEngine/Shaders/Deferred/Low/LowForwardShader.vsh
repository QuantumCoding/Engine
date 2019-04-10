#version 330

#import /com/Engine/RenderEngine/Shaders/Deferred/Low/ForwardLighting.glsl

in vec3 position;
in vec2 texCoordIN;
in vec3 normal;

out vec2 texCoord;
out vec3 lighting;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void) {
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	vec4 positionRelativeToCam = viewMatrix * worldPosition;
	gl_Position = projectionMatrix * positionRelativeToCam;
	
	texCoord = texCoordIN;
	
	vec3 cameraPos = (inverse(viewMatrix) * vec4(0, 0, 0, 1)).xyz;
	vec3 surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
	
	lighting = vec3(0);
	for(int i = 0; i < lightCount; i ++) {
		lighting += perVertexLighting(lights[i], cameraPos, worldPosition.xyz, surfaceNormal);
	}
}