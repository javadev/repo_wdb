package org.wdbuilder.view;

import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;

public interface ILineEndRendererContext extends ILineRendererContextBase {

    Point getBaseLocation();

    LinkSocket.Direction getDirection();

}
