package org.wdbuilder.view;

import static org.wdbuilder.util.ImageUtility.getGraphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.plugin.IRenderContext;
import org.wdbuilder.plugin.IRenderer;
import org.wdbuilder.service.IPluginFacadeRepository;
import org.wdbuilder.web.ApplicationState;

public class BlockImageGenerator extends ImageGenerator {

	private final IPluginFacadeRepository<Block, IBlockPluginFacade, IRenderContext> pluginFacadeRepository;

	public BlockImageGenerator(
			ApplicationState appState,
			IPluginFacadeRepository<Block, IBlockPluginFacade, IRenderContext> pluginFacadeRepository) {
		super(appState);
		this.pluginFacadeRepository = pluginFacadeRepository;
	}

	@Override
	protected BufferedImage generateImage(String blockKey, Diagram diagram) {
		final Block block = diagram.getBlock(blockKey);
		if (null == block) {
			return null;
		}

		final BufferedImage image = new BufferedImage(block.getSize()
				.getWidth(), block.getSize().getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		final Graphics2D gr = getGraphics(image);

		int offsetX = block.getSize().getWidth() / 2;
		int offsetY = block.getSize().getHeight() / 2;

		final RenderContext renderCtx = new RenderContext();
		renderCtx.setOpaque(false);
		renderCtx.getOffset().setX(offsetX);
		renderCtx.getOffset().setY(offsetY);
		renderCtx.setAppState(appState);
		renderCtx.setGraphics(gr);

		IBlockPluginFacade pluginFacade = pluginFacadeRepository
				.getFacade(block.getClass());
		IRenderer<Block, IRenderContext> renderer = pluginFacade.getRenderer();
		renderer.draw(block, renderCtx);
		gr.dispose();
		return image;

	}

}
