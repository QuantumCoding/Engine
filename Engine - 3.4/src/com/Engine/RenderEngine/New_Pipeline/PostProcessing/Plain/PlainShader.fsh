#version 150

in vec2 texCoord;
out vec4 out_colour;

uniform sampler2D texture0;

void main(void) {
	out_colour = texture(texture0, texCoord);
}