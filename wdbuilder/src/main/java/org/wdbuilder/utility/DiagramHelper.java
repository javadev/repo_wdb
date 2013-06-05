package org.wdbuilder.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Entity;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

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

	public void removeBlockByKey(String key) {
		// Remove all corresponding links:
		final Block block = findBlockByKey(key);
		if (null == block) {
			return;
		}
		final Collection<Link> connectedLinks = getConnectedLinks(block);
		final Set<String> connectedLinkKeys = new HashSet<String>(
				connectedLinks.size());
		for (final Link link : connectedLinks) {
			connectedLinkKeys.add(link.getKey());
		}

		List<Link> newList = new ArrayList<Link>(diagram.getLinks().size());
		for (final Link link : diagram.getLinks()) {
			if (!connectedLinkKeys.contains(link.getKey())) {
				newList.add(link);
			}
		}
		diagram.getLinks().clear();
		diagram.getLinks().addAll(newList);

		removeByKey(key, diagram.getBlocks());
	}

	public void removeLinkByKey(String key) {
		removeByKey(key, diagram.getLinks());
	}

	private static <T extends Entity> void removeByKey(String key,
			Collection<T> list) {
		for (final T obj : list) {
			if (obj.getKey().equals(key)) {
				list.remove(obj);
				return;
			}
		}
	}
	
	private Collection<Link> getConnectedLinks(Block baseBlock) {
		final String baseBlockKey = baseBlock.getKey();
		final Predicate<Link> predicate = new Predicate<Link>() {
			@Override
			public boolean apply(@Nullable Link link) {
				for (final LinkSocket socket : link.getSockets()) {
					if (baseBlockKey.equals(socket.getBlockKey())) {
						return true;
					}
				}
				return false;
			}
		};
		return Collections2.filter(diagram.getLinks(), predicate);
	}

	public final void calculatePivot(Link link) {

		// Every link should have at least 2 ends:
		final LinkSocket socket0 = link.getSockets().get(0);
		final LinkSocket socket1 = link.getSockets().get(1);

		final String key0 = socket0.getBlockKey();
		final String key1 = socket1.getBlockKey();

		final Block begin = findBlockByKey(key0);
		if (null == begin) {
			return;
		}
		final Block end = findBlockByKey(key1);
		if (null == end) {
			return;
		}
		Point beginP = socket0.getOffset(begin);
		Point endP = socket1.getOffset(end);

		link.setPivot(new Point((beginP.getX() + endP.getX()) / 2, (beginP
				.getY() + endP.getY()) / 2));
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
