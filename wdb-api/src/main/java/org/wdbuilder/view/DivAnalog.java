package org.wdbuilder.view;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Graphics2D;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.helper.Point;

/**
 * Behavior of line drawer based on &lt;div&gt; HTML element
 * 
 * @author o.pavloschuk
 * 
 */
public class DivAnalog {

	private final boolean horizontal;
	private final Point[] line = new Point[3];
	private final Link link;

	public static void render(Graphics2D gr, Link link, Block beginBlock,
			Block endBlock) {

		Point begin = link.getSockets().get(0).getOffset(beginBlock);
		Point end = link.getSockets().get(1).getOffset(endBlock);

		final boolean hintHorizontal = link.getSockets().get(0).isHorizontal();

		DivAnalog div1 = new DivAnalog(link, begin, hintHorizontal);
		DivAnalog div2 = new DivAnalog(link, end, !hintHorizontal);
		if (div1.intersectsWith(div2)) {
			div1 = new DivAnalog(link, begin, !hintHorizontal);
			div2 = new DivAnalog(link, end, hintHorizontal);
		}
		div1.render(gr);
		div2.render(gr);
	}
	
	public static Point[] getLine( Link link, Block beginBlock,
			Block endBlock) {
		
		Point begin = link.getSockets().get(0).getOffset(beginBlock);
		Point end = link.getSockets().get(1).getOffset(endBlock);

		final boolean hintHorizontal = link.getSockets().get(0).isHorizontal();

		DivAnalog div1 = new DivAnalog(link, begin, hintHorizontal);
		DivAnalog div2 = new DivAnalog(link, end, !hintHorizontal);
		if (div1.intersectsWith(div2)) {
			div1 = new DivAnalog(link, begin, !hintHorizontal);
			div2 = new DivAnalog(link, end, hintHorizontal);
		}
		
		Point[] result = new Point[6];
		result[0] = div1.line[0];
		result[1] = div1.line[1];
		result[2] = div1.line[2];
		result[3] = div2.line[0];
		result[4] = div2.line[1];
		result[5] = div2.line[2];
		
		return result;
	}

	private DivAnalog(Link link, Point origin, boolean horizontal) {
		this.link = link;
		this.horizontal = horizontal;
		line[0] = link.getPivot();
		line[2] = origin;
		line[1] = getMiddle();
	}

	private Point getMiddle() {
		final int px = getPivot().getX();
		final int py = getPivot().getY();
		final int ox = getOrigin().getX();
		final int oy = getOrigin().getY();
		return horizontal ? new Point(px, oy) : new Point(ox, py);
	}

	private Point getPivot() {
		return line[0];
	}

	private Point getOrigin() {
		return line[2];
	}

	private void render(Graphics2D gr) {
		for (int i = 0; i < line.length - 1; i++) {
			drawLine(gr, line[i], line[i + 1]);
		}
	}

	private void drawLine(Graphics2D gr, Point p1, Point p2) {
		LineRendererContext renderCtx = new LineRendererContext();
		renderCtx.setGraphics(gr);
		renderCtx.setColor(link.getLineColor().getForegroundColor());
		renderCtx.setBegin(p1);
		renderCtx.setEnd(p2);

		ILineRenderer<ILineRendererContext> lineRenderer = link.getLineStyle()
				.getRenderer();
		lineRenderer.draw(renderCtx);
	}

	private boolean intersectsWith(DivAnalog another) {
		if (horizontal) {
			return isBetween(another.getPivot().getX(), this.getPivot().getX(),
					this.getOrigin().getX());
		} else {
			return isBetween(another.getPivot().getY(), this.getPivot().getY(),
					this.getOrigin().getY());
		}
	}

	private static boolean isBetween(int m, int n1, int n2) {
		return m >= min(n1, n2) && m <= max(n1, n2);
	}
}
