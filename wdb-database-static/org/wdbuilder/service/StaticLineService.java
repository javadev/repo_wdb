package org.wdbuilder.service;

import java.util.UUID;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.plugin.ILinkPluginFacade;

@Deprecated
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
		removeByKey(linkKey, diagram.getLinks() );
	}

	@Override
	public String persist( Link link) {
		final String key = UUID.randomUUID().toString();
		link.setKey(key);
		
		if (!hasLinkWithSameEnds(link)) {
			diagram.getLinks().add(link);
		}	
		
		return key;
	}


	@Override
	public void setPosition(String linkKey, int x, int y) {
		final Link link = findByKey(linkKey, diagram.getLinks() );
		if (null == link) {
			return;
		}
		link.setPivot(new Point(x, y));	
	}

	@Override
	public void update(String linkKey, Link link) {
		final Link savedLink = findByKey(linkKey,  diagram.getLinks() );
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
	
	private boolean hasLinkWithSameEnds(Link probeLink) {
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
