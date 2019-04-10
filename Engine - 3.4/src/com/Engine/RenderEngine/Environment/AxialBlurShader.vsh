#version 330 core
#define BLUR_SAMPLES 11

in vec3 position;
out vec3 sampleCoords[BLUR_SAMPLES];

uniform mat3 forwardRot;
uniform mat3 backwardRot;

uniform mat4 faceRotation;

void main(void) {
	gl_Position = faceRotation * vec4(position, 1);
	sampleCoords[5] = position;
	
	sampleCoords[ 6] = forwardRot * sampleCoords[5];
	sampleCoords[ 7] = forwardRot * sampleCoords[6];
	sampleCoords[ 8] = forwardRot * sampleCoords[7];
	sampleCoords[ 9] = forwardRot * sampleCoords[8];
	sampleCoords[10] = forwardRot * sampleCoords[9];
	
	sampleCoords[4] = backwardRot * sampleCoords[5];
	sampleCoords[3] = backwardRot * sampleCoords[4];
	sampleCoords[2] = backwardRot * sampleCoords[3];
	sampleCoords[1] = backwardRot * sampleCoords[2];
	sampleCoords[0] = backwardRot * sampleCoords[1];
	
	//int midPoint = int(floor(BLUR_SAMPLES / 2.0));
	//sampleCoords[midPoint] = baseVector;
	
	//for(int i = midPoint + 1; i < BLUR_SAMPLES; i ++)
	//	sampleCoords[i] = forwardRot * sampleCoords[i - 1];
		
	//for(int i = midPoint - 1; i >= 0; i --)
	//	sampleCoords[i] = backwardRot * sampleCoords[i + 1];
}