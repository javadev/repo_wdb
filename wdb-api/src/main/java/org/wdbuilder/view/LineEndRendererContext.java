package org.wdbuilder.view;

import java.awt.Color;
import java.awt.Graphics2D;

import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;

class LineEndRenderContext implements ILineEndRendererContext {
  private Graphics2D graphics;
	private Point baseLocation;
	private Color color;
	private LinkSocket.Direction direction;

	@Override
	public Graphics2D getGraphics() {
		return graphics;
	}
	
	@Override
	public Point getBaseLocation() {
		return baseLocation;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public LinkSocket.Direction getDirection() {
		return direction;
	}	

	void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;
	}

	void setBaseLocation(Point baseLocation) {
		this.baseLocation = baseLocation;
	}

	void setColor(Color color) {
		this.color = color;
	}

	void setDirection(LinkSocket.Direction direction) {
		this.direction = direction;
	}

}
