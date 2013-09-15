package org.wdbuilder.plugin;

import java.awt.Color;
import java.awt.Graphics2D;

import org.wdbuilder.domain.Block;

public interface ILinkRenderContext {

    Graphics2D getGraphics();

    Block getBlock(String key);

    boolean isBlockMode();

    Color getDiagramBackgroundColor(); 
}
