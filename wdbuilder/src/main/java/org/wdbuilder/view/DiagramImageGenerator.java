package org.wdbuilder.view;

import static org.wdbuilder.util.ImageUtility.getGraphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.utility.IPluginFacadeRepository;
import org.wdbuilder.web.ApplicationState;

public class DiagramImageGenerator extends ImageGenerator {

	private final IPluginFacadeRepository pluginFacadeRepository;

	public DiagramImageGenerator(ApplicationState appState,
			IPluginFacadeRepository pluginFacadeRepository) {
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
