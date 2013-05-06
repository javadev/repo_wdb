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

	public static void render(Graphics2D gr, Link link, Block beginBlock,
			Block endBlock) {

		Point begin = link.getSockets().get(0).getOffset(beginBlock);
		Point end = link.getSockets().get(1).getOffset(endBlock);
		Point pivot = link.getPivot();

		final boolean hintHorizontal = link.getSockets().get(0).isHorizontal();

		DivAnalog div1 = new DivAnalog(pivot, begin, hintHorizontal);
		DivAnalog div2 = new DivAnalog(pivot, end, !hintHorizontal);
		if (div1.intersectsWith(div2)) {
			div1 = new DivAnalog(pivot, begin, !hintHorizontal);
			div2 = new DivAnalog(pivot, end, hintHorizontal);
		}
		div1.render(gr);
		div2.render(gr);
	}

	private DivAnalog(Point pivot, Point origin, boolean horizontal) {
		this.horizontal = horizontal;
		line[0] = pivot;
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

	private static void drawLine(Graphics2D gr, Point p1, Point p2) {
		gr.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
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
