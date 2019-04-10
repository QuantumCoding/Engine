package com.Engine.RenderEngine.Environment;

import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;

import com.Engine.RenderEngine.Camera.PerspectiveCamera;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.Attachment;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderBuffer;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderTexture;
import com.Engine.RenderEngine.Textures.TextureCubeMap;
import com.Engine.RenderEngine.Textures.TextureCubeMap.CubeMapSide;
import com.Engine.RenderEngine.Textures.TextureCubeMapArray;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class CubeCamera extends PerspectiveCamera {
	public static enum CameraDirection {
		Forward	(new Vector3f(  0,   0, 180), CubeMapSide.Back),
		Backward(new Vector3f(  0, 180, 180), CubeMapSide.Front),
		
		Left	(new Vector3f(  0,  90, 180), CubeMapSide.Left),
		Right	(new Vector3f(  0, -90, 180), CubeMapSide.Right),
		
		Up		(new Vector3f(-90,   0,   0), CubeMapSide.Top),
		Down	(new Vector3f( 90,   0,   0), CubeMapSide.Bottom);
		
		public Vector3f rotation; public CubeMapSide side;
		private CameraDirection(Vector3f rotation, CubeMapSide side) { 
			this.rotation = rotation;
			this.side = side;
		}
	}
	
	private int size; 
	private FBO renderTarget;
	private TextureCubeMap texture;
	
	public CubeCamera(int size, float zNear, float zFar) {
		super(90, 1, zNear, zFar);
		this.size = size;
		
		renderTarget = new FBO(size, size);
		renderTarget.attach(new RenderBuffer(renderTarget), Attachment.DepthBuffer);

		texture = new TextureCubeMap(new Vector2f(size, size));
		for(CameraDirection dir : CameraDirection.values()) {
			renderTarget.attach(
					new RenderTexture(size, size, texture, dir.side.gl(), 0), 
					Attachment.ColourBuffer, dir.ordinal()
				);
		}
	}
	
	public CubeCamera(TextureCubeMapArray textureMap, int layer, int size, float zNear, float zFar) {
		super(90, 1, zNear, zFar);
		this.size = size;
		
		renderTarget = new FBO(size, size);
		renderTarget.attach(new RenderBuffer(renderTarget), Attachment.DepthBuffer);

		for(CameraDirection dir : CameraDirection.values()) {
			renderTarget.attach(
					new RenderTexture(size, size, textureMap, -1, 0, 
							layer * 6 + dir.side.gl() - GL_TEXTURE_CUBE_MAP_POSITIVE_X), 
					Attachment.ColourBuffer, dir.ordinal()
				);
		}
	}
	
	public void renderToFace(CameraDirection direction) {
		renderTarget.bindDraw(direction.ordinal());
		setRotation(direction.rotation);
	}
	
	public TextureCubeMap getTexture() { return texture; }
	public FBO getFBO() { return renderTarget; }
	
	public int getSize() { return size; }
}
