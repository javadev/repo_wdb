package org.wdbuilder.view;

import java.awt.Graphics2D;

import org.wdbuilder.domain.Block;
import org.wdbuilder.plugin.ILinkRenderContext;
import org.wdbuilder.utility.DiagramHelper;
import org.wdbuilder.web.ApplicationState;

class LinkRenderContext implements ILinkRenderContext {
	
	private final ApplicationState appState;
	private final Graphics2D graphics;
	private final DiagramHelper diagramHelper; 

	LinkRenderContext(ApplicationState appState, Graphics2D graphics) {
		this.appState = appState;
		this.graphics = graphics;
		this.diagramHelper = new DiagramHelper(this.appState.getDiagram());
	}
	

	@Override
	public Graphics2D getGraphics() {
		return this.graphics;
	}

	@Override
	public Block getBlock(String key) {
		return this.diagramHelper.findBlockByKey(key);
	}

	@Override
	public boolean isBlockMode() {
		return this.appState.isBlockMode();
	}

}
