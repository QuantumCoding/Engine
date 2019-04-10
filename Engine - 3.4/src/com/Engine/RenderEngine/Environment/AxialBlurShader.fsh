#version 330 core
#define BLUR_SAMPLES 11

in vec3 sampleCoords[BLUR_SAMPLES];

out vec4 blur_L1_out;
out vec4 blur_L2_out;
out vec4 blur_L3_out;
out vec4 blur_L4_out;

uniform samplerCube texture0;

void main(void) {
	vec4 texureSamples[BLUR_SAMPLES];
	for(int i = 0; i < BLUR_SAMPLES; i ++) {
		texureSamples[i] = texture(texture0, sampleCoords[i]);
	}
	
	blur_L1_out = vec4(0);
    blur_L1_out += texureSamples[3]  * 0.153388;
    blur_L1_out += texureSamples[4]  * 0.221461;
    blur_L1_out += texureSamples[5]  * 0.250301;
    blur_L1_out += texureSamples[6]  * 0.221461;
    blur_L1_out += texureSamples[7]  * 0.153388;
	
	
	blur_L2_out = vec4(0);
    blur_L2_out += texureSamples[2]  * 0.071303;
    blur_L2_out += texureSamples[3]  * 0.131514;
    blur_L2_out += texureSamples[4]  * 0.189879;
    blur_L2_out += texureSamples[5]  * 0.214607;
    blur_L2_out += texureSamples[6]  * 0.189879;
    blur_L2_out += texureSamples[7]  * 0.131514;
    blur_L2_out += texureSamples[8]  * 0.071303;
	
	blur_L3_out = vec4(0);
    blur_L3_out += texureSamples[1]  * 0.028532;
    blur_L3_out += texureSamples[2]  * 0.067234;
    blur_L3_out += texureSamples[3]  * 0.124009;
    blur_L3_out += texureSamples[4]  * 0.179044;
    blur_L3_out += texureSamples[5]  * 0.202360;
    blur_L3_out += texureSamples[6]  * 0.179044;
    blur_L3_out += texureSamples[7]  * 0.124009;
    blur_L3_out += texureSamples[8]  * 0.067234;
    blur_L3_out += texureSamples[9]  * 0.028532;
	
	blur_L4_out = vec4(0);
	blur_L4_out += texureSamples[0]  * 0.0093;
    blur_L4_out += texureSamples[1]  * 0.028002;
    blur_L4_out += texureSamples[2]  * 0.065984;
    blur_L4_out += texureSamples[3]  * 0.121703;
    blur_L4_out += texureSamples[4]  * 0.175713;
    blur_L4_out += texureSamples[5]  * 0.198596;
    blur_L4_out += texureSamples[6]  * 0.175713;
    blur_L4_out += texureSamples[7]  * 0.121703;
    blur_L4_out += texureSamples[8]  * 0.065984;
    blur_L4_out += texureSamples[9]  * 0.028002;
    blur_L4_out += texureSamples[10] * 0.0093;
    
    //blur_L1_out = texureSamples[5];//vec4(sampleCoords[5], 1);//
	//blur_L2_out = texureSamples[6];//vec4(sampleCoords[6], 1);
	//blur_L3_out = vec4(sampleCoords[7], 1);
	//blur_L4_out = vec4(sampleCoords[8], 1);
}