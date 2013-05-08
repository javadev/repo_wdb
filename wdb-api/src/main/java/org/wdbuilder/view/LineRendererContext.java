package org.wdbuilder.view;

import java.awt.Color;
import java.awt.Graphics2D;

public class LineRendererContext implements ILineRendererContext {
  
	private Graphics2D graphics;
	private Color color;

	@Override
	public Graphics2D getGraphics() {
		return graphics;
	}

	@Override
	public Color getColor() {
		return color;
	}

	public void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;
	}

	public void setColor(Color color) {
		this.color = color;
	}


}
