package org.wdbuilder.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.DiagramBackground;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.SizedEntity;
import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.service.validator.DiagramValidator;

class StaticDiagramService implements DiagramService {

  private final Map<String, Diagram> diagrams = new LinkedHashMap<String, Diagram>(
			2);

	private final IServiceFacade serviceFacade;

	StaticDiagramService(IServiceFacade serviceFacade) {
		this.serviceFacade = serviceFacade;
	}	

	@Override
	public Collection<Diagram> retrieveList() {
		return diagrams.values();
	}

	@Override
	public Diagram get(String diagramKey) {
		return diagrams.get(diagramKey);
	}

	@Override
	public void setSize(String diagramKey, int width, int height) {
		final Diagram diagram = get(diagramKey);
		if (null == diagram) {
			return;
		}
		Dimension oldSize = new Dimension(diagram.getSize().getWidth(), diagram
				.getSize().getHeight());
		diagram.setSize(new Dimension(width, height));
		try {
			new DiagramValidator( diagram ).validate(diagram, null);
		} catch (IllegalArgumentException ex) {
			// Restore old size:
			diagram.setSize(oldSize);
			throw ex;
		}
	}

	@Override
	public String persist(String name, String backgroundKey) {
		DiagramBackground background = DiagramBackground.valueOf(backgroundKey);
		Diagram diagram = createDiagram(UUID.randomUUID().toString(), name,
				320, 240, background);
		new DiagramValidator( diagram ).validate(diagram, null);
		return saveDiagram(diagram);
	}

	@Override
	public void update(String key, String name, String backgroundKey) {
		Diagram diagram = get(key);
		if (null == diagram) {
			return;
		}
		String oldName = diagram.getName();
		diagram.setName(name);
		DiagramBackground oldBackground = diagram.getBackground();
		final DiagramBackground background = DiagramBackground
				.valueOf(backgroundKey);
		diagram.setBackground(background);

		try {
			new DiagramValidator( diagram ).validate(diagram, null);
		} catch (IllegalArgumentException ex) {
			// Restore old values:
			diagram.setName(oldName);
			diagram.setBackground(oldBackground);

			throw ex;
		}
	}

	@Override
	public void delete(String key) {
		diagrams.remove(key);
	}
	
	@Override
	public void upload(Diagram diagram) {
		diagrams.put( diagram.getKey(), diagram );
	}	
	
	@Override
	public EntityServiceBase<Block> getBlockService( String diagramKey ) {
		return new StaticBlockService( get( diagramKey ), serviceFacade );
	}
	
	@Override
	public EntityServiceBase<Link> getLinkService( String diagramKey ) {
		return new StaticLinkService( get( diagramKey ), serviceFacade);
	}

	// }}} DIAGRAM

	private static final Diagram createDiagram(String id, String name,
			int width, int height, DiagramBackground background) {
		Diagram result = new Diagram();
		fillEntity(result, id, name, width, height);
		result.setBlocks(new ArrayList<Block>(2));
		result.setLinks(new ArrayList<Link>(2));
		result.setBackground(background);
		return result;
	}

	private final String saveDiagram(Diagram diagram) {
		if (null == diagram) {
			return null;
		}
		String key = diagram.getKey();
		diagrams.put(key, diagram);
		return key;
	}

	private static final void fillEntity(SizedEntity result, String id,
			String name, int width, int height) {
		result.setKey(id);
		result.setName(name);
		result.setSize(new Dimension(width, height));
	}

}
