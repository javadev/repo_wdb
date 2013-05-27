package org.wdbuilder.plugin;

import java.awt.Graphics2D;
import java.util.Collection;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;

// TODO: extract IBlockRenderContext and base for ILinkRenderContext (2013/05/06)
public interface IRenderContext {

	public boolean isOpaque();

	public boolean isBlockMode();

	public Point getOffset();
	
	public Diagram getDiagram();
	
	public Graphics2D getGraphics();
	
	public Collection<LinkSocket> getUsedLinkSockets(final Block block);
}
