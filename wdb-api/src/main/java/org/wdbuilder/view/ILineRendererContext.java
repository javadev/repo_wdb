package org.wdbuilder.view;

import org.wdbuilder.domain.helper.Point;

public interface ILineRendererContext extends ILineRendererContextBase {
    Point getBegin();

    Point getEnd();
}
