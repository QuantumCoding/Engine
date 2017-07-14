package com.Engine.RenderEngine.GLFunctions;

import static com.Engine.RenderEngine.GLFunctions.BlendFunc.BlendMulti.One;
import static com.Engine.RenderEngine.GLFunctions.BlendFunc.BlendMulti.One_Minus_SRC_Alpha;
import static com.Engine.RenderEngine.GLFunctions.BlendFunc.BlendMulti.SRC_Alpha;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CONSTANT_ALPHA;
import static org.lwjgl.opengl.GL11.GL_CONSTANT_COLOR;
import static org.lwjgl.opengl.GL11.GL_DST_ALPHA;
import static org.lwjgl.opengl.GL11.GL_DST_COLOR;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_CONSTANT_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_CONSTANT_COLOR;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_DST_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_DST_COLOR;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_COLOR;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_COLOR;
import static org.lwjgl.opengl.GL11.GL_ZERO;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glIsEnabled;
import static org.lwjgl.opengl.GL14.GL_BLEND_COLOR;
import static org.lwjgl.opengl.GL14.GL_BLEND_DST_ALPHA;
import static org.lwjgl.opengl.GL14.GL_BLEND_DST_RGB;
import static org.lwjgl.opengl.GL14.GL_BLEND_SRC_ALPHA;
import static org.lwjgl.opengl.GL14.GL_BLEND_SRC_RGB;
import static org.lwjgl.opengl.GL14.GL_FUNC_ADD;
import static org.lwjgl.opengl.GL14.GL_FUNC_REVERSE_SUBTRACT;
import static org.lwjgl.opengl.GL14.GL_FUNC_SUBTRACT;
import static org.lwjgl.opengl.GL14.GL_MAX;
import static org.lwjgl.opengl.GL14.GL_MIN;
import static org.lwjgl.opengl.GL14.glBlendColor;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;
import static org.lwjgl.opengl.GL20.GL_BLEND_EQUATION_ALPHA;
import static org.lwjgl.opengl.GL20.GL_BLEND_EQUATION_RGB;
import static org.lwjgl.opengl.GL20.glBlendEquationSeparate;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.Engine.RenderEngine.GLFunctions.BlendFunc.BlendMulti.MultiplierSet;
import com.Engine.RenderEngine.GLFunctions.BlendFunc.BlendOperator.FunctionSet;
import com.Engine.Util.Vectors.Vector4f;

public class BlendFunc extends GL_Function {
	private static final BlendFunc ADDED_BLENDING = new BlendFunc(true).setMultipliers(SRC_Alpha, One);
	private static final BlendFunc NORMAL_BLENDING = new BlendFunc(true).setMultipliers(SRC_Alpha, One_Minus_SRC_Alpha);

	private static final FloatBuffer COLOUR_PULL = BufferUtils.createFloatBuffer(16);
	
	// http://www.andersriggelsen.dk/glblendfunc.php
	
	// Src (Source) 		what is being added
	// Dst (Destination)	what is already there
	
	// Function: Src * multi  (operation) Dst * multi
	// Example:	 Src * src.alpha   +    Dst * (1 - src.alpha)
	//			   SRC_Aplha      Add	 One_Minus_SRC_Alpha
	
	private FunctionSet function;
	private MultiplierSet multipliers;
	private Vector4f constColor;
	
	private BlendFunc() { super(); }
	private BlendFunc(boolean skip) { 
		super(skip); 
		multipliers = new MultiplierSet(One, One);
		function = new FunctionSet(BlendOperator.Add);
		constColor = new Vector4f(1);
	}
	
	public void push() {
		glBlendColor(constColor.x, constColor.y, constColor.z, constColor.w);
		function.push();
		multipliers.push();
	}

	public void pull() {
		if(COLOUR_PULL == null) return;
		
		COLOUR_PULL.clear();
		glGetFloat(GL_BLEND_COLOR, COLOUR_PULL);
		constColor = new Vector4f(COLOUR_PULL.get(), COLOUR_PULL.get(), COLOUR_PULL.get(), COLOUR_PULL.get());
		function = BlendOperator.pull();
		multipliers = BlendMulti.pull();
	}

	protected int getGLCapablity() { return GL_BLEND; }

	public BlendFunc clone() {
		BlendFunc func = new BlendFunc(true);
			func.function = this.function;
			func.multipliers = this.multipliers;
		return func;
	}

	public static boolean isEnabled() { return glIsEnabled(GL_BLEND); }
	public static void disable() { glDisable(GL_BLEND); }
	
	public static BlendFunc current() { return new BlendFunc(); }
	public static BlendFunc additive() { return ADDED_BLENDING.clone(); }
	public static BlendFunc normal() { return NORMAL_BLENDING.clone(); }
	
	public BlendFunc setMultipliers(BlendMulti src, BlendMulti dst) { return setMultipliers(src, dst, src, dst); }
	public BlendFunc setMultipliersRGB(BlendMulti src, BlendMulti dst) { return setMultipliers(src, dst, null, null); }
	public BlendFunc setMultipliersAlpha(BlendMulti src, BlendMulti dst) { return setMultipliers(null, null, src, dst); }
	
	public BlendFunc setMultipliers(BlendMulti src, BlendMulti dst, BlendMulti srcA, BlendMulti dstA) {
		if(src != null) multipliers.SRC_RGB_multi = src;	
		if(dst != null) multipliers.DST_RGB_multi = dst;
		if(srcA != null) multipliers.SRC_ALPHA_multi = srcA;	
		if(dstA != null) multipliers.DST_ALPHA_multi = dstA;
		
		return this;
	}
	
	public BlendFunc setOperator(BlendOperator op) { return setOperator(op, op); }
	public BlendFunc setOperatorRGB(BlendOperator op) { return setOperator(op, null); }
	public BlendFunc setOperatorAlpha(BlendOperator op) { return setOperator(null, op); }
	
	public BlendFunc setOperator(BlendOperator rgbOp, BlendOperator alphaOp) {
		if(rgbOp != null) function.RGB_func = rgbOp;	
		if(alphaOp != null) function.ALPHA_func = alphaOp;
		
		return this;
	}
	
	public static enum BlendMulti {
		Zero(GL_ZERO), One(GL_ONE),
		
		SRC_Color(GL_SRC_COLOR),
		SRC_Alpha(GL_SRC_ALPHA),
		One_Minus_SRC_Color(GL_ONE_MINUS_SRC_COLOR),
		One_Minus_SRC_Alpha(GL_ONE_MINUS_SRC_ALPHA),

		DST_Color(GL_DST_COLOR),
		DST_Alpha(GL_DST_ALPHA),
		One_Minus_DST_Color(GL_ONE_MINUS_DST_COLOR),
		One_Minus_DST_Alpha(GL_ONE_MINUS_DST_ALPHA),

		CONSTANT_Color(GL_CONSTANT_COLOR),
		CONSTANT_Alpha(GL_CONSTANT_ALPHA),
		One_Minus_CONSTANT_Color(GL_ONE_MINUS_CONSTANT_COLOR),
		One_Minus_CONSTANT_Alpha(GL_ONE_MINUS_CONSTANT_ALPHA);
		
		private int glValue;
		private BlendMulti(int glValue) { this.glValue = glValue; }
		public int getFunction() { return glValue; }
		
		public static BlendMulti lookUp(int glValue) {
			for(BlendMulti op : values()) if(op.glValue == glValue) return op;
			throw new IllegalArgumentException(glValue + " is not a valid BlendMultiplier");
		}
		
		static class MultiplierSet { 
			public MultiplierSet(BlendMulti src, BlendMulti dst) { this(src, dst, src, dst); }
			public MultiplierSet(
					BlendMulti srcRGB, BlendMulti dstRGB, 
					BlendMulti srcAlpha, BlendMulti dstAlpha
			) { 
				this.SRC_RGB_multi = srcRGB; 
				this.DST_RGB_multi = dstRGB;
				this.SRC_ALPHA_multi = srcAlpha; 
				this.DST_ALPHA_multi = dstAlpha;
			}
			
			BlendMulti SRC_RGB_multi, DST_RGB_multi; 
			BlendMulti SRC_ALPHA_multi, DST_ALPHA_multi;
			
			void push() { glBlendFuncSeparate(
				SRC_RGB_multi.glValue, DST_RGB_multi.glValue,
				SRC_ALPHA_multi.glValue, DST_ALPHA_multi.glValue
			);}
		}
		
		private static MultiplierSet pull() {
			return new MultiplierSet(
				lookUp(glGetInteger(GL_BLEND_SRC_RGB)),
				lookUp(glGetInteger(GL_BLEND_DST_RGB)),
				lookUp(glGetInteger(GL_BLEND_SRC_ALPHA)),
				lookUp(glGetInteger(GL_BLEND_DST_ALPHA))
			);
		}
	}
	
	public static enum BlendOperator {
		Add(GL_FUNC_ADD),
		
		Subtract(GL_FUNC_SUBTRACT),
		Src_Minus_Dest(GL_FUNC_SUBTRACT),
		Dest_Minus_Src(GL_FUNC_REVERSE_SUBTRACT),
		
		Min(GL_MIN), Max(GL_MAX);
		
		private int glValue;
		private BlendOperator(int glValue) { this.glValue = glValue; }
		public int getFunction() { return glValue; }
		
		public static BlendOperator lookUp(int glValue) {
			for(BlendOperator op : values()) if(op.glValue == glValue) return op;
			throw new IllegalArgumentException(glValue + " is not a valid BlendOperation");
		}
		
		static class FunctionSet { 
			public FunctionSet(BlendOperator op) { this(op, op); }
			public FunctionSet(BlendOperator rgb, BlendOperator alpha) { 
				this.RGB_func = rgb; 
				this.ALPHA_func = alpha;
			}
			
			BlendOperator RGB_func, ALPHA_func; 
			void push() { glBlendEquationSeparate(RGB_func.glValue, ALPHA_func.glValue); }
		}
		
		private static FunctionSet pull() {
			return new FunctionSet(
				lookUp(glGetInteger(GL_BLEND_EQUATION_RGB)), 
				lookUp(glGetInteger(GL_BLEND_EQUATION_ALPHA))
			);
		}
	}
}
