package com.Engine.RenderEngine.Shaders.Uniforms;

import java.lang.annotation.AnnotationFormatError;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.Engine.RenderEngine.Shaders.RawShader;
import com.Engine.RenderEngine.Shaders.UniformType;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformBuffer;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformImage;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformImage.AccessQualifier;
import com.Engine.RenderEngine.Shaders.Uniforms.Types.UniformTexture;
import com.Engine.Util.Log;

public class UniformLinker {
	public static void initUniformLocations(RawShader shader) { 
		initUniformLocations(shader.getClass().getDeclaredFields(), "", shader, shader); 
	}
	
	public static void initUniformLocations(Field[] fields, String namePrefix, Object source, RawShader shader) {
		for(Field field : fields) {
			if(!field.isAnnotationPresent(Uniform.class)) continue;
			
			Class<?> type = field.getType();
			
			boolean isArray = type.isArray();
			if(isArray) type = type.getComponentType();

			if(!UniformType.class.isAssignableFrom(type) || type.equals(UniformType.class)) 
				throw new AnnotationFormatError(Uniform.ERROR_MESSAGE + "; not of type " + type.getSimpleName());
			
			Uniform annotation = field.getAnnotation(Uniform.class);
			String name = annotation.value().isEmpty() ? field.getName() : annotation.value();
			name = namePrefix + name;
			
			Object value;
			if(isArray) value = createUniformArrayType(name, annotation, shader, type);
			else 		value = createAttachedUniform(name, annotation, shader, type);
			
			assignField(field, source, value);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <E> UniformType<E> createUniform(String name, Uniform annotation, Object source, Class<?> type) {
		try {
			UniformType<E> value;
			Constructor<?> constructor;
			
			if(UniformBuffer.class.isAssignableFrom(type)) {
				if(!(source instanceof RawShader)) {
					throw new IllegalArgumentException("UniformBuffers can only be the children of Shaders;"
							+ " not " + source.getClass().getSimpleName());
				}
				
				value = (UniformType<E>) UniformBuffer.createBuffer(type, (RawShader) source, name);
				
			} else if(type.equals(UniformTexture.class)) {
				constructor = type.getConstructor(String.class, int.class);
				value = (UniformType<E>) constructor.newInstance(name, annotation.index());
				
			} else if(type.equals(UniformImage.class)) {
				constructor = type.getConstructor(String.class, int.class, AccessQualifier.class);
				value = (UniformType<E>) constructor.newInstance(name, annotation.index(), annotation.access());
				
			} else {
				constructor = type.getConstructor(String.class);
				value = (UniformType<E>) constructor.newInstance(name);
			}
			
			return value;
			
		} catch(ReflectiveOperationException | SecurityException | IllegalArgumentException e) {
			Log.sneekyThrow(e);
			return null;
		}
	}
	
	private static UniformType<?> createAttachedUniform(String name, Uniform annotation, RawShader shader, Class<?> type) {
		try {
			UniformType<?> value = createUniform(name, annotation, shader, type);
			if(value == null) throw new ReflectiveOperationException(); 
			
			attachUniform(value, shader);
			return value;
			
		} catch(ReflectiveOperationException | SecurityException | IllegalArgumentException e) {
			Log.sneekyThrow(e);
			return null;
		}
	}
	
	private static Object createUniformArrayType(String name, Uniform annotation, RawShader shader, Class<?> type) {
		int size = annotation.size();
		Object value = Array.newInstance(type, size);
		
		for(int i = 0; i < size; i ++) {
			Array.set(value, i, createAttachedUniform(name + "["+i+"]", annotation, shader, type));
		}
		
		return value;
	}
	
	public static void assignField(Field field, Object source, Object value) {
		try {
			if((field.getModifiers() & Modifier.FINAL) != 0) {
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			}
			
			field.setAccessible(true); 
			field.set(source, value);
			
		} catch(IllegalArgumentException | ReflectiveOperationException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private static final Method UNIFORM_ATTACH; static {
		Method method = null; try {
			method = UniformType.class.getDeclaredMethod("attach", RawShader.class);
			method.setAccessible(true);
		} catch(ReflectiveOperationException e) { e.printStackTrace(); }
		
		UNIFORM_ATTACH = method;
	}
	
	private static void attachUniform(UniformType<?> uniform, RawShader shader) {
		try { UNIFORM_ATTACH.invoke(uniform, shader); }
		catch(ReflectiveOperationException | IllegalArgumentException e) { Log.sneekyThrow(e); }
	}
}
