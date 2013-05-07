package org.wdbuilder.view.line.end;

import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.view.ILineEndRenderer;
import org.wdbuilder.view.ILineEndRendererContext;

import static org.wdbuilder.service.DiagramService.LINE_OFFSET;

public class DefaultLineEndRenderer implements ILineEndRenderer {

	@Override
	public void draw(ILineEndRendererContext renderCtx) {
		renderCtx.getGraphics().setColor(renderCtx.getColor());
		Point p0 = renderCtx.getBaseLocation();
		Point p1;
		switch (renderCtx.getDirection()) {
		case LEFT:
			p1 = p0.addX(-LINE_OFFSET);
			break;
		case RIGHT:
			p1 = p0.addX(LINE_OFFSET);
			break;
		case TOP:
			p1 = p0.addX(-LINE_OFFSET);
			break;
		case BOTTOM:
			p1 = p0.addX(LINE_OFFSET);
			break;
		default:
			throw new IllegalArgumentException("Link socket direction is null");
		}
		renderCtx.getGraphics().drawLine(p0.getX(), p0.getY(), p1.getX(),
				p1.getY());
	}

}
