#version 400 core

in vec2 position;
in vec4 texCoord_in;

out vec4 texCoord;

uniform mat4 transformationMatrix;
uniform mat4 orthographicMatrix;

uniform float lineHeight;

void main(void) {
	vec2 filpedPos = vec2(position.x, -position.y - lineHeight);
	gl_Position = orthographicMatrix * transformationMatrix * vec4(filpedPos, 0.0, 1.0);
	texCoord = texCoord_in;
}