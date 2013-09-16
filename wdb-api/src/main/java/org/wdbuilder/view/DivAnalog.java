package org.wdbuilder.view;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Graphics2D;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.helper.Point;

public class DivAnalog {

    private final boolean horizontal;
    private final Point[] line = new Point[3];
    private final Link link;

    public static void render(Graphics2D gr, Link link, Block beginBlock,
            Block endBlock) {       
        final DivAnalog[] divs = getDivPair(link, beginBlock, endBlock);
        for (final DivAnalog div : divs) {
            div.render(gr);
        }
    }
    
    public static Point[] getLine(Link link, Block beginBlock,
            Block endBlock) {
        final DivAnalog[] divs = getDivPair(link, beginBlock, endBlock);
                
        Point[] result = new Point[6];
        int i = 0;
        for (DivAnalog div : divs) {
            for (Point p : div.line) {
                result[i++] = p;
            }
        }
        return result;
    }
    
    private static DivAnalog[] getDivPair(Link link, Block beginBlock,
            Block endBlock) {
        DivAnalog[] result = new DivAnalog[2];
        
        Point begin = link.getSockets().get(0).getOffset(beginBlock);
        Point end = link.getSockets().get(1).getOffset(endBlock);

        final boolean hintHorizontal = link.getSockets().get(0).isHorizontal();

        result[0] = new DivAnalog(link, begin, hintHorizontal);
        result[1] = new DivAnalog(link, end, !hintHorizontal);
        if (result[0].intersectsWith(result[1])) {
            result[0] = new DivAnalog(link, begin, !hintHorizontal);
            result[1] = new DivAnalog(link, end, hintHorizontal);
        }
        
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
