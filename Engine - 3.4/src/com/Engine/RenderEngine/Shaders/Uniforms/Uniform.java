package com.Engine.RenderEngine.Shaders.Uniforms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.Engine.RenderEngine.Shaders.UniformType;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformImage.AccessQualifier;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Uniform {
	public static final String ERROR_MESSAGE = 
			"@Uniform is only applicable to fields that are of a sub-type of " + UniformType.class.getSimpleName();
	
	String value() default "";
	int index() default 0;
	int size() default 1;
		
	AccessQualifier access() default AccessQualifier.READ_WRITE;
}
