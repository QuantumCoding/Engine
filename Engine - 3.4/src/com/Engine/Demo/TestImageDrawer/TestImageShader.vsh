#version 330

in vec2 position;
out vec2 texCoords;

void main(void) {
	gl_Position = vec4(position / 2 + vec2(-.5, .5), 0, 1);
	texCoords = position * .5 + .5;
}