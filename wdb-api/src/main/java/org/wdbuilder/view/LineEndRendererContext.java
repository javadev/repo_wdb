package org.wdbuilder.view;

import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;

class LineEndRenderContext extends LineRendererContext implements
		ILineEndRendererContext {
	private Point baseLocation;
	private LinkSocket.Direction direction;

	@Override
	public Point getBaseLocation() {
		return baseLocation;
	}

	@Override
	public LinkSocket.Direction getDirection() {
		return direction;
	}

	void setBaseLocation(Point baseLocation) {
		this.baseLocation = baseLocation;
	}

	void setDirection(LinkSocket.Direction direction) {
		this.direction = direction;
	}

}
