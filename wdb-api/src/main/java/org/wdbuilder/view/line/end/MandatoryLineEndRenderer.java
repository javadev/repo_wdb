package org.wdbuilder.view.line.end;

import java.awt.Graphics2D;

import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.view.ILineEndRenderer;
import org.wdbuilder.view.ILineEndRendererContext;

import static org.wdbuilder.service.DiagramService.LINE_OFFSET;

public class Mandatory1to1LineEndRenderer implements ILineEndRenderer {

	@Override
	public void draw(ILineEndRendererContext renderCtx) {
		Graphics2D gr = renderCtx.getGraphics();
		gr.setColor(renderCtx.getColor());
		Point base = renderCtx.getBaseLocation();
		Point p0 = getSmallLineStart(renderCtx);
		Point[] across = getAcross(renderCtx);

		gr.drawLine(base.getX(), base.getY(), p0.getX(), p0.getY());
		gr.drawLine(across[0].getX(), across[0].getY(), across[1].getX(),
				across[1].getY());
	}

	private static Point getSmallLineStart(ILineEndRendererContext renderCtx) {
		return getOffset(renderCtx, LINE_OFFSET);
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
	
	private static Point[] getAcross(ILineEndRendererContext renderCtx) {
		Point[] result = new Point[2];
		Point p = renderCtx.getBaseLocation();
		switch (renderCtx.getDirection()) {
		case LEFT:
			result[0] = new Point( p.getX() - 7, p.getY() - 5 );
			result[1] = new Point( p.getX() - 7, p.getY() + 5 );
			break;
		case RIGHT:
			result[0] = new Point( p.getX() + 7, p.getY() - 5 );
			result[1] = new Point( p.getX() + 7, p.getY() + 5 );
			break;
		case TOP:
			result[0] = new Point( p.getX() - 5, p.getY() - 7 );
			result[1] = new Point( p.getX() + 5, p.getY() - 7 );
			break;
		case BOTTOM:
			result[0] = new Point( p.getX() - 5, p.getY() + 7 );
			result[1] = new Point( p.getX() + 5, p.getY() + 7 );
			break;
		default:
			throw new IllegalArgumentException("Link socket direction is null");
		}
		return result;
	}
}
