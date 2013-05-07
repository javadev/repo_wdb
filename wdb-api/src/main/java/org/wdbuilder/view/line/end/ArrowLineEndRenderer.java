package org.wdbuilder.view.line.end;

import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.view.ILineEndRenderer;
import org.wdbuilder.view.ILineEndRendererContext;

import static org.wdbuilder.service.DiagramService.LINE_OFFSET;

public class ArrowLineEndRenderer implements ILineEndRenderer {

  @Override
	public void draw(ILineEndRendererContext renderCtx) {
		renderCtx.getGraphics().setColor(renderCtx.getColor());
		int[] x;
		int[] y;
		Point base = renderCtx.getBaseLocation();
		int px = base.getX();
		int py = base.getY();
		Point p0, p1;

		switch (renderCtx.getDirection()) {
		case LEFT:
			p0 = base.addX(-LINE_OFFSET);
			p1 = p0.addX(7);
			x = new int[] { px, px - 7, px - 7 };
			y = new int[] { py, py - 3, py + 4 };
			break;
		case RIGHT:
			p0 = base.addX(LINE_OFFSET);
			p1 = p0.addX(-7);
			x = new int[] { px, px + 7, px + 7 };
			y = new int[] { py, py - 3, py + 4 };
			break;
		case TOP:
			p0 = base.addY(-LINE_OFFSET);
			p1 = p0.addY(7);
			x = new int[] { px, px - 3, px + 4 };
			y = new int[] { py, py - 7, py - 7 };
			break;
		case BOTTOM:
			p0 = base.addY(LINE_OFFSET);
			p1 = p0.addY(-7);
			x = new int[] { px, px - 3, px + 4 };
			y = new int[] { py, py + 7, py + 7 };
			break;
		default:
			throw new IllegalArgumentException("Link socket direction is null");
		}
		renderCtx.getGraphics().drawLine(p0.getX(), p0.getY(), p1.getX(),
				p1.getY());
		renderCtx.getGraphics().fillPolygon(x, y, 3);
	}

}
