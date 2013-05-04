package org.wdbuilder.domain;

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

}
