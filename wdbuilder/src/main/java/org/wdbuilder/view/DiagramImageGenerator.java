package org.wdbuilder.view;

import static org.wdbuilder.util.ImageUtility.getGraphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.service.IPluginFacadeRepository;
import org.wdbuilder.web.ApplicationState;

public class DiagramImageGenerator extends ImageGenerator {

	private final IPluginFacadeRepository<Block, IBlockPluginFacade> pluginFacadeRepository;

	public DiagramImageGenerator(ApplicationState appState,
			IPluginFacadeRepository<Block, IBlockPluginFacade> pluginFacadeRepository) {
		super(appState);
		this.pluginFacadeRepository = pluginFacadeRepository;
	}

	@Override
	protected BufferedImage generateImage(String blockKey, Diagram diagram) {
		final BufferedImage image = new BufferedImage(diagram.getSize()
				.getWidth(), diagram.getSize().getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		appState.setSelectedBlockKey(blockKey);

		final Graphics2D gr = getGraphics(image);

		final RenderContext renderCtx = new RenderContext();
		renderCtx.setOpaque(false);
		renderCtx.setGraphics(gr);

		new DiagramRenderer(appState, pluginFacadeRepository).draw(diagram,
				renderCtx);
		gr.dispose();
		return image;
	}
}
