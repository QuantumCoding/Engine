package com.Engine.RenderEngine.Textures;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL12.glTexSubImage3D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL21.GL_SRGB_ALPHA;
import static org.lwjgl.opengl.GL40.GL_TEXTURE_CUBE_MAP_ARRAY;
import static org.lwjgl.opengl.GL42.glTexStorage3D;

import java.util.Collection;

import com.Engine.RenderEngine.Textures.TextureCubeMap.CubeMapSources;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class TextureCubeMapArray extends Texture {
	public TextureCubeMapArray(int textureID) { super(textureID); }
	
	public TextureCubeMapArray(Vector3f size, int miplayerCount) { 
		super();

		bind();
			glTexStorage3D(GL_TEXTURE_CUBE_MAP_ARRAY, miplayerCount, GL_RGBA8, (int) size.x, (int) size.y, (int) size.z * 6);
			setDefaultProperties();
		unbind();
	}
	
	public TextureCubeMapArray(boolean sRGB, Collection<CubeMapSources>[] sources) {
		super();
		
		bind();
			Vector2f size = sources[0].iterator().next().data.size;
			glTexStorage3D(GL_TEXTURE_CUBE_MAP_ARRAY, 1, GL_RGBA8, (int) size.x, (int) size.y, sources.length * 6);
			
			for(int layer = 0; layer < sources.length; layer ++) {
				for(CubeMapSources sideData : sources[layer]) {
					int depth = layer * 6 + sideData.side.gl() - GL_TEXTURE_CUBE_MAP_POSITIVE_X;
					
					glTexSubImage3D(
							GL_TEXTURE_CUBE_MAP_ARRAY, 0, 											// Face, Mip-Level
							0, 0, depth, 															// Offset: X, Y, Z
							(int) sideData.data.size.x, (int) sideData.data.size.y, 1,				// Width, Height, Depth
							sRGB ? GL_SRGB_ALPHA : GL_RGBA, GL_UNSIGNED_BYTE, sideData.data.buffer	// Format, DataType, Data
						);
				}
			}
			
			setDefaultProperties();
		unbind();
	}
	
	public void setDefaultProperties() {
		glTexParameteri(GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

		super.pullTextureSize(GL_TEXTURE_CUBE_MAP_ARRAY);
	}
	
	public int getGLTextureType() { return GL_TEXTURE_CUBE_MAP_ARRAY; }
	public static void unbind_Cube(int texurePos) { Texture.unbind(GL_TEXTURE_CUBE_MAP_ARRAY, texurePos); }
}
