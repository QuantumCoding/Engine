#version 400 core

in vec2 position;

out vec2 texCoord;

uniform mat4 transformationMatrix;
uniform mat4 othographicMatrix;

void main(void) {
	gl_Position = othographicMatrix * transformationMatrix * vec4(position, 0.0, 1.0);
	
	texCoord = position;
	texCoord.y = 1 - texCoord.y;
}