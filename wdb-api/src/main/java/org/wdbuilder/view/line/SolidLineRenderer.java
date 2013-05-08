package org.wdbuilder.view.line;

import org.wdbuilder.view.ILineRenderer;
import org.wdbuilder.view.ILineRendererContext;

public class SolidLineRenderer implements ILineRenderer<ILineRendererContext> {

	@Override
	public void draw(ILineRendererContext renderCtx) {
		int x0 = renderCtx.getBegin().getX();
		int y0 = renderCtx.getBegin().getY();
		int x1 = renderCtx.getEnd().getX();
		int y1 = renderCtx.getEnd().getY();

		renderCtx.getGraphics().setColor(renderCtx.getColor());
		renderCtx.getGraphics().drawLine(x0, y0, x1, y1);
	}

}
