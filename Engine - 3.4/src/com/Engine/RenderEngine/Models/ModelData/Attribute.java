package com.Engine.RenderEngine.Models.ModelData;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class Attribute {
	private int attribId;
	private int attribStride;
	
	public Attribute(int id, int stride) {
		this.attribId = id;
		this.attribStride = stride;
	}
	
	public void enable() {
		switch(attribStride) {
			case 0: glEnableVertexAttribArray(attribId); return;
			default: for(int i = 0; i < attribStride; i ++) glEnableVertexAttribArray(attribId + i); return;
		}
	}
	
	public void disable() {
		switch(attribStride) {
			case 0: glDisableVertexAttribArray(attribId); return;
			default: for(int i = 0; i < attribStride; i ++) glDisableVertexAttribArray(attribId + i); return;
		}
	}
	
	public int getId() { return attribId; }

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + attribId;
		result = prime * result + attribStride;
		
		return result;
	}
	
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof Attribute))
			return false;
		
		Attribute other = (Attribute) obj;
		if(attribId != other.attribId) return false;
		if(attribStride != other.attribStride) return false;
		
		return true;
	}
}
