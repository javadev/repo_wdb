package org.wdbuilder.plugin.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import org.wdbuilder.domain.SizedEntity;
import org.wdbuilder.domain.IGradientBackgroundProvider;
import org.wdbuilder.view.IBackgroundRenderer;

public class VerticalGradientBackgroundRenderer implements IBackgroundRenderer<IGradientBackgroundProvider> {

	@Override
	public void render(Graphics2D gr, SizedEntity entity, IGradientBackgroundProvider backgroundProvider) {
		Dimension size = entity.getSize().toAWT();

		final Color colorFrom = backgroundProvider.getPrimaryBackgroundColor();
		final Color colorTo = backgroundProvider.getSecondaryBackgroundColor();

		int redStart = colorFrom.getRed();
		int blueStart = colorFrom.getBlue();
		int greenStart = colorFrom.getGreen();

		int redEnd = colorTo.getRed();
		int blueEnd = colorTo.getBlue();
		int greenEnd = colorTo.getGreen();

		int h = size.height / 2;

		double redStep = ((double) (redEnd - redStart)) / h;
		double blueStep = ((double) (blueEnd - blueStart)) / h;
		double greenStep = ((double) (greenEnd - greenStart)) / h;

		for (int i = 0; i < size.height; i++) {
			int n = Math.abs(i - h);
			int red = redStart + (int) (n * redStep);
			int blue = blueStart + (int) (n * blueStep);
			int green = greenStart + (int) (n * greenStep);
			Color color = new Color(red, green, blue);
			gr.setColor(color);
			gr.drawLine(0, i, size.width, i);
		}

	}

}
