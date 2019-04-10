#define PI 3.1415926535897932384626433832795

#define MAX_LIGHTS 16

struct Light {
	vec4 attenuation;	// xyz == attenuation, w = radius
	vec3 position;
	vec4 color;			// xyz == rgb, w = type

	vec4 direction;		// xyz == direction, w = angle
};

uniform Lights {
	int numActiveLights;
	Light lights[MAX_LIGHTS];
};

struct MaterialData {
	vec3 albedo;
	float metallic;
	float roughness;
	vec3 F0;
};

const int TYPE_SPOT 		= 0;
const int TYPE_POINT 		= 1;
const int TYPE_DIRECTIONAL  = 2;

float microfacetAlignmentApprox(vec3 normal, vec3 halfWay, float roughness) { // Trowbridge-Reitz GGX
	float surfaceRoughness = roughness * roughness;
	float surfaceRoughSq = surfaceRoughness * surfaceRoughness;
	
	float normHalfAlign = max(dot(normal, halfWay), 0.0);
	float alignSq = normHalfAlign * normHalfAlign;
		
	float denom = (alignSq * (surfaceRoughSq - 1.0) + 1.0);
	denom = PI * denom * denom;
		
	return surfaceRoughSq / denom;
}

float overshadowFromAlignment(float alignment, float k) {
	float denom = alignment * (1.0 - k) + k;
	return alignment / denom;
}

float microfacetOvershadowApprox(vec3 normal, vec3 toView, vec3 toLight, float roughness) { // Smith's Schlick-GGX
//	Direct Lights
	float k = roughness + 1;
	k = k * k / 8;
	
//	IBL
//	float k = roughness;
//	k = k * k / 2;

	float normViewAlign  = max(dot(normal, toView), 0.0);
	float normLightAlign = max(dot(normal, toLight), 0.0);
	
	float viewOvershadow  = overshadowFromAlignment(normViewAlign, k);
	float lightOvershadow = overshadowFromAlignment(normLightAlign, k);
		
	return viewOvershadow * lightOvershadow;
}

vec3 fresnelApprox(float cosTheta, vec3 F0) { // Fresnel-Schlick
	return F0 + (vec3(1.0) - F0) * pow(1.0 - cosTheta, 5.0);
}

vec3 preformLightingCalculation(vec3 toLight, vec3 toView, vec3 worldPos, vec3 radiance, vec3 normal, MaterialData material) {
	vec3 halfWay = normalize(toView + toLight);

	// cook-torrance brdf
	float microfacets 	= microfacetAlignmentApprox(normal, halfWay, material.roughness);        
	float overshadows   = microfacetOvershadowApprox(normal, toView, toLight, material.roughness);      
	vec3  fersnel		= fresnelApprox(max(dot(halfWay, toView), 0.0), material.F0);       
	
	vec3 specularPerc = fersnel;
	vec3 diffusedPerc = vec3(1.0) - specularPerc;
	diffusedPerc *= 1.0 - material.metallic;	  
	
	vec3  numerator	  = microfacets * overshadows * fersnel;
	float denominator = 4.0 * max(dot(normal, toView), 0.0) * max(dot(normal, toLight), 0.0);
	vec3  specular	  = numerator / max(denominator, 0.001);  
	    
	// add to outgoing radiance luminance
	float normLightAlign = max(dot(normal, toLight), 0.0);                
	return (diffusedPerc * material.albedo / PI + specular) * radiance * normLightAlign; 
}

vec3 pointLight(Light light, MaterialData material, vec3 worldPos, vec3 toView, vec3 normal) {
	vec3 toLight = normalize(light.position - worldPos);
	
	float distToLight = length(light.position - worldPos);
	//float attFactor = 1.0 / (distToLight * distToLight);
	//float attFactor = 1.0 / (light.attenuation.x + (light.attenuation.y * distToLight) + (light.attenuation.z * distToLight * distToLight));
	//float attFactor = 1.0 / exp((light.attenuation.y * distToLight - pow(distToLight, light.attenuation.x)) / light.attenuation.z);
	float attFactor = smoothstep(0, 1, 1 - distToLight / light.attenuation.x);
	
	vec3 radiance = light.color.rgb * attFactor;
	return preformLightingCalculation(toLight, toView, worldPos, radiance, normal, material);	
}

vec3 directionalLight(Light light, MaterialData material, vec3 worldPos, vec3 toView, vec3 normal) {
	vec3 toLight = light.direction.xyz;
	vec3 radiance = light.color.rgb;
	
	return preformLightingCalculation(toLight, toView, worldPos, radiance, normal, material);	
}

vec3 spotLight(Light light, MaterialData material, vec3 worldPos, vec3 toView, vec3 normal) {
	// calculate per-light radiance						 
	vec3 toLight = normalize(light.position - worldPos);
	
	// -----
	
	float falloff = light.attenuation.y;
	float angle = acos(dot(toLight, normalize(light.direction.xyz)));
	if(angle > light.direction.w + falloff) {
		return vec3(0);
	}
	
	float t = clamp(angle - light.direction.w, 0, falloff);
    float attFactor = 2 / (exp(light.attenuation.z * t*t) + 1);
	
	//float distToLight = length(light.position - worldPos);
	//float attFactor = smoothstep(0, 1, clamp((angle - light.direction.w) / falloff, 0, 1));//smoothstep(0, 1, 1 - distToLight / light.attenuation.x);
	
	//float attFactor = smoothstep(0, 1, 1 - distToLight / light.attenuation.x);
	//float angle = acos(dot(-toLight, normalize(light.direction.xyz)));
	//attFactor = mix(attFactor, 0, float(angle > light.direction.w));
	
	// -----
	
	vec3 radiance = light.color.rgb * attFactor;
	return preformLightingCalculation(toLight, toView, worldPos, radiance, normal, material);	
}

vec3 calculateLightInfluence(Light light, vec3 worldPos, vec3 toView, vec3 normal, vec3 albedo, float metallic, float roughness, vec3 F0) {
	MaterialData material = MaterialData(albedo, metallic, roughness, F0);
	int type = int(light.color.w);
	
	switch(type) {
		case TYPE_SPOT: return spotLight(light, material, worldPos, toView, normal);
		case TYPE_POINT: return pointLight(light, material, worldPos, toView, normal);
		case TYPE_DIRECTIONAL: return directionalLight(light, material, worldPos, toView, normal);
	}
	
	return vec3(0); // Default - Invalid Type
}