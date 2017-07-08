#version 400 core

in vec2 position;
in vec2 texCoord;

out vec2 textureCoord;

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;

uniform vec2 scale;

void main(void) {
	gl_Position = projectionMatrix * modelViewMatrix * vec4(position * scale, 0.0, 1.0);
	textureCoord = texCoord;
}