package com.Engine.RenderEngine.Shaders;

public abstract class UniformType<E> {
	private String name;
	protected int location;
	
	public UniformType(String name) { this.name = name; }
	public void attach(Shader shader) {
		location = shader.getUniformLocation(name);
		if(location == -1) System.err.println("No uniform named \"" + name + "\" exits for " + 
				shader.getClass().getSimpleName() + "; Shader may not work as intended");
	}
	
	public abstract void load(E value);
}
