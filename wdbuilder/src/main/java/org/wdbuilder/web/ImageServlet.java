package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.view.BlockImageGenerator;
import org.wdbuilder.view.DiagramImageGenerator;
import org.wdbuilder.view.ImageGenerator;
import org.wdbuilder.web.base.DiagramHelperServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/image")
public class ImageServlet extends DiagramHelperServlet {
	private static final long serialVersionUID = 1L;

	public static final String IMAGE_FORMAT = "png";

	@Override
	public void do4DiagramHelper(ServletInput input) throws Exception {
		final Diagram diagram = diagramHelper.getDiagram();

		ApplicationState appState = input.getState();
		appState.setDiagram(diagram);

		// Check for block id:
		final String blockKey = BlockParameter.BlockKey.getString(input);

		final boolean blockOnly = BlockParameter.BlockOnly.getBoolean(input);
		if (blockOnly) {
			appState.setSelectedBlockKey(blockKey);
		}

		byte[] imageData = getImageGenerator(blockOnly, appState).render(
				blockKey);
		input.getResponse().setContentLength(imageData.length);
		input.getResponse().getOutputStream().write(imageData);
	}

	@Override
	protected String getContentType() {
		return "image/" + IMAGE_FORMAT;
	}

	private ImageGenerator getImageGenerator(boolean blockOnly,
			ApplicationState appState) {
		if (blockOnly) {
			return new BlockImageGenerator(appState, pluginFacadeRepository);
		} else {
			return new DiagramImageGenerator(appState, pluginFacadeRepository);
		}
	}

}
