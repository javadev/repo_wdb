package org.wdbuilder.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.plugin.IPluginFacade;
import org.wdbuilder.plugin.IRenderer;
import org.wdbuilder.utility.DiagramHelper;
import org.wdbuilder.utility.IPluginFacadeRepository;
import static org.wdbuilder.util.ImageUtility.getGraphics;
import org.wdbuilder.web.ApplicationState;

public class BlockImageGenerator extends ImageGenerator {

	private final IPluginFacadeRepository pluginFacadeRepository;

	public BlockImageGenerator(ApplicationState appState,
			IPluginFacadeRepository pluginFacadeRepository) {
		super(appState);
		this.pluginFacadeRepository = pluginFacadeRepository;
	}

	@Override
	protected BufferedImage generateImage(String blockKey, Diagram diagram) {
		final Block block = new DiagramHelper(diagram).findBlockByKey(blockKey);
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

		IPluginFacade pluginFacade = pluginFacadeRepository.getFacade(block.getClass());
		IRenderer renderer = pluginFacade.getRenderer();
		renderer.draw(block, renderCtx);
		gr.dispose();
		return image;

	}

}
