package org.wdbuilder.view;

import java.awt.Graphics2D;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.plugin.ILinkRenderContext;
import org.wdbuilder.plugin.IRenderer;

import static org.wdbuilder.service.DiagramService.LINE_AREA;

public class LinkRenderer implements IRenderer<Link, ILinkRenderContext> {

	@Override
	public void draw(Link link, ILinkRenderContext renderCtx) {
		final Graphics2D gr = renderCtx.getGraphics();
		final LinkSocket socket0 = link.getSockets().get(0);
		final LinkSocket socket1 = link.getSockets().get(1);

		final Block block0 = renderCtx.getBlock(socket0.getBlockKey());
		final Block block1 = renderCtx.getBlock(socket1.getBlockKey());

		renderLine(link, renderCtx, 0);
		renderLine(link, renderCtx, 1);
		DivAnalog.render(gr, link, block0, block1);

		renderArrow(link, renderCtx);

		// For line mode render
		if (!renderCtx.isBlockMode()) {
			// Render "update link entry point"
			final int w = LINE_AREA.getWidth();
			final int h = LINE_AREA.getHeight();
			final Point pivot = link.getPivot();
			gr.fillRect(pivot.getX() - w / 2, pivot.getY() - h / 2, w, h);
		}
	}

	private void renderLine(Link link, ILinkRenderContext renderCtx, int index) {
		final LinkSocket s = link.getSockets().get(index);
		final Point p = getLocation(link, renderCtx, index);
		Point o = s.getOffset(p);
		renderCtx.getGraphics()
				.drawLine(p.getX(), p.getY(), o.getX(), o.getY());
	}

	private void renderArrow(Link link, ILinkRenderContext renderCtx) {
		Point p = getLocation(link, renderCtx, 1);
		final int[][] a = link.getSockets().get(1).getArrow(p);
		renderCtx.getGraphics().fillPolygon(a[0], a[1], 3);
	}

	private Point getLocation(Link link, ILinkRenderContext renderCtx, int index) {
		final LinkSocket socket = link.getSockets().get(index);
		final Block block = renderCtx.getBlock(socket.getBlockKey());
		return getPoint(block, socket);
	}

	private static Point getPoint(Block b, LinkSocket s) {
		return s.getLocation(b);
	}

}
