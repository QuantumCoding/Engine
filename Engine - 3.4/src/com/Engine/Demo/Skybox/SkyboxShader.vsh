#version 330 core

in vec3 position;
out vec3 texCoord;

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(void) {
	gl_Position = projectionMatrix * viewMatrix * vec4(position, 1);
	texCoord = position;
}