package org.wdbuilder.service;

import java.util.ArrayList;
import java.util.UUID;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.plugin.ILinkPluginFacade;
import org.wdbuilder.view.line.end.LineEnd;

class StaticLinkService extends StaticDiagramRelatedService implements
		LinkService {

	StaticLinkService(Diagram diagram, IServiceFacade serviceFacade) {
		super(diagram, serviceFacade);
	}

	@Override
	public void delete(String linkKey) {
		if (linkKey.isEmpty()) {
			return;
		}
		diagramHelper.removeLinkByKey(linkKey);
	}

	@Override
	public void persist(String beginBlockKey,
			String beginSocketDirection, int beginSocketIndex,
			String endBlockKey, String endSocketDirection, int endSocketIndex) {
		if (beginBlockKey.isEmpty()
				|| beginSocketDirection.isEmpty() || endBlockKey.isEmpty()
				|| endSocketDirection.isEmpty()) {
			return;
		}
		if (beginBlockKey.equals(endBlockKey)
				&& beginSocketDirection.equals(endSocketDirection)
				&& beginSocketIndex == endSocketIndex) {
			return;
		}
		final Diagram diagram = diagramHelper.getDiagram();

		final Block beginBlock = diagramHelper.findBlockByKey(beginBlockKey);
		if (null == beginBlock) {
			return;
		}
		final Block endBlock = diagramHelper.findBlockByKey(endBlockKey);
		if (null == endBlock) {
			return;
		}
		final LinkSocket beginSocket = new LinkSocket(beginBlockKey,
				LinkSocket.Direction.valueOf(beginSocketDirection),
				beginSocketIndex);
		beginSocket.setLineEnd(LineEnd.SIMPLE);

		final LinkSocket endSocket = new LinkSocket(endBlockKey,
				LinkSocket.Direction.valueOf(endSocketDirection),
				endSocketIndex);
		endSocket.setLineEnd(LineEnd.SOLID_ARROW);

		Link link = new Link();
		link.setKey(UUID.randomUUID().toString());
		link.setLineColor(Link.LineColor.Black);

		link.setSockets(new ArrayList<LinkSocket>(2));
		link.getSockets().add(beginSocket);
		link.getSockets().add(endSocket);

		diagramHelper.calculatePivot(link);

		if (!diagramHelper.hasLinkWithSameEnds(link)) {
			diagram.getLinks().add(link);
		}	
	}

	@Override
	public void setPivot(String linkKey, int x, int y) {
		final Link link = diagramHelper.findLinkByKey(linkKey);
		if (null == link) {
			return;
		}
		link.setPivot(new Point(x, y));	
	}

	@Override
	public void update(String linkKey, Link link) {
		final Diagram diagram = diagramHelper.getDiagram();
		final Link savedLink = diagramHelper.findLinkByKey(linkKey);
		if (null == savedLink) {
			// Nothing to update
			return;
		}
		link.setKey(linkKey);
		link.setPivot(savedLink.getPivot());

		// Update socket types: (TODO (2013/05/07) strange code)
		int size = link.getSockets().size();
		for (int i = 0; i < size; i++) {
			savedLink.getSockets().get(i)
					.setLineEnd(link.getSockets().get(i).getLineEnd());
		}

		link.setSockets(savedLink.getSockets());

		ILinkPluginFacade pluginFacade = getLinkPluginFacade(link);
		pluginFacade.getValidator().validate(diagram, link);

		// TODO: doubtful code (2013/05/06)
		diagram.getLinks().remove(savedLink);
		diagram.getLinks().add(link);	
	}
	
	private ILinkPluginFacade getLinkPluginFacade(final Link link) {
		return serviceFacade.getLinkPluginRepository().getFacade(
				link.getClass());
	}	

}
