package org.wdbuilder.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

// TODO: create package util and move this file there (2013/05/03)
public abstract class ImageUtility {

  public static Graphics2D getGraphics(BufferedImage image) {
		final Graphics2D result = image.createGraphics();
		result.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		result.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		result.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		return result;
	}

	public static ImageObserver getImageObserver() {
		return new ImageObserver() {
			@Override
			public boolean imageUpdate(Image img, int infoflags, int x, int y,
					int width, int height) {
				return true;
			}
		};
	}
}
