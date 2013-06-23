package org.wdbuilder.domain;

import java.util.HashSet;
import java.util.Set;

import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.domain.helper.Point;

public abstract class Block extends SizedEntity {
	private static final long serialVersionUID = 1L;

	private Point location;
	
	public abstract int getMaxLinkSocketNumX();
	public abstract int getMaxLinkSocketNumY();
	
	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		if (null == location) {
			throw new IllegalArgumentException("Location can't be null");
		}
		this.location = location;
	}
	
	public Set<LinkSocket> getUsedLinkSockets(final Diagram diagram) {

		final Set<LinkSocket> result = new HashSet<LinkSocket>(4);
		for (final Link link : diagram.getLinks()) {
			for (LinkSocket socket : link.getSockets()) {
				if (getKey().equals(socket.getBlockKey())) {
					result.add(socket);
				}
			}
		}

		return result;
	}
	
	public Point getTopLeft() {
		Dimension size = getSize();
		int x = getLocation().getX() - size.getWidth() / 2;
		int y = getLocation().getY() - size.getHeight() / 2;		
		return new Point( x, y );
	}

}
