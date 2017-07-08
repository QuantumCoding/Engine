package com.Engine.RenderEngine.Shaders.Default;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Lights.Light;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformFloat;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformTexture;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformVec2;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformVec3;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class DefaultShader extends Shader {
	private static final String VERTEX_SHADER_LOC = "DefaultShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "DefaultShader.fsh";

	private static final int LIGHT_COUNT = 4;
	
	@Uniform private UniformVec3 skyColor;
	@Uniform private UniformFloat fogDensity;
	@Uniform private UniformFloat fogGradient;

	@Uniform(size=LIGHT_COUNT) private UniformVec3 lightPosition[];
	@Uniform(size=LIGHT_COUNT) private UniformVec3 lightColor[];
	@Uniform(size=LIGHT_COUNT) private UniformVec3 lightAttenuation[];
	@Uniform private UniformFloat shineDamper;
	@Uniform private UniformFloat reflectivity;

	@Uniform private UniformFloat numberOfRows;
	@Uniform private UniformVec2 offset;
	
	@Uniform private UniformMat4 transformationMatrix;

	@Uniform private UniformMat4 projectionMatrix;
	@Uniform private UniformMat4 viewMatrix;
	
	@Uniform private UniformTexture texture0;
	@Uniform private UniformFloat time;
	
	public DefaultShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, DefaultRenderer.class);
	}

	@Override
	protected void bindAttributies() {
		bind();
		
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");
		super.bindAttribute(ATTRIBUTE_LOC_TEXCOORDS, "texCoord");
		super.bindAttribute(ATTRIBUTE_LOC_NORMALS,   "normal");
	}
	
	public void loadTransformationMatrix(Matrix4f transformationMatrix) {
		this.transformationMatrix.load(transformationMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		this.projectionMatrix.load(projectionMatrix);
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix) {
		this.viewMatrix.load(viewMatrix);
	}
	
	public void loadFogValues(float density, float gradient) {
		this.fogDensity.load(density);
		this.fogGradient.load(gradient);
	}
	
	public void loadSkyColor(Vector3f skyColor) {
		this.skyColor.load(skyColor);
	}
	
	public void loadLights(List<Light> lights) {	
		for(int i = 0; i < LIGHT_COUNT; i ++) {
			if(i < lights.size()) {
				this.lightPosition[i].load(lights.get(i).getPosition());
				this.lightColor[i].load(lights.get(i).getColor());
				this.lightAttenuation[i].load(lights.get(i).getAttenuation());
			} else {
				this.lightPosition[i].load(new Vector3f());
				this.lightColor[i].load(new Vector3f());		
				this.lightAttenuation[i].load(new Vector3f(1, 0, 0));		
			}
		}
	}
	
	public void loadLights(Light... lights) {	
		for(int i = 0; i < LIGHT_COUNT; i ++) {
			if(i < lights.length) {
				this.lightPosition[i].load(lights[i].getPosition());
				this.lightColor[i].load(lights[i].getColor());
				this.lightAttenuation[i].load(lights[i].getAttenuation());
			} else {
				this.lightPosition[i].load(new Vector3f());
				this.lightColor[i].load(new Vector3f());		
				this.lightAttenuation[i].load(new Vector3f(1, 0, 0));		
			}
		}
	}
	
	public void loadShineVariables(float damper, float reflectivity) {
		this.shineDamper.load(damper);
		this.reflectivity.load(reflectivity);
	}
	
	public void loadTextureAtlasIndex(float numberOfRows, Vector2f offset) {
		this.numberOfRows.load(numberOfRows);
		this.offset.load(offset);
	}
}