package org.wdbuilder.view;

import static org.wdbuilder.util.ImageUtility.getGraphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.service.IServiceFacade;
import org.wdbuilder.web.ApplicationState;

public class DiagramImageGenerator extends ImageGenerator {

	private final IServiceFacade serviceFacade;

	public DiagramImageGenerator(ApplicationState appState,
			IServiceFacade serviceFacade) {
		super(appState);
		this.serviceFacade = serviceFacade;
	}

	@Override
	protected BufferedImage generateImage(String blockKey, Diagram diagram) {
		final BufferedImage image = new BufferedImage(diagram.getSize()
				.getWidth(), diagram.getSize().getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		final Graphics2D gr = getGraphics(image);

		final RenderContext renderCtx = new RenderContext();
		renderCtx.setMovingBlock(true);
		renderCtx.setGraphics(gr);

		new DiagramRenderer(appState, serviceFacade).draw(diagram, renderCtx);
		gr.dispose();
		return image;
	}
}
