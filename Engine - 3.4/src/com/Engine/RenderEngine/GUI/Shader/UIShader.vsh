#version 400 core

in vec2 position;
out vec2 texCoord;

uniform mat4 textureMatrix;
uniform mat4 transformationMatrix;
uniform mat4 othographicMatrix;

void main(void) {
	vec4 transformable = vec4(position, 0.0, 1.0);
	gl_Position = othographicMatrix * transformationMatrix * transformable;
	texCoord = (textureMatrix * transformable).xy;
}