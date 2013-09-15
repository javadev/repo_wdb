package org.wdbuilder.view;

import java.awt.Color;
import java.awt.Graphics2D;

import org.wdbuilder.domain.Block;
import org.wdbuilder.plugin.ILinkRenderContext;
import org.wdbuilder.web.ApplicationState;

class LinkRenderContext implements ILinkRenderContext {

    private final ApplicationState appState;
    private final Graphics2D graphics;

    LinkRenderContext(ApplicationState appState, Graphics2D graphics) {
        this.appState = appState;
        this.graphics = graphics;
    }

    @Override
    public Graphics2D getGraphics() {
        return this.graphics;
    }

    @Override
    public Block getBlock(String key) {
        return appState.getDiagram().getBlock(key);
    }

    @Override
    public boolean isBlockMode() {
        return this.appState.isBlockMode();
    }

    @Override
    public Color getDiagramBackgroundColor() {
        return this.appState.getDiagram().getBackground()
                .getPrimaryBackgroundColor();
    }

}
