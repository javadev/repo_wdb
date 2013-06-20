package org.wdbuilder.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Entity;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;

/**
 * Helper diagram related methods that does not correspond with data storing and
 * serializing
 * 
 * @author o.pavloschuk
 * 
 */
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
		Block block = findBlockByKey(socket.getBlockKey());
		if (null == block) {
			return null;
		}
		return socket.getOffset(socket.getLocation(block));
	}

	public Block findBlockByKey(String key) {
		return findByKey(key, diagram.getBlocks());
	}

	public Link findLinkByKey(String key) {
		return findByKey(key, diagram.getLinks());
	}

	private static <T extends Entity> T findByKey(String key, Collection<T> list) {
		for (final T obj : list) {
			if (obj.getKey().equals(key)) {
				return obj;
			}
		}
		return null;
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

	public boolean hasLinkWithSameEnds(Link probeLink) {
		for (Link link : diagram.getLinks()) {
			if (link.getSockets().get(0).equals(probeLink.getSockets().get(0))
					&& link.getSockets().get(1)
							.equals(probeLink.getSockets().get(1))) {
				return true;
			}
		}
		return false;
	}
}
