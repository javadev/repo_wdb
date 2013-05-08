package org.wdbuilder.view.line.end;

import java.awt.Graphics2D;

import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.view.ILineEndRenderer;
import org.wdbuilder.view.ILineEndRendererContext;

import static org.wdbuilder.service.DiagramService.LINE_OFFSET;

public abstract class ArrowLineEndRenderer implements ILineEndRenderer {

	protected abstract void drawArrow(Graphics2D gr, int[] x, int[] y);

	@Override
	public void draw(ILineEndRendererContext renderCtx) {
		Graphics2D gr = renderCtx.getGraphics();
		gr.setColor(renderCtx.getColor());
		Point p0 = getSmallLineStart(renderCtx);
		Point p1 = getSmallLineEnd(renderCtx);
		int[] x = getX(renderCtx);
		int[] y = getY(renderCtx);

		gr.drawLine(p0.getX(), p0.getY(), p1.getX(), p1.getY());
		drawArrow(gr, x, y);
	}

	private static Point getSmallLineStart(ILineEndRendererContext renderCtx) {
		Point base = renderCtx.getBaseLocation();
		switch (renderCtx.getDirection()) {
		case LEFT:
			return base.addX(-LINE_OFFSET);
		case RIGHT:
			return base.addX(LINE_OFFSET);
		case TOP:
			return base.addY(-LINE_OFFSET);
		case BOTTOM:
			return base.addY(LINE_OFFSET);
		default:
			throw new IllegalArgumentException("Link socket direction is null");
		}
	}

	private static Point getSmallLineEnd(ILineEndRendererContext renderCtx) {
		Point p0 = getSmallLineStart(renderCtx);
		switch (renderCtx.getDirection()) {
		case LEFT:
			return p0.addX(7);
		case RIGHT:
			return p0.addX(-7);
		case TOP:
			return p0.addY(-7);
		case BOTTOM:
			return p0.addY(7);
		default:
			throw new IllegalArgumentException("Link socket direction is null");
		}
	}

	private static int[] getX(ILineEndRendererContext renderCtx) {
		Point base = renderCtx.getBaseLocation();
		int x = base.getX();
		switch (renderCtx.getDirection()) {
		case LEFT:
			return new int[] { x, x - 7, x - 7 };
		case RIGHT:
			return new int[] { x, x + 7, x + 7 };
		case TOP:
		case BOTTOM:
			return new int[] { x, x - 3, x + 4 };
		default:
			throw new IllegalArgumentException("Link socket direction is null");
		}
	}

	private static int[] getY(ILineEndRendererContext renderCtx) {
		Point base = renderCtx.getBaseLocation();
		int y = base.getY();
		switch (renderCtx.getDirection()) {
		case LEFT:
		case RIGHT:
			return new int[] { y, y - 3, y + 4 };
		case TOP:
			return new int[] { y, y - 7, y - 7 };
		case BOTTOM:
			return new int[] { y, y + 7, y + 7 };
		default:
			throw new IllegalArgumentException("Link socket direction is null");
		}
	}

	public static class Solid extends ArrowLineEndRenderer {
		@Override
		protected void drawArrow(Graphics2D gr, int[] x, int[] y) {
			gr.fillPolygon(x, y, 3);
		}
	}
	
	public static class Transparent extends ArrowLineEndRenderer {
		@Override
		protected void drawArrow(Graphics2D gr, int[] x, int[] y) {
			gr.drawPolygon(x, y, 3);
		}
	}
	

}
