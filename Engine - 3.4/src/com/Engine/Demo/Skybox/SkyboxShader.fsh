#version 400

in vec3 texCoord;
out vec4 skyColour;

uniform samplerCube skyTexture;

void main(void) {
	skyColour = texture(skyTexture, vec3(texCoord));
}