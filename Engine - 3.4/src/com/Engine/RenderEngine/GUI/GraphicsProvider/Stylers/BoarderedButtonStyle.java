//package com.Engine.RenderEngine.GUI.GraphicsProvider.Stylers;
//
//import com.Engine.RenderEngine.GUI.Components.UIButton;
//import com.Engine.RenderEngine.GUI.Components.SubComponents.QuadComponent;
//import com.Engine.RenderEngine.GUI.GraphicsProvider.Style;
//import com.Engine.Util.Vectors.Vector2f;
//
//public class BoarderedButtonStyle extends Style<UIButton> {
//
//	private Vector2f offset;
//	private Vector2f dimentions;
//	private float bSize;
//	
//	public BoarderedButtonStyle() {
//		super(UIButton.class);
//	}
//	
//	public QuadComponent[] getRects() {
//		return new QuadComponent[] {
//			new QuadComponent(bSize, bSize, 1 - bSize*2, 1 - bSize*2).updateTextureBounds(bSize, bSize, 1 - bSize*2, 1 - bSize*2),
//			
//			new QuadComponent(			0,			0, bSize, bSize).updateTextureBounds(		 0,			0, bSize, bSize),
//			new QuadComponent( 1 - bSize, 			0, bSize, bSize).updateTextureBounds(1 - bSize,			0, bSize, bSize),
//			new QuadComponent( 1 - bSize,  1 - bSize, bSize, bSize).updateTextureBounds(1 - bSize, 1 - bSize, bSize, bSize),
//			new QuadComponent(			0,  1 - bSize, bSize, bSize).updateTextureBounds(		 0, 1 - bSize, bSize, bSize),
//			
//			new QuadComponent(		bSize, 			0, 	1 - bSize * 2, 			bSize).updateTextureBounds(		bSize, 			0, 1 - bSize*2, 	  bSize),
//			new QuadComponent(		bSize, 	1 - bSize, 	1 - bSize * 2, 			bSize).updateTextureBounds(		bSize, 	1 - bSize, 1 - bSize*2, 	  bSize),
//			new QuadComponent(			0, 		bSize, 			bSize, 	1 - bSize * 2).updateTextureBounds(			0, 		bSize, 		 bSize, 1 - bSize*2),
//			new QuadComponent(	1 - bSize, 		bSize, 			bSize, 	1 - bSize * 2).updateTextureBounds(	1 - bSize, 		bSize, 		 bSize, 1 - bSize*2)
//		};
//	}
//
//	public QuadComponent applyTextureCoords(QuadComponent properties) {
//		if(properties == null) throw new NullPointerException();
//		properties.updateTextureBounds(offset.x, offset.y, dimentions.x, dimentions.y);
//		return properties;
//	}
//
//	public QuadComponent[] updateUI(UIButton button) {
//		return null;
//	}
//}
