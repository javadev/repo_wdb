package org.wdbuilder.view;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.wdbuilder.domain.ISimpleBackgroundProvider;


public class SimpleBackgroundRenderer {
	private final ISimpleBackgroundProvider source;
	
	public SimpleBackgroundRenderer( ISimpleBackgroundProvider source ) {
		this.source = source;
	}
	
	public void render( Graphics2D gr, Rectangle rect ) {
		gr.setColor( source.getPrimaryBackgroundColor() );
		gr.fillRect(rect.x, rect.y, rect.width, rect.height);
	}
	
}
