package org.wdbuilder.domain;

import java.util.List;

import org.wdbuilder.domain.helper.Point;

public class Link extends Entity {
	private static final long serialVersionUID = 1L;
	
	private List<LinkSocket> sockets;
	
	private Point pivot;

	public Point getPivot() {
	    return pivot;
    }

	public void setPivot(Point pivot) {
		if( null==pivot ) {
			throw new IllegalArgumentException("Pivot can't be null");
		}
	    this.pivot = pivot;
    }

	public List<LinkSocket> getSockets() {
		return sockets;
	}

	public void setSockets(List<LinkSocket> sockets) {
		this.sockets = sockets;
	}

}
