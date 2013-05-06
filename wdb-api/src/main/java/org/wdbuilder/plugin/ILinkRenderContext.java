package org.wdbuilder.plugin;

import java.awt.Color;
import java.awt.Graphics2D;

import org.wdbuilder.domain.Block;

public interface ILinkRenderContext {
	
	public Graphics2D getGraphics();

	public Block getBlock(String key);

	public boolean isBlockMode();
	
	public Color getDiagramBackgroundColor(); 
}
