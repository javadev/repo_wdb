package org.wdbuilder.view;

import java.awt.Graphics2D;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.web.ApplicationState;


import static org.wdbuilder.service.DiagramService.LINE_AREA;

public class LinkRenderer {

	private final Link link;
	private final Block beginBlock;
	private final Block endBlock;
	private final Graphics2D gr;

	public LinkRenderer(Graphics2D gr, Link link, Block beginBlock, Block endBlock) {
		this.gr = gr;
		this.link = link;
		this.beginBlock = beginBlock;
		this.endBlock = endBlock;
	}

	public void render(ApplicationState.Mode mode) {
		if( null==gr || null==link || null==beginBlock || null==endBlock ) {
			return;
		}

		renderLine(getBeginLocation(), link.getBeginSocket());
		renderLine(getEndLocation(), link.getEndSocket());
		DivAnalog.render(gr, link, beginBlock, endBlock );		

		renderArrow();

		// For line mode render
		if (ApplicationState.Mode.LINE.equals(mode)) {
			// Render "update link entry point"
			final int w = LINE_AREA.getWidth();
			final int h = LINE_AREA.getHeight();
			final Point pivot = link.getPivot();
			gr.fillRect(pivot.getX() - w / 2, pivot.getY() - h / 2, w, h);
		}
	}

	private void renderLine(Point p, LinkSocket s) {
		Point o = s.getOffset(p);
		gr.drawLine(p.getX(), p.getY(), o.getX(), o.getY());
	}

	private void renderArrow() {
		final int[][] a = link.getEndSocket().getArrow(getEndLocation());
		gr.fillPolygon(a[0], a[1], 3);
	}

	private Point getEndLocation() {
		return getPoint(endBlock, link.getEndSocket());
	}

	private Point getBeginLocation() {
		return getPoint(beginBlock, link.getBeginSocket());
	}

	private static Point getPoint(Block b, LinkSocket s) {
		return s.getLocation(b);
	}

}
