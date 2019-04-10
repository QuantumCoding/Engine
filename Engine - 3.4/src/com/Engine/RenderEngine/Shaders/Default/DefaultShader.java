package com.Engine.RenderEngine.Shaders.Default;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformFloat;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformStruct;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformTexture;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformVec2;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformVec3;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class DefaultShader extends Shader {
	private static final String VERTEX_SHADER_LOC = "DefaultShader.vsh";
	private static final String FRAGMENT_SHADER_LOC = "DefaultShader.fsh";

	private static final int LIGHT_COUNT = 4;
	
	public static class UniformLight extends UniformStruct<Light> {
		public UniformLight(String name) { super(name); }

		@Uniform UniformVec3 color;
		@Uniform UniformVec3 position;
		@Uniform UniformVec3 attenuation;
		
		public void load(Light light) {
			if(light  == null) {
				color.load(new Vector3f());
				position.load(new Vector3f());
				attenuation.load(new Vector3f(1, 0, 0));
			} else {
				color.load(light.getColor());
				position.load(light.getPosition());
				attenuation.load(light.getAttenuation());
			}
		}
	}
	
	@Uniform UniformVec3 skyColor;
	@Uniform UniformFloat fogDensity;
	@Uniform UniformFloat fogGradient;

	@Uniform(size=LIGHT_COUNT) UniformLight lights[];
	@Uniform UniformFloat shineDamper;
	@Uniform UniformFloat reflectivity;

	@Uniform UniformFloat numberOfRows;
	@Uniform UniformVec2 offset;
	
	@Uniform UniformMat4 transformationMatrix;

	@Uniform UniformMat4 projectionMatrix;
	@Uniform UniformMat4 viewMatrix;
	
	@Uniform UniformTexture texture0;
	@Uniform UniformFloat time;
	
	public DefaultShader() {
		super(VERTEX_SHADER_LOC, FRAGMENT_SHADER_LOC, DefaultRenderer.class);
	}

	@Override
	protected void bindAttributies() {
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
		for(int i = 0; i < LIGHT_COUNT; i ++) this.lights[i].load(i < lights.size() ? lights.get(i) : null);
	}
	
	public void loadLights(Light... lights) {	
		for(int i = 0; i < LIGHT_COUNT; i ++) this.lights[i].load(i < lights.length ? lights[i] : null);
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