package org.wdbuilder.service;

import java.util.HashSet;
import java.util.Set;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;

public class DiagramHelper {
	private final Diagram diagram;

	public DiagramHelper(Diagram diagram) {
		if (null == diagram) {
			throw new IllegalArgumentException("Diagram can' t be null");
		}
		this.diagram = diagram;
	}

	public Diagram getDiagram() {
		return this.diagram;
	}

	public Set<LinkSocket> getUsedLinkSockets(final Block block) {

		final Set<LinkSocket> result = new HashSet<LinkSocket>(4);
		if (null == block) {
			return result;
		}
		final String key = block.getKey();
		if (null == key) {
			return result;
		}
		for (final Link link : this.diagram.getLinks()) {
			for (LinkSocket socket : link.getSockets()) {
				if (key.equals(socket.getBlockKey())) {
					result.add(socket);
				}
			}
		}

		return result;
	}

	public Point getOffset(LinkSocket socket) {
		Block block = diagram.getBlock(socket.getBlockKey());
		if (null == block) {
			return null;
		}
		return socket.getOffset(socket.getLocation(block));
	}
	
	public final void calculatePivot(Link link) {

		// Every link should have at least 2 ends:
		final LinkSocket socket0 = link.getSockets().get(0);
		final LinkSocket socket1 = link.getSockets().get(1);
		
		final Point beginP = getOffset(socket0);
		final Point endP = getOffset(socket1);
		if( null==beginP || null==endP ) {
			return;
		}
		final int x = (beginP.getX() + endP.getX()) / 2;
		final int y = (beginP.getY() + endP.getY()) / 2;		
		link.setPivot(new Point(x,y));
	}

}
