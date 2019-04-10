package com.Engine.RenderEngine.Textures;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z;
import static org.lwjgl.opengl.GL21.GL_SRGB8_ALPHA8;
import static org.lwjgl.opengl.GL40.GL_TEXTURE_CUBE_MAP_ARRAY;
import static org.lwjgl.opengl.GL43.glCopyImageSubData;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.Engine.RenderEngine.Textures.TextureUtil.TextureData;
import com.Engine.Util.Math.MathUtil;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class TextureCubeMap extends Texture {
	public static enum CubeMapSide {
		Right	(GL_TEXTURE_CUBE_MAP_POSITIVE_X, new Vector3f( 1,  0,  0)),
		Top		(GL_TEXTURE_CUBE_MAP_POSITIVE_Y, new Vector3f( 0,  1,  0)), 
		Front	(GL_TEXTURE_CUBE_MAP_POSITIVE_Z, new Vector3f( 0,  0,  1)),
		Left	(GL_TEXTURE_CUBE_MAP_NEGATIVE_X, new Vector3f(-1,  0,  0)),
		Bottom	(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, new Vector3f( 0, -1,  0)), 
		Back	(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, new Vector3f( 0,  0, -1));
		
		private int gl;
		private Vector3f axis;
		private CubeMapSide(int gl, Vector3f axis) { 
			this.gl = gl; 
			this.axis = axis; 
		}
		
		public int gl() { return gl; }
		public Vector3f axis() { return axis; }
	}

	public static class CubeMapSources {
		TextureData data;
		CubeMapSide side;
		
		public CubeMapSources(CubeMapSide side, BufferedImage image) {
			this.side = side;
			this.data = TextureUtil.from(image);
		}
		
		public CubeMapSources(CubeMapSide side, InputStream stream) {
			this.side = side;
			this.data = TextureUtil.from(true, stream);
		}
	}
	
	public static Set<CubeMapSources> from(
			InputStream left, InputStream right, InputStream front, InputStream back, InputStream top, InputStream bottom) {
		HashSet<CubeMapSources> sources = new HashSet<>();
		
		sources.add(new CubeMapSources(CubeMapSide.Right	, right	));
		sources.add(new CubeMapSources(CubeMapSide.Top		, top	));
		sources.add(new CubeMapSources(CubeMapSide.Front	, front	));
		sources.add(new CubeMapSources(CubeMapSide.Left		, left	));
		sources.add(new CubeMapSources(CubeMapSide.Bottom	, bottom));
		sources.add(new CubeMapSources(CubeMapSide.Back		, back	));
		
		return sources;
	}
	
	public TextureCubeMap(int textureID) { super(textureID); }
	
	public TextureCubeMap(Vector2f size) { 
		super();

		bind();
			for(CubeMapSide side : CubeMapSide.values()) {
				glTexImage2D(
				        side.gl, 0, GL_RGBA8, (int) size.x, (int) size.y, 
				        0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null
				    );
			}
			
			setDefaultProperties();
		unbind();
	}
	
	public TextureCubeMap(boolean sRGB, Collection<CubeMapSources> sources) {
		super();
		
		bind();
			for(CubeMapSources sideData : sources) {
				glTexImage2D(
				        sideData.side.gl, 0, 
				        sRGB ? GL_SRGB8_ALPHA8 : GL_RGBA8, 
		        		(int) sideData.data.size.x, (int) sideData.data.size.y, 0, 
		        		GL_RGBA, GL_UNSIGNED_BYTE, sideData.data.buffer
				    );
			}
			
			setDefaultProperties();
		unbind();
	}
	
	public void setDefaultProperties() {
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
		
		super.pullTextureSize(GL_TEXTURE_CUBE_MAP_POSITIVE_X);
	}
	
	public void copyToLayer(int layer, TextureCubeMapArray array) {
		// All Aspect Ratio's are 1:1
		// All Sizes are x^2
		// All Mip Levels are present and Filled (if source)
		
		// Assume Base Mip-Level is 0
		
		int sourceMipOffset, destMipOffset;
		int iterationCount;
		
		int sourcePower = (int) MathUtil.log2(super.width);
		int destPower = (int) MathUtil.log2(array.width);
		
		if(sourcePower > destPower) {
			sourceMipOffset = sourcePower - destPower;
			destMipOffset = 0;
			iterationCount = array.maxMipLayers;
			
		} else if(sourcePower < destPower) {
			sourceMipOffset = 0;
			destMipOffset = 0;
			throw new IllegalStateException("Unhandled State");
		
		} else {
			sourceMipOffset = 0;
			destMipOffset = 0;
			iterationCount = maxMipLayers;
		}
		
		for(int i = 0; i < iterationCount; i ++) {
			copyLevel(layer * 6, sourceMipOffset + i, destMipOffset + i, this, array);
		}
	}
	
	private static void copyLevel(int depth, int srcLevel, int destLevel, TextureCubeMap cube, TextureCubeMapArray array) {
		int dimension = cube.width >>> srcLevel;

		glCopyImageSubData(
				cube.textureId,  GL_TEXTURE_CUBE_MAP, 		srcLevel, 	0, 0, 0, 
				array.textureId, GL_TEXTURE_CUBE_MAP_ARRAY, destLevel, 	0, 0, depth, 
				dimension, dimension, 6
			);
	}
	
	public Vector2f getSize() { return new Vector2f(width, height); }
	
	public int getGLTextureType() { return GL_TEXTURE_CUBE_MAP; }
	public static void unbind_Cube(int texurePos) { Texture.unbind(GL_TEXTURE_CUBE_MAP, texurePos); }
}
