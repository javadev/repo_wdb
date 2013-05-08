package org.wdbuilder.view;

import java.awt.Color;
import java.awt.Graphics2D;

import org.wdbuilder.domain.helper.Point;

public interface ILineRendererContext {
  
	Graphics2D getGraphics();

	Color getColor();

	Point getBegin();

	Point getEnd();
}
