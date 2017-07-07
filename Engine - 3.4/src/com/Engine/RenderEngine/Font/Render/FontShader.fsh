#version 400 core

in vec4 texCoord;

out vec4 out_colour;

uniform sampler2DArray texture0;
uniform vec4 colour;

const float width = .53;
const float edge = .2;

void main(void) {
	vec4 textSample = texture(texture0, texCoord.xyz);
	
	float dist = 1 - textSample[int(texCoord.w)];
	float value = 1 - smoothstep(width, width + edge, dist);
	
	out_colour = colour * value;
	
	// if(texCoord.w == 0) out_colour = vec4(0, 0, 1, 1);
	// if(texCoord.w == 1) out_colour = vec4(0, 1, 0, 1);
	// if(texCoord.w == 2) out_colour = vec4(1, 0, 0, 1);
	// if(texCoord.w == 3) out_colour = vec4(1, 1, 1, 1);
	// 
	// out_colour = mix(colour * value, out_colour, .5);
}


//     GL  AWT
// red  0   2
// gre  1   1
// blu  2 	0
// alp  3 	3

//		 IND	BIT
//  GL = RGBA	ABGR
// AWT = BGRA 	ARGB