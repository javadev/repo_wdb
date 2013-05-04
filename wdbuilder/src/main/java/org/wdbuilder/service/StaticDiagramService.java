package org.wdbuilder.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.DiagramBackground;
import org.wdbuilder.domain.SizedEntity;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.service.validator.DiagramValidator;
import org.wdbuilder.utility.DiagramHelper;
import org.wdbuilder.utility.IPluginFacadeRepository;

public class StaticDiagramService implements DiagramService {

	private static StaticDiagramService instance = new StaticDiagramService();

	private IPluginFacadeRepository pluginRepository;

	public static final DiagramService getInstance() {
		return instance;
	}

	private final Map<String, Diagram> diagrams = new LinkedHashMap<String, Diagram>(
			2);

	private StaticDiagramService() {
	}

	@Override
	public Collection<Diagram> getDiagrams() {
		return diagrams.values();
	}

	@Override
	public Diagram getDiagram(String diagramKey) {
		return diagrams.get(diagramKey);
	}

	@Override
	public void updateDiagramSize(String diagramKey, int width, int height) {
		final Diagram diagram = getDiagram(diagramKey);
		if (null == diagram) {
			return;
		}
		Dimension oldSize = new Dimension(diagram.getSize().getWidth(), diagram
				.getSize().getHeight());
		diagram.setSize(new Dimension(width, height));
		try {
			new DiagramValidator().validate(diagram, null);
		} catch (IllegalArgumentException ex) {
			// Restore old size:
			diagram.setSize(oldSize);
			throw ex;
		}
	}

	@Override
	public String persistDiagram(String name, int width, int height,
			String backgroundKey) {
		DiagramBackground background = DiagramBackground.valueOf(backgroundKey);
		Diagram diagram = createDiagram(UUID.randomUUID().toString(), name,
				width, height, background);
		new DiagramValidator().validate(diagram, null);
		return saveDiagram(diagram);
	}

	@Override
	public void updateDiagram(String key, String name, String backgroundKey) {
		Diagram diagram = getDiagram(key);
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
			new DiagramValidator().validate(diagram, null);
		} catch (IllegalArgumentException ex) {
			// Restore old values:
			diagram.setName(oldName);
			diagram.setBackground(oldBackground);

			throw ex;
		}
	}

	@Override
	public void deleteDiagram(String key) {
		diagrams.remove(key);
	}

	// }}} DIAGRAM

	// BLOCK {{{
	@Override
	public void updateBlockPosition(String diagramKey, String blockKey,
			int offsetX, int offsetY) {
		final Diagram diagram = getDiagram(diagramKey);
		if (null == diagram) {
			return;
		}
		final DiagramHelper diagramHelper = new DiagramHelper(diagram);
		final Block block = diagramHelper.findBlockByKey(blockKey);
		if (null == block) {
			// Nothing to update
			return;
		}

		IBlockPluginFacade pluginFacade = pluginRepository.getFacade(block
				.getClass());
		// Save old values:
		Point oldLocation = new Point(block.getLocation().getX(), block
				.getLocation().getY());
		block.setLocation(new Point(offsetX, offsetY));

		try {
			pluginFacade.getValidator().validate(diagram, block);
		} catch (IllegalArgumentException ex) {
			// Restore old location:
			block.setLocation(oldLocation);

			throw ex;
		}
	}

	@Override
	public void deleteBlock(String diagramKey, String blockKey) {
		if (diagramKey.isEmpty() || blockKey.isEmpty()) {
			return;
		}
		final Diagram diagram = getDiagram(diagramKey);
		if (null == diagram) {
			return;
		}
		new DiagramHelper(diagram).removeBlockByKey(blockKey);
	}

	@Override
	public String persistBlock(String diagramKey, Block block) {

		final Diagram diagram = getDiagram(diagramKey);
		if (null == diagram) {
			return null;
		}

		final int offsetX = diagram.getSize().getWidth() / 2;
		final int offsetY = diagram.getSize().getHeight() / 2;

		final String key = UUID.randomUUID().toString();
		block.setKey(key);
		block.setLocation(new Point(offsetX, offsetY));

		IBlockPluginFacade pluginFacade = pluginRepository.getFacade(block
				.getClass());
		pluginFacade.getValidator().validate(diagram, block);

		diagram.getBlocks().add(block);

		return key;
	}

	@Override
	public void updateBlock(String diagramKey, String blockKey, Block block) {
		final Diagram diagram = getDiagram(diagramKey);
		if (null == diagram) {
			return;
		}

		final DiagramHelper diagramHelper = new DiagramHelper(diagram);
		final Block savedBlock = diagramHelper.findBlockByKey(blockKey);
		if (null == savedBlock) {
			// Nothing to update
			return;
		}
		block.setKey(blockKey);
		block.setLocation(savedBlock.getLocation());

		IBlockPluginFacade pluginFacade = pluginRepository.getFacade(block
				.getClass());
		pluginFacade.getValidator().validate(diagram, block);

		// TODO: doubtful code (2013/04/29)
		diagram.getBlocks().remove(savedBlock);
		diagram.getBlocks().add(block);
	}

	// }}} BLOCK

	// LINK {{{
	@Override
	public void persistLink(String diagramKey, String beginBlockKey,
			String beginSocketDirection, int beginSocketIndex,
			String endBlockKey, String endSocketDirection, int endSocketIndex) {
		if (diagramKey.isEmpty() || beginBlockKey.isEmpty()
				|| beginSocketDirection.isEmpty() || endBlockKey.isEmpty()
				|| endSocketDirection.isEmpty()) {
			return;
		}
		if (beginBlockKey.equals(endBlockKey)
				&& beginSocketDirection.equals(endSocketDirection)
				&& beginSocketIndex == endSocketIndex) {
			return;
		}
		final Diagram diagram = getDiagram(diagramKey);
		if (null == diagram) {
			return;
		}
		DiagramHelper diagramHelper = new DiagramHelper(diagram);

		final Block beginBlock = diagramHelper.findBlockByKey(beginBlockKey);
		if (null == beginBlock) {
			return;
		}
		final Block endBlock = diagramHelper.findBlockByKey(endBlockKey);
		if (null == endBlock) {
			return;
		}
		final LinkSocket beginSocket = new LinkSocket(
				LinkSocket.Direction.valueOf(LinkSocket.Direction.class,
						beginSocketDirection), beginSocketIndex);
		final LinkSocket endSocket = new LinkSocket(
				LinkSocket.Direction.valueOf(LinkSocket.Direction.class,
						endSocketDirection), endSocketIndex);

		Link link = new Link();
		link.setKey(UUID.randomUUID().toString());
		link.setBeginKey(beginBlockKey);
		link.setEndKey(endBlockKey);
		link.setBeginSocket(beginSocket);
		link.setEndSocket(endSocket);

		diagramHelper.calculatePivot(link);

		// TODO: check for doubling:
		if (!diagramHelper.hasLinkWithSameEnds(link)) {
			diagram.getLinks().add(link);
		}
	}

	@Override
	public void updateLink(String diagramKey, String linkKey, int x, int y) {

		if (diagramKey.isEmpty() || linkKey.isEmpty()) {
			return;
		}
		final Diagram diagram = getDiagram(diagramKey);
		if (null == diagram) {
			return;
		}
		final DiagramHelper diagramHelper = new DiagramHelper(diagram);
		final Link link = diagramHelper.findLinkByKey(linkKey);
		if (null == link) {
			return;
		}
		link.setPivot(new Point(x, y));
	}

	@Override
	public void deleteLink(String diagramKey, String linkKey) {
		if (diagramKey.isEmpty() || linkKey.isEmpty()) {
			return;
		}
		final Diagram diagram = getDiagram(diagramKey);
		if (null == diagram) {
			return;
		}
		new DiagramHelper(diagram).removeLinkByKey(linkKey);
	}

	// }}} LINK

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

	public void setPluginRepository(IPluginFacadeRepository pluginRepository) {
		this.pluginRepository = pluginRepository;
	}
}
