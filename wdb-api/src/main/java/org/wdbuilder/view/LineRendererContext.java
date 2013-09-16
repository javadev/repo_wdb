package org.wdbuilder.view;

import org.wdbuilder.domain.helper.Point;

class LineRendererContext extends LineRendererContextBase implements
    ILineRendererContext {

    private Point begin;
    private Point end;

    @Override
    public Point getBegin() {
        return begin;
    }

    @Override
    public Point getEnd() {
        return end;
    }

    void setBegin(Point begin) {
        this.begin = begin;
    }

    void setEnd(Point end) {
        this.end = end;
    }

}
