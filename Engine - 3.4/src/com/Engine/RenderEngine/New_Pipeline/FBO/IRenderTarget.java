package com.Engine.RenderEngine.New_Pipeline.FBO;

import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.TargetFormat;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.TargetFormat.TargetLevel;

public interface IRenderTarget {
	public void bind();
	public default IRenderTarget init(TargetFormat format) { return init(format, TargetLevel.Default); }
	public IRenderTarget init(TargetFormat format, TargetLevel level);
	public void resize(float scaleX, float scaleY);
	public void attach(int attachment);
	
	public TargetFormat getFormat();
	public int getMultiSampleCount();
	
	public default boolean isMultiSampled() { 
		return getMultiSampleCount() > 0; 
	}
	
	public void cleanUp();
}
