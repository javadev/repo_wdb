package org.wdbuilder.view;

import java.awt.Graphics2D;
import java.util.Collection;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.plugin.IRenderContext;
import org.wdbuilder.service.DiagramHelper;
import org.wdbuilder.web.ApplicationState;

class RenderContext implements IRenderContext {
	private boolean opaque;
	private ApplicationState appState;
	private final Point offset = new Point(0, 0);
	private Graphics2D graphics;

	RenderContext() {

	}

	RenderContext(IRenderContext src, ApplicationState appState) {
		this.appState = appState;
		this.offset.setX(src.getOffset().getX());
		this.offset.setY(src.getOffset().getY());
		this.opaque = src.isOpaque();
	}
	
	@Override
	public Graphics2D getGraphics() {
		return this.graphics;
	}	

	@Override
	public boolean isOpaque() {
		return opaque;
	}
	
	@Override
	public Point getOffset() {
		return offset;
	}

	@Override
	public boolean isBlockMode() {
		return appState.isBlockMode();
	}

	@Override
	public Diagram getDiagram() {
		return appState.getDiagram();
	}

	void setOpaque(boolean opaque) {
		this.opaque = opaque;
	}

	ApplicationState getAppState() {
		return appState;
	}

	void setAppState(ApplicationState appState) {
		this.appState = appState;
	}
	
	void setGraphics( Graphics2D graphics ) {
		this.graphics = graphics;
	}

	@Override
	public Collection<LinkSocket> getUsedLinkSockets(Block block) {
		return new DiagramHelper(getDiagram()).getUsedLinkSockets(block);
	}
}
