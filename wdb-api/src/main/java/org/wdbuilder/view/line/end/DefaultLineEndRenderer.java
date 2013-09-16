package org.wdbuilder.view.line.end;

import java.awt.Graphics2D;

import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.view.ILineRenderer;
import org.wdbuilder.view.ILineEndRendererContext;

import static org.wdbuilder.service.DiagramService.LINE_OFFSET;

public class DefaultLineEndRenderer implements
        ILineRenderer<ILineEndRendererContext> {

    @Override
    public void draw(ILineEndRendererContext renderCtx) {
        Graphics2D gr = renderCtx.getGraphics();
        gr.setColor(renderCtx.getColor());
        Point p0 = renderCtx.getBaseLocation();
        Point p1 = getNextPoint(renderCtx);
        gr.drawLine(p0.getX(), p0.getY(), p1.getX(), p1.getY());
    }

    private Point getNextPoint(ILineEndRendererContext renderCtx) {
        Point p0 = renderCtx.getBaseLocation();
        switch (renderCtx.getDirection()) {
        case LEFT:
            return p0.addX(-LINE_OFFSET);
        case RIGHT:
            return p0.addX(LINE_OFFSET);
        case TOP:
            return p0.addY(-LINE_OFFSET);
        case BOTTOM:
            return p0.addY(LINE_OFFSET);
        default:
            throw new IllegalArgumentException("Link socket direction is null");
        }
    }

}
