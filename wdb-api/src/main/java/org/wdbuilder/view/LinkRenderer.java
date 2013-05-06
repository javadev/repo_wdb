package org.wdbuilder.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.plugin.ILinkRenderContext;
import org.wdbuilder.plugin.IRenderer;

import static org.wdbuilder.service.DiagramService.LINE_AREA;
import static org.apache.commons.lang.StringUtils.isEmpty;

public class LinkRenderer implements IRenderer<Link, ILinkRenderContext> {

	protected static final Font FONT = new Font(Font.SANS_SERIF, Font.ITALIC,
			12);

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

		final Point pivot = link.getPivot();

		renderArrow(link, renderCtx);

		// For line mode render
		if (!renderCtx.isBlockMode()) {
			// Render "update link entry point"
			final int w = LINE_AREA.getWidth();
			final int h = LINE_AREA.getHeight();
			gr.fillRect(pivot.getX() - w / 2, pivot.getY() - h / 2, w, h);
		} else {
			// Render the name:
			drawText(renderCtx, pivot, link.getName());
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

	private static void drawText(ILinkRenderContext renderCtx, Point p,
			String str) {
		if (isEmpty(str)) {
			return;
		}
		Graphics2D gr = renderCtx.getGraphics();

		final FontMetrics metrics = gr.getFontMetrics(FONT);
		final int width = metrics.stringWidth(str);
		final int height = metrics.getHeight();

		int x = p.getX() - width / 2;
		int y = p.getY() + height / 2;
		gr.setColor(renderCtx.getDiagramBackgroundColor());
		gr.fillRect(x, y - height, width, height);
		gr.setColor(Color.black);
		gr.setFont(FONT);
		gr.drawString(str, x, y);
	}

	private static Point getPoint(Block b, LinkSocket s) {
		return s.getLocation(b);
	}

}
