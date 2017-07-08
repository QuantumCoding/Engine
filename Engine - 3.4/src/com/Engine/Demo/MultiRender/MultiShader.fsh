#version 400 core

in vec2 textureCoord;

out vec4 out_colour;

uniform sampler2D texture0;
uniform sampler2D texture1;

const vec4 FLOWER_COLOUR = vec4(0, .75, 1, 1);

void main(void) {
	vec4 sample0 = texture(texture0, textureCoord);
	vec4 sample1 = texture(texture1, textureCoord);

	out_colour = mix(sample0, sample1 * FLOWER_COLOUR, sample1.r / 4);
}