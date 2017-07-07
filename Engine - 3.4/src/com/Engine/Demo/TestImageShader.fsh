#version 330

in vec2 texCoords;
out vec4 colour;

uniform sampler2D texture0;

void main(void) {
	colour = texture(texture0, texCoords);
}