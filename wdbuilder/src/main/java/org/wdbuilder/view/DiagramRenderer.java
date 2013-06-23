package org.wdbuilder.view;

import static org.wdbuilder.service.DiagramService.RESIZE_AREA;
import static org.wdbuilder.util.ImageUtility.getImageObserver;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.plugin.ILinkPluginFacade;
import org.wdbuilder.plugin.ILinkRenderContext;
import org.wdbuilder.plugin.IRenderContext;
import org.wdbuilder.plugin.IRenderer;
import org.wdbuilder.service.IPluginFacadeRepository;
import org.wdbuilder.service.IServiceFacade;
import org.wdbuilder.web.ApplicationState;

import com.google.common.io.Resources;

public class DiagramRenderer implements IRenderer<Diagram, IRenderContext> {

	private static final Logger LOG = Logger.getLogger(DiagramRenderer.class);

	private final ApplicationState appState;
	private final IPluginFacadeRepository<Block, IBlockPluginFacade, IRenderContext> blockPluginFacadeRepository;
	private final IPluginFacadeRepository<Link, ILinkPluginFacade, ILinkRenderContext> linkPluginFacadeRepository;

	public DiagramRenderer(ApplicationState appState,
			IServiceFacade serviceFacade) {
		this.appState = appState;
		this.blockPluginFacadeRepository = serviceFacade
				.getBlockPluginRepository();
		this.linkPluginFacadeRepository = serviceFacade
				.getLinkPluginRepository();
	}

	@Override
	public void draw(Diagram diagram, IRenderContext diagramRenderCtx) {

		Graphics2D gr = diagramRenderCtx.getGraphics();

		final Rectangle rect = new Rectangle(diagramRenderCtx.getOffset()
				.toAWT(), diagram.getSize().toAWT());
		new SimpleBackgroundRenderer(diagram.getBackground()).render(gr, rect);

		if (appState.isBlockMode()) {
			// Load "resize corner":
			Image resizeCornerImage = loadImageResource("images/resize-corner.png");
			if (null != resizeCornerImage) {
				int x = diagram.getSize().getWidth() - RESIZE_AREA.getWidth();
				int y = diagram.getSize().getHeight() - RESIZE_AREA.getHeight();
				gr.drawImage(resizeCornerImage, x, y, getImageObserver());
			}
		}

		final RenderContext blockCtx = new RenderContext(diagramRenderCtx,
				appState);
		blockCtx.setOpaque(true);
		blockCtx.setGraphics(gr);
		for (final Block block : diagram.getBlocks()) {
			blockCtx.getOffset().setX(block.getLocation().getX());
			blockCtx.getOffset().setY(block.getLocation().getY());

			IBlockPluginFacade pluginFacade = blockPluginFacadeRepository
					.getFacade(block.getClass());
			IRenderer<Block, IRenderContext> renderer = pluginFacade
					.getRenderer();
			renderer.draw(block, blockCtx);
		}

		final LinkRenderContext linkRenderCtx = new LinkRenderContext(appState,
				gr);

		// TODO: draw more than 1 to 1 link (2013/05/05)
		for (final Link link : diagram.getLinks()) {

			final ILinkPluginFacade linkPluginFacade = linkPluginFacadeRepository
					.getFacade(link.getClass());
			IRenderer<Link, ILinkRenderContext> linkRenderer = linkPluginFacade
					.getRenderer();
			linkRenderer.draw(link, linkRenderCtx);
		}
	}

	private static Image loadImageResource(String resourceURI) {
		try {
			URL imageURL = Resources.getResource(resourceURI);
			return ImageIO.read(imageURL);
		} catch (Exception ex) {
			LOG.error("Can't load image resource: " + resourceURI, ex);
			return null;
		}
	}
}
