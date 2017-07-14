#version 400 core

in vec2 position;
in vec4 texCoord_in;

out vec4 texCoord;

uniform mat4 transformationMatrix;
uniform mat4 orthographicMatrix;

void main(void) {
	gl_Position = orthographicMatrix * transformationMatrix * vec4(position, 0.0, 1.0);
	texCoord = texCoord_in;
}