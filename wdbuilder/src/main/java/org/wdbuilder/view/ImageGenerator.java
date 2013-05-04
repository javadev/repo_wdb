package org.wdbuilder.view;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.web.ApplicationState;

import static org.wdbuilder.web.ImageServlet.IMAGE_FORMAT;

public abstract class ImageGenerator {

	protected final ApplicationState appState;

	protected abstract BufferedImage generateImage(String blockKey,
			Diagram diagram);

	public ImageGenerator(ApplicationState appState) {
		this.appState = appState;
	}

	public byte[] render(String blockKey) throws IOException {

		final Diagram diagram = appState.getDiagram();

		final BufferedImage image = generateImage(blockKey, diagram);

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
				8192);
		ImageIO.write(image, IMAGE_FORMAT, outputStream);
		outputStream.close();

		final byte[] result = outputStream.toByteArray();
		return result;
	}

}
