package org.wdbuilder.view.line;

import java.awt.Graphics2D;

import org.wdbuilder.view.ILineRenderer;
import org.wdbuilder.view.ILineRendererContext;

import static java.lang.Math.min;
import static java.lang.Math.max;

class DashedLineRenderer implements ILineRenderer<ILineRendererContext> {

  private final int offset;
    private final int length;

    DashedLineRenderer(int offset, int length) {
        this.length = length;
        this.offset = offset;
    }

    @Override
    public void draw(ILineRendererContext renderCtx) {

        renderCtx.getGraphics().setColor(renderCtx.getColor());

        int x0 = renderCtx.getBegin().getX();
        int y0 = renderCtx.getBegin().getY();
        int x1 = renderCtx.getEnd().getX();
        int y1 = renderCtx.getEnd().getY();
        if (x0 == x1) {
            drawVertical(renderCtx.getGraphics(), x0, y0, y1);
        } else {
            drawHorizontal(renderCtx.getGraphics(), y0, x0, x1);
        }
    }

    private void drawHorizontal(Graphics2D graphics, int y, int x0, int x1) {
        int start = min(x0, x1);
        int end = max(x0, x1);
        for (int i = start; i < end; i += length + offset) {
            int n = min(i + length, end);
            graphics.drawLine(i, y, n, y);
        }
    }

    private void drawVertical(Graphics2D graphics, int x, int y0, int y1) {
        int start = min(y0, y1);
        int end = max(y0, y1);
        for (int i = start; i < end; i += length + offset) {
            int n = min(i + length, end);
            graphics.drawLine(x, i, x, n);
        }
    }

}
