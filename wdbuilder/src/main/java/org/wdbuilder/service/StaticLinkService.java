package org.wdbuilder.service;

import java.util.UUID;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.plugin.ILinkPluginFacade;

class StaticLinkService extends StaticDiagramRelatedService implements
		EntityServiceBase<Link> {

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
	public String persist( Link link) {
		final String key = UUID.randomUUID().toString();
		link.setKey(key);
		
		if (!diagramHelper.hasLinkWithSameEnds(link)) {
			diagramHelper.getDiagram().getLinks().add(link);
		}	
		
		return key;
	}


	@Override
	public void setPosition(String linkKey, int x, int y) {
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
