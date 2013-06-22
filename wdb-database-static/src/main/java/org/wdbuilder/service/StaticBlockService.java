package org.wdbuilder.service;

import java.util.UUID;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
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
		removeByKey(blockKey, diagram.getBlocks());
		
		// Remove all related links:
		for( Link link : diagram.getLinks() ) {
			if( isBlockRelated( link, blockKey ) ) {
				diagram.getLinks().remove(link);
			}
		}
	}

	private static boolean isBlockRelated(Link link, String blockKey) {
		for( LinkSocket socket : link.getSockets() ) {
			if( blockKey.equals(socket.getBlockKey())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String persist(Block block) {
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
		final Block block = findByKey(blockKey, diagram.getBlocks());
		if (null == block) {
			// Nothing to update
			return;
		}

		Point oldLocation = new Point(block.getLocation().getX(), block
				.getLocation().getY());
		block.setLocation(new Point(x, y));

		try {
			getBlockPluginFacade(block.getClass()).getValidator().validate(
					diagram, block);
		} catch (IllegalArgumentException ex) {
			// Restore old location:
			block.setLocation(oldLocation);

			throw ex;
		}

	}

	@Override
	public void update(String blockKey, Block block) {
		final Block savedBlock = findByKey(blockKey, diagram.getBlocks());
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
