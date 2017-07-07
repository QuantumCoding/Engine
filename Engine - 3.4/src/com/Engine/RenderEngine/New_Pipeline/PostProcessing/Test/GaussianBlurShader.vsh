#version 150

in vec2 position;
out vec2 blurTextureCoords[11];

uniform vec2 axis;
uniform float size;

void main(void) {
	gl_Position = vec4(position, 0, 1);
	
	vec2 texCoordCenter = position * .5 + .5;
	float pixelSize = 1 / size;
	
	for(int i = -5; i <= 5; i ++) {
		blurTextureCoords[i + 5] = texCoordCenter + (pixelSize * i * axis);
	}
}