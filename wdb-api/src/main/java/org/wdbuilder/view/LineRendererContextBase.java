package org.wdbuilder.view;

import java.awt.Color;
import java.awt.Graphics2D;

public class LineRendererContextBase implements ILineRendererContext {
	
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

	void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;
	}

	void setColor(Color color) {
		this.color = color;
	}


}
