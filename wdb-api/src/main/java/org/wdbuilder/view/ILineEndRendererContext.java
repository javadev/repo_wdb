package org.wdbuilder.view;

import java.awt.Color;
import java.awt.Graphics2D;

import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;

interface ILineEndRendererContext extends ILineRenderContext {

	Point getBaseLocation();

	LinkSocket.Direction getDirection();

}
