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
import static org.lwjgl.opengl.GL11.glEnable;
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
import static org.lwjgl.opengl.GL30.glDisablei;
import static org.lwjgl.opengl.GL30.glEnablei;
import static org.lwjgl.opengl.GL30.glIsEnabledi;
import static org.lwjgl.opengl.GL40.glBlendEquationSeparatei;
import static org.lwjgl.opengl.GL40.glBlendFuncSeparatei;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.Engine.RenderEngine.GLFunctions.BlendFunc.BlendMulti.MultiplierSet;
import com.Engine.RenderEngine.GLFunctions.BlendFunc.BlendOperator.FunctionSet;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.Util.Vectors.Vector4f;

public class BlendFunc extends GL_Function {
	private static final BlendFunc CURRENT = new BlendFunc(); static { CURRENT.syncGLState(); }
	
	private static final BlendFunc ADDED_BLENDING = new BlendFunc(true).setMultipliers(SRC_Alpha, One);
	private static final BlendFunc NORMAL_BLENDING = new BlendFunc(true).setMultipliers(SRC_Alpha, One_Minus_SRC_Alpha);

	private static final FloatBuffer COLOUR_PULL = BufferUtils.createFloatBuffer(16);
	
	// http://www.andersriggelsen.dk/glblendfunc.php
	
	// Src (Source) 		what is being added
	// Dst (Destination)	what is already there
	
	// Function: Src * multi  (operation) Dst * multi
	// Example:	 Src * src.alpha   +    Dst * (1 - src.alpha)
	//			   SRC_Aplha      Add	 One_Minus_SRC_Alpha

	private Vector4f constColor;
	private BlendLayer[] blendLayers;
	private BlendLayer defaultValues;

	private BlendFunc() { init(); }
	private BlendFunc(boolean enabled) { super(enabled); init(); defaultValues.enabled = enabled; }
	
	private void init() {
		if(constColor == null) constColor = new Vector4f(1);
		if(defaultValues == null) defaultValues = new BlendLayer();
		if(blendLayers == null) blendLayers = new BlendLayer[FBO.MAX_DRAW_BUFFERS];
	}
	
	public void push() { _push(); }
	
	protected void _push() {
		glBlendColor(constColor.x, constColor.y, constColor.z, constColor.w);
		CURRENT.constColor.x = constColor.x;	CURRENT.constColor.y = constColor.y;
		CURRENT.constColor.z = constColor.z;	CURRENT.constColor.w = constColor.w;
		
		defaultValues.push();
		defaultValues.copyTo(CURRENT.defaultValues);
		
		for(int i = 0; i < blendLayers.length; i ++) {
			if(blendLayers[i] == null) {
				CURRENT.blendLayers[i] = null;
				continue;
			}
			
			blendLayers[i].pushToLayer(i);
			blendLayers[i].copyTo(CURRENT.blendLayers[i]);
		}
	}

	protected void _pull() {
		if(COLOUR_PULL == null) return;
		
		COLOUR_PULL.clear();
		get(GL_BLEND_COLOR, COLOUR_PULL);
		constColor = new Vector4f(COLOUR_PULL.get(), COLOUR_PULL.get(), COLOUR_PULL.get(), COLOUR_PULL.get());

		defaultValues.pull();
		for(int i = 0; i < blendLayers.length; i ++) {
			BlendLayer layer = new BlendLayer(); 
			layer.pullFromLayer(i);
			
			blendLayers[i] = defaultValues.isSame(layer) ? null : layer;
		}
	}
	
	public BlendFunc setMultipliers(BlendMulti src, BlendMulti dst) { return setMultipliers(src, dst, src, dst); }
	public BlendFunc setMultipliersRGB(BlendMulti src, BlendMulti dst) { return setMultipliers(src, dst, null, null); }
	public BlendFunc setMultipliersAlpha(BlendMulti src, BlendMulti dst) { return setMultipliers(null, null, src, dst); }
	
	public BlendFunc setMultipliers(BlendMulti src, BlendMulti dst, BlendMulti srcA, BlendMulti dstA) {
		defaultValues.setMultipliers(src, dst, srcA, dstA);
		return this;
	}
	
	public BlendFunc setMultipliers(int layer, BlendMulti src, BlendMulti dst) { return setMultipliers(layer, src, dst, src, dst); }
	public BlendFunc setMultipliersRGB(int layer, BlendMulti src, BlendMulti dst) { return setMultipliers(layer, src, dst, null, null); }
	public BlendFunc setMultipliersAlpha(int layer, BlendMulti src, BlendMulti dst) { return setMultipliers(layer, null, null, src, dst); }
	
	public BlendFunc setMultipliers(int layer, BlendMulti src, BlendMulti dst, BlendMulti srcA, BlendMulti dstA) {
		if(blendLayers[layer] == null) blendLayers[layer] = defaultValues.clone();
		blendLayers[layer].setMultipliers(src, dst, srcA, dstA);
		return this;
	}
	
	public BlendFunc setOperator(BlendOperator op) { return setOperator(op, op); }
	public BlendFunc setOperatorRGB(BlendOperator op) { return setOperator(op, null); }
	public BlendFunc setOperatorAlpha(BlendOperator op) { return setOperator(null, op); }
	
	public BlendFunc setOperator(BlendOperator rgbOp, BlendOperator alphaOp) {
		defaultValues.setOperator(rgbOp, alphaOp);
		return this;
	}
	
	public BlendFunc setOperator(int layer, BlendOperator op) { return setOperator(layer, op, op); }
	public BlendFunc setOperatorRGB(int layer, BlendOperator op) { return setOperator(layer, op, null); }
	public BlendFunc setOperatorAlpha(int layer, BlendOperator op) { return setOperator(layer, null, op); }
	
	public BlendFunc setOperator(int layer,  BlendOperator rgbOp, BlendOperator alphaOp) {
		if(blendLayers[layer] == null) blendLayers[layer] = defaultValues.clone();
		blendLayers[layer].setOperator(rgbOp, alphaOp);	
		return this;
	}

	protected BlendFunc getLocalCashe() { return CURRENT; }

	protected int getGLCapablity() { return GL_BLEND; }
	public BlendFunc clone() { return (BlendFunc) copyTo(new BlendFunc()); }
	
	protected BlendFunc _copyTo(GL_Function function) {
		BlendFunc blendFunc = (BlendFunc) function;
			blendFunc.constColor = this.constColor;
			blendFunc.defaultValues = this.defaultValues;
			blendFunc.blendLayers = this.blendLayers;
		return blendFunc;
	}
	
	public static boolean isEnabled() { return CURRENT.defaultValues.enabled; } //glIsEnabled(GL_BLEND); }
	public static void disable() { glDisable(GL_BLEND); CURRENT.defaultValues.enabled = false; }
	
	public static boolean isEnabled(int layer) { 
		return CURRENT.blendLayers[layer] == null ? isEnabled() : CURRENT.blendLayers[layer].enabled; 
	}
	
	public static void disable(int layer) { 
		glDisable(GL_BLEND);
		(CURRENT.blendLayers[layer] == null ? CURRENT.defaultValues : CURRENT.blendLayers[layer]).enabled = false;
	}
	
	public static BlendFunc blankDiabled() { return new BlendFunc(false); }
	public static BlendFunc	blankEnabled() { return new BlendFunc(true); }
	
	public static BlendFunc current() { return new BlendFunc(isEnabled()); }
	
	public static BlendFunc additive() { return ADDED_BLENDING.clone(); }
	public static BlendFunc normal() { return NORMAL_BLENDING.clone(); }
	
	private class BlendLayer {
		private boolean enabled = false;
		private FunctionSet function = new FunctionSet(BlendOperator.Add);
		private MultiplierSet multipliers = new MultiplierSet(SRC_Alpha, One);
		
		public BlendLayer setMultipliers(BlendMulti src, BlendMulti dst, BlendMulti srcA, BlendMulti dstA) {
			if(src != null) multipliers.SRC_RGB_multi = src;	
			if(dst != null) multipliers.DST_RGB_multi = dst;
			if(srcA != null) multipliers.SRC_ALPHA_multi = srcA;	
			if(dstA != null) multipliers.DST_ALPHA_multi = dstA;
			
			return this;
		}
		
		public BlendLayer setOperator(BlendOperator rgbOp, BlendOperator alphaOp) {
			if(rgbOp != null) function.RGB_func = rgbOp;	
			if(alphaOp != null) function.ALPHA_func = alphaOp;
			
			return this;
		}
		
		public void push() {
			if(!enabled) {
				glDisable(GL_BLEND);
				return;
			}

			glEnable(GL_BLEND);
			function.push();
			multipliers.push();
		}
		
		public void pushToLayer(int layer) {
			if(!enabled) {
				glDisablei(GL_BLEND, layer);
				return;
			}
			
			glEnablei(GL_BLEND, layer);
			function.push(layer);
			multipliers.push(layer);
		}
		
		public void pull() {
			enabled = glIsEnabled(GL_BLEND);
			function = BlendOperator.pull(BlendFunc.this);
			multipliers = BlendMulti.pull(BlendFunc.this);
		}
		
		public void pullFromLayer(int layer) {
			enabled = glIsEnabledi(GL_BLEND, layer);
			function = BlendOperator.pull(BlendFunc.this, layer);
			multipliers = BlendMulti.pull(BlendFunc.this, layer);
		}
		
		public void copyTo(BlendLayer layer) {
			layer.enabled = enabled;
			
			layer.function.RGB_func = function.RGB_func;
			layer.function.ALPHA_func = function.ALPHA_func;
			
			layer.multipliers.SRC_RGB_multi = multipliers.SRC_RGB_multi;
			layer.multipliers.DST_RGB_multi = multipliers.DST_RGB_multi;
			layer.multipliers.SRC_ALPHA_multi = multipliers.SRC_ALPHA_multi;
			layer.multipliers.DST_ALPHA_multi = multipliers.DST_ALPHA_multi;
		}
		
		public BlendLayer clone() {
			BlendLayer layer = new BlendLayer();
			copyTo(layer);
			return layer;
		}
		
		public boolean isSame(BlendLayer layer) {
			return 
				enabled == layer.enabled &&
					
				function.RGB_func == layer.function.RGB_func &&
				function.ALPHA_func == layer.function.ALPHA_func &&
				
				multipliers.SRC_RGB_multi == layer.multipliers.SRC_RGB_multi &&
				multipliers.DST_RGB_multi == layer.multipliers.DST_RGB_multi &&
				multipliers.SRC_ALPHA_multi == layer.multipliers.SRC_ALPHA_multi &&
				multipliers.DST_ALPHA_multi == layer.multipliers.DST_ALPHA_multi ;
		}
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
			
			void push(int layer) { glBlendFuncSeparatei( layer,
				SRC_RGB_multi.glValue, DST_RGB_multi.glValue,
				SRC_ALPHA_multi.glValue, DST_ALPHA_multi.glValue
			);}
		}
		
		private static MultiplierSet pull(BlendFunc func) {
			return new MultiplierSet(
				lookUp(func.getInt(GL_BLEND_SRC_RGB)),
				lookUp(func.getInt(GL_BLEND_DST_RGB)),
				lookUp(func.getInt(GL_BLEND_SRC_ALPHA)),
				lookUp(func.getInt(GL_BLEND_DST_ALPHA))
			);
		}
		
		private static MultiplierSet pull(BlendFunc func, int layer) {
			return new MultiplierSet(
				lookUp(func.getInt(GL_BLEND_SRC_RGB, layer)),
				lookUp(func.getInt(GL_BLEND_DST_RGB, layer)),
				lookUp(func.getInt(GL_BLEND_SRC_ALPHA, layer)),
				lookUp(func.getInt(GL_BLEND_DST_ALPHA, layer))
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
			void push(int layer) { glBlendEquationSeparatei(layer, RGB_func.glValue, ALPHA_func.glValue); }
		}
		
		private static FunctionSet pull(BlendFunc func) {
			return new FunctionSet(
				lookUp(func.getInt(GL_BLEND_EQUATION_RGB)), 
				lookUp(func.getInt(GL_BLEND_EQUATION_ALPHA))
			);
		}
		
		private static FunctionSet pull(BlendFunc func, int layer) {
			return new FunctionSet(
				lookUp(func.getInt(GL_BLEND_EQUATION_RGB, layer)), 
				lookUp(func.getInt(GL_BLEND_EQUATION_ALPHA, layer))
			);
		}
	}
}
