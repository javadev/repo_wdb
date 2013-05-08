package org.wdbuilder.view;

import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;

interface ILineEndRendererContext extends ILineRendererContext {

	Point getBaseLocation();

	LinkSocket.Direction getDirection();

}
