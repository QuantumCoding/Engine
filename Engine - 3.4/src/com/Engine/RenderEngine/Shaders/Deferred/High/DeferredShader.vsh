#version 330

in vec3 position;
in vec2 texCoordIN;
in vec3 normal;
in vec3 tangent;

out vec3 fragWorldPos;
out vec2 texCoord;

out mat3 tanToWorld;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void) {
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	vec4 positionRelativeToCam = viewMatrix * worldPosition;
	gl_Position = projectionMatrix * positionRelativeToCam;
	
	vec3 norm = normalize((transformationMatrix * vec4(normal, 0.0)).xyz);
	vec3 tang = normalize((transformationMatrix * vec4(tangent, 0.0)).xyz);
	vec3 bitang = cross(norm, tang);
	
	tanToWorld = mat3(tang, bitang, norm);
	
	fragWorldPos = worldPosition.xyz;
	texCoord = texCoordIN;
}