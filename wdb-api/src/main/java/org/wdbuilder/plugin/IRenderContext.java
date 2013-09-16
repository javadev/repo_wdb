package org.wdbuilder.plugin;

import java.awt.Graphics2D;
import java.util.Collection;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;

// TODO: extract IBlockRenderContext and base for ILinkRenderContext (2013/05/06)
public interface IRenderContext {

    boolean isOpaque();

    boolean isBlockMode();

    Point getOffset();
    
    Diagram getDiagram();
    
    Graphics2D getGraphics();
    
    Collection<LinkSocket> getUsedLinkSockets(final Block block);
}
