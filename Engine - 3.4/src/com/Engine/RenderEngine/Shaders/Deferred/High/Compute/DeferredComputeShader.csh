#version 430 core

#define MAX_LIGHTS_PER_TILE 1024//64

#define WORK_GROUP_SIZE 16
#define GROUP_THREAD_COUNT WORK_GROUP_SIZE * WORK_GROUP_SIZE
#import /com/Engine/RenderEngine/Shaders/Deferred/High/LightingCalculations.glsl

layout (local_size_x = WORK_GROUP_SIZE, local_size_y = WORK_GROUP_SIZE) in;

uniform float exposure;
uniform vec3 cameraPos;

uniform sampler2D positionMap;
uniform sampler2D normalMap;
uniform sampler2D albedoMap;
uniform sampler2D pbrDataMap;

uniform samplerCubeArray environmentMap; // samplerCube
uniform int environmentMapSudoLayerCap;

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform vec2 framebufferDim;

uniform writeonly image2D outTexture;

shared uint minDepth;
shared uint maxDepth;

shared uint lightIndex[MAX_LIGHTS];
shared uint lightCount;

void main(void) {
	if(gl_LocalInvocationIndex == 0) {
		minDepth = 0x7F7FFFFF;
		maxDepth = 0;
		
		lightCount = 0;
	}
	
	barrier();

	ivec2 pixelPos = ivec2(gl_GlobalInvocationID.xy);
	float d = 1 - texture(positionMap, pixelPos / framebufferDim).w;
	
	uint depth = floatBitsToUint(d);
	
	atomicMin(minDepth, depth);
	atomicMax(maxDepth, depth);
	
	barrier();
// ---------------------------------------- ---------------------- --------------------------------------------------- \\
	barrier(); // ------------------------- Sync Depth Information --------------------------------------------------- \\
// ---------------------------------------- ---------------------- --------------------------------------------------- \\
	
	float minDepthZ = uintBitsToFloat(minDepth);
	float maxDepthZ = uintBitsToFloat(maxDepth);
	
	minDepthZ = minDepthZ * -2 + 1;
	minDepthZ = projectionMatrix[3][2] / (minDepthZ - projectionMatrix[2][2]);
	
	maxDepthZ = maxDepthZ * -2 + 1;
	maxDepthZ = projectionMatrix[3][2] / (maxDepthZ - projectionMatrix[2][2]);
		
	vec2 tileScale = framebufferDim / (2 * WORK_GROUP_SIZE);
	vec2 tileBias = tileScale - gl_WorkGroupID.xy;
	
	// Now work out composite projection matrix
	// Relevant matrix columns for this tile frusta
	vec4 colX = vec4(-projectionMatrix[0][0] * tileScale.x,  0, tileBias.x, 0);
	vec4 colY = vec4(0, -projectionMatrix[1][1] * tileScale.y, tileBias.y, 0);
	vec4 colW = vec4(0, 0, 1, 0);
	
	// Derive frustum planes
	vec4 frustumPlanes[6];
	
	// Sides
	frustumPlanes[0] = colW - colX;
	frustumPlanes[1] = colW + colX;
	frustumPlanes[2] = colW - colY;
	frustumPlanes[3] = colW + colY;
	
	// Near/far
	frustumPlanes[4] = vec4(0, 0, -1,  minDepthZ);
	frustumPlanes[5] = vec4(0, 0,  1, -maxDepthZ);
	
	// Normalize frustum planes (near/far already normalized)
	frustumPlanes[0] /= length(frustumPlanes[0].xyz);
	frustumPlanes[1] /= length(frustumPlanes[1].xyz);
	frustumPlanes[2] /= length(frustumPlanes[2].xyz);
	frustumPlanes[3] /= length(frustumPlanes[3].xyz);

// ---------------------------------------- ---------------------- --------------------------------------------------- \\
	barrier(); // ------------------------- Complete Frustum Calc. --------------------------------------------------- \\
// ---------------------------------------- ---------------------- --------------------------------------------------- \\

	uint count = min(numActiveLights, MAX_LIGHTS);
	for(uint index = gl_LocalInvocationIndex; index < count && lightCount < MAX_LIGHTS_PER_TILE; index += GROUP_THREAD_COUNT) {
		uint testLightIndex = min(index, numActiveLights);
		
		Light light = lights[testLightIndex];
		vec4 pos = viewMatrix * vec4(light.position, 1); // Maybe do on CPU before passing In,
		pos.w = 1;								  		 // Wont have to do it for every group
		
		bool inFrustum = true;
		
		for(uint i = 0; i < 6; i ++) {
			float dist = dot(frustumPlanes[i], pos);
			inFrustum = inFrustum && -light.attenuation.w <= dist; // light.attenuation.w == radius
		}
		
		if(inFrustum) {
			uint id = atomicAdd(lightCount, 1);
			lightIndex[id] = testLightIndex;
		}
	}
	
	barrier();
// ---------------------------------------- ----------------------- --------------------------------------------------- \\
	barrier(); // ------------------------- Sync Calculation Lights --------------------------------------------------- \\
// ---------------------------------------- ----------------------- --------------------------------------------------- \\
	
	vec2 texCoord = pixelPos / framebufferDim;
	vec3 colourSum = vec3(0);

	//float maxLOD = log2(textureSize(environmentMap, 0).x);
	float maxLOD = textureQueryLevels(environmentMap);
	
	float environmentCount = textureSize(environmentMap, 0).z;
	environmentCount = min(environmentCount, environmentMapSudoLayerCap);
	
	if(pixelPos.x <= framebufferDim.x && pixelPos.y <= framebufferDim.y) {
	
		vec4 pbrData = texture(pbrDataMap, texCoord);
		if(pbrData.a > .5 && d > 0.00001) {
		
			vec4 normalMapData = texture(normalMap, texCoord);
			float environmentLayer = normalMapData.w;
			environmentLayer = min(environmentLayer, environmentCount);
			
			vec3 worldPos = texture(positionMap, texCoord).rgb;
			vec3 albedo = texture(albedoMap, texCoord).rgb;
			vec3 normal = normalMapData.xyz;
			
			float occlusion = pbrData.r;
			float metallic  = pbrData.g;
			float roughness = pbrData.b;
			
			vec3 toView = normalize(cameraPos - worldPos);
			
			vec3 F0 = vec3(0.04); 
			F0 = mix(F0, albedo, metallic);
			           
			// reflectance equation
			vec3 luminanceOut = vec3(0.0);
			for(uint i = 0; i < lightCount; i ++) { // lightCount lightIndex[i] numActiveLights
				luminanceOut += calculateLightInfluence(lights[lightIndex[i]], worldPos, toView, normal, albedo, metallic, roughness, F0);
			}

			vec3 ambient = vec3(0.03) * albedo * occlusion;
			vec3 color = ambient + luminanceOut;
			
			// HDR Correction
			//color = color / (color + vec3(1.0));
			color = vec3(1.0) - exp(-color * exposure);
			color = pow(color, vec3(1.0 / 2.2)); 
			
			vec3 reflectDir	   = reflect(-toView, normal); //refract(-toView, normal, 1 / 1.42);
			vec3 reflectColour = textureLod(environmentMap, vec4(reflectDir, environmentLayer), maxLOD * roughness).rgb;
			
			vec3  fersnel = max(fresnelApprox(max(dot(normal, toView), 0.0), vec3(0.04)), 0);
			
			colourSum += mix(color, reflectColour, min(metallic * .35 + (1 - roughness) * .40 + fersnel * .25, 1)); 
			//colourSum = luminanceOut;
			//colourSum = vec3(environmentLayer * 1000);
		}
	}
	
// ---------------------------------------- ------------------------- --------------------------------------------------- \\
	barrier(); // ------------------------- Sync Lighting Information --------------------------------------------------- \\
// ---------------------------------------- ------------------------- --------------------------------------------------- \\
	
	//if(gl_LocalInvocationID.x == 0 || gl_LocalInvocationID.y == 0 || gl_LocalInvocationID.x == 16 || gl_LocalInvocationID.y == 16)
	//	imageStore(outTexture, pixelPos, vec4(.2, .2, .2, 1));
	//else

	imageStore(outTexture, pixelPos, vec4(colourSum, 1.0));
	//imageStore(outTexture, pixelPos, vec4(vec3(reflectionSizeRatio), 1));
	//imageStore(outTexture, pixelPos, vec4(vec3(d), 1));
	//imageStore(outTexture, pixelPos, vec4(vec3(minDepthZ), 1));
	//imageStore(outTexture, pixelPos, vec4(vec3(lightCount), 1));
	//imageStore(outTexture, pixelPos, vec4(vec3(fudgeYou / float(16*16 * 2)), 1));
	//imageStore(outTexture, pixelPos, vec4(vec3(distLight), 1));
	//imageStore(outTexture, pixelPos, vec4(vec3((maxDepthZ - minDepthZ) / 2 + .5), 1));
	//imageStore(outTexture, pixelPos, vec4(texture(normalMap, texCoord).rgb, 1));
	//imageStore(outTexture, pixelPos, vec4(vec3(numActiveLights), 1));
	//imageStore(outTexture, pixelPos, vec4(vec3(lights[0].position), 1));
	//imageStore(outTexture, pixelPos, vec4(texture(albedoMap, texCoord).rgb, 1.0));
	
	//vec2 tilePos = vec2(gl_WorkGroupID.xy * gl_WorkGroupSize.xy) / framebufferDim;
	//imageStore(outTexture, pixelPos, vec4(vec2(tilePos.xy), 0.0f, 1.0f));
}