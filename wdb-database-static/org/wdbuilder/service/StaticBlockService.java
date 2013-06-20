package org.wdbuilder.service;

import java.util.UUID;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.plugin.IBlockPluginFacade;

class StaticBlockService extends StaticDiagramRelatedService implements
  	EntityServiceBase<Block> {

	StaticBlockService(Diagram diagram, IServiceFacade serviceFacade) {
		super(diagram, serviceFacade);
	}

	@Override
	public void delete(String blockKey) {
		if (blockKey.isEmpty()) {
			return;
		}
		diagramHelper.removeBlockByKey(blockKey);
	}

	@Override
	public String persist(Block block) {
		final Diagram diagram = diagramHelper.getDiagram();
		final int offsetX = diagram.getSize().getWidth() / 2;
		final int offsetY = diagram.getSize().getHeight() / 2;

		final String key = UUID.randomUUID().toString();
		block.setKey(key);
		block.setLocation(new Point(offsetX, offsetY));

		getBlockPluginFacade(block.getClass()).getValidator().validate(diagram,
				block);

		diagram.getBlocks().add(block);

		return key;

	}

	@Override
	public void setPosition(String blockKey, int x, int y) {
		final Block block = diagramHelper.findBlockByKey(blockKey);
		if (null == block) {
			// Nothing to update
			return;
		}

		Point oldLocation = new Point(block.getLocation().getX(), block
				.getLocation().getY());
		block.setLocation(new Point(x, y));

		try {
			getBlockPluginFacade(block.getClass()).getValidator().validate(
					diagramHelper.getDiagram(), block);
		} catch (IllegalArgumentException ex) {
			// Restore old location:
			block.setLocation(oldLocation);

			throw ex;
		}

	}

	@Override
	public void update(String blockKey, Block block) {
		final Diagram diagram = diagramHelper.getDiagram();
		final Block savedBlock = diagramHelper.findBlockByKey(blockKey);
		if (null == savedBlock) {
			// Nothing to update
			return;
		}
		block.setKey(blockKey);
		block.setLocation(savedBlock.getLocation());

		getBlockPluginFacade(block.getClass()).getValidator().validate(diagram,
				block);

		// TODO: doubtful code (2013/04/29)
		diagram.getBlocks().remove(savedBlock);
		diagram.getBlocks().add(block);
	}

	private IBlockPluginFacade getBlockPluginFacade(final Class<?> klass) {
		return serviceFacade.getBlockPluginRepository().getFacade(klass);
	}

}
