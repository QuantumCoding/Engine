package com.Engine.RenderEngine.New_Pipeline;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ParallelRenderEngine {
	private List<Pipeline> pipelines;
	
	public ParallelRenderEngine() {
		pipelines = new ArrayList<>();
	}
	
	public void render() {
		for(ListIterator<Pipeline> iter = pipelines.listIterator(); iter.hasNext(); ) {
			Pipeline pipe = iter.next();
			
//			pipe.render();
		}
	}
}
