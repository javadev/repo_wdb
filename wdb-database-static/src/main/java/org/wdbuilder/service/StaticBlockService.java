package org.wdbuilder.service;

import java.util.UUID;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.plugin.IBlockPluginFacade;

class StaticBlockService extends StaticDiagramRelatedService<Block> implements
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
		for (Link link : diagram.getLinks()) {
			if (isBlockRelated(link, blockKey)) {
				diagram.getLinks().remove(link);
			}
		}
	}

	@Override
	public String persist(Block block) {
		block.setKey(UUID.randomUUID().toString());
		block.setLocation(getCenterOfDiagram());
		validate(block);
		diagram.getBlocks().add(block);
		return block.getKey();
	}

	@Override
	public void setPosition(String blockKey, int x, int y) {
		final Block block = diagram.getBlock(blockKey);
		if (null == block) {
			// Nothing to update
			return;
		}

		Point oldLocation = new Point(block.getLocation().getX(), block
				.getLocation().getY());
		block.setLocation(new Point(x, y));

		try {
			validate(block);
		} catch (IllegalArgumentException ex) {
			// Restore old location:
			block.setLocation(oldLocation);

			throw ex;
		}

	}

	@Override
	public void update(String blockKey, Block block) {
		final Block savedBlock = diagram.getBlock(blockKey);
		if (null == savedBlock) {
			// Nothing to update
			return;
		}
		block.setKey(blockKey);
		block.setLocation(savedBlock.getLocation());

		validate(block);

		// TODO: doubtful code (2013/04/29)
		diagram.getBlocks().remove(savedBlock);
		diagram.getBlocks().add(block);
	}
	
	@Override
	protected void validate(Block block) {
		getBlockPluginFacade(block).getValidator().validate(diagram, block);
	}	

	private IBlockPluginFacade getBlockPluginFacade(Block block) {
		return serviceFacade.getBlockPluginRepository().getFacade(
				block.getClass());
	}

	private Point getCenterOfDiagram() {
		final int offsetX = diagram.getSize().getWidth() / 2;
		final int offsetY = diagram.getSize().getHeight() / 2;
		final Point location = new Point(offsetX, offsetY);
		return location;
	}

	private static boolean isBlockRelated(Link link, String blockKey) {
		for (LinkSocket socket : link.getSockets()) {
			if (blockKey.equals(socket.getBlockKey())) {
				return true;
			}
		}
		return false;
	}

}
