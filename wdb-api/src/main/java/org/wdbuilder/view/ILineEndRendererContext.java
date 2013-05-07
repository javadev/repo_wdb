package org.wdbuilder.view;

import java.awt.Color;
import java.awt.Graphics2D;

import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;

interface ILineEndRendererContext {

  public abstract Graphics2D getGraphics();

	Point getBaseLocation();

	Color getColor();

	LinkSocket.Direction getDirection();

}
