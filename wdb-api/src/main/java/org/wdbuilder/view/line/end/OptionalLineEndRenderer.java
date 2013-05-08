package org.wdbuilder.view.line.end;

import java.awt.Graphics2D;

import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.view.ILineRenderer;
import org.wdbuilder.view.ILineEndRendererContext;

import static org.wdbuilder.service.DiagramService.LINE_OFFSET;

public abstract class OptionalLineEndRenderer implements
		ILineRenderer<ILineEndRendererContext> {

	protected abstract void drawCrossingAngle(ILineEndRendererContext renderCtx);

	@Override
	public void draw(ILineEndRendererContext renderCtx) {
		Graphics2D gr = renderCtx.getGraphics();
		gr.setColor(renderCtx.getColor());
		Point b = renderCtx.getBaseLocation();
		Point p0 = getOffset(renderCtx, LINE_OFFSET);
		Point p1 = getOffset(renderCtx, 8);
		Point p2 = getOffset(renderCtx, 4);

		gr.drawLine(b.getX(), b.getY(), p2.getX(), p2.getY());
		gr.drawLine(p1.getX(), p1.getY(), p0.getX(), p0.getY());

		Point ul = getUpperLeft(renderCtx);
		gr.drawOval(ul.getX(), ul.getY(), 4, 4);
		drawCrossingAngle(renderCtx);
	}

	private static Point getOffset(ILineEndRendererContext renderCtx, int offset) {
		Point p0 = renderCtx.getBaseLocation();
		switch (renderCtx.getDirection()) {
		case LEFT:
			return p0.addX(-offset);
		case RIGHT:
			return p0.addX(offset);
		case TOP:
			return p0.addY(-offset);
		case BOTTOM:
			return p0.addY(offset);
		default:
			throw new IllegalArgumentException("Link socket direction is null");
		}
	}

	private static Point getUpperLeft(ILineEndRendererContext renderCtx) {
		Point p0 = renderCtx.getBaseLocation();
		switch (renderCtx.getDirection()) {
		case LEFT:
			return p0.addX(-8).addY(-2);
		case RIGHT:
			return p0.addX(4).addY(-2);
		case TOP:
			return p0.addY(-8).addX(-2);
		case BOTTOM:
			return p0.addY(4).addX(-2);
		default:
			throw new IllegalArgumentException("Link socket direction is null");
		}
	}

	public static class One extends OptionalLineEndRenderer {

		@Override
		protected void drawCrossingAngle(ILineEndRendererContext renderCtx) {
			// Do nothing
		}

	}

	private static final int[] STEPS = { -5, 5 };

	public static class Many extends OptionalLineEndRenderer {

		@Override
		protected void drawCrossingAngle(ILineEndRendererContext renderCtx) {
			Point b = renderCtx.getBaseLocation();
			Point p = getOffset(renderCtx, 4);
			Graphics2D gr = renderCtx.getGraphics();
			switch (renderCtx.getDirection()) {
			case LEFT:
			case RIGHT:
				for (int n : STEPS) {
					gr.drawLine(p.getX(), p.getY(), b.getX(), b.getY() + n);
				}
				break;
			case TOP:
			case BOTTOM:
				for (int n : STEPS) {
					gr.drawLine(p.getX(), p.getY(), b.getX() + n, b.getY());
				}
				break;
			default:
				throw new IllegalArgumentException(
						"Link socket direction is null");
			}
		}

	}

}
