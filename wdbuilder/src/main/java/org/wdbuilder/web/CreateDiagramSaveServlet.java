package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.utility.DiagramHelper;
import org.wdbuilder.web.base.CanvasFrameWriter;
import org.wdbuilder.web.base.DiagramServiceServlet;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/create-diagram-save")
public class CreateDiagramSaveServlet extends DiagramServiceServlet {

	private DiagramHelper diagramHelper = null;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		final String key = serviceFacade.getDiagramService().persistDiagram(
				BlockParameter.Name.getString(input),
				BlockParameter.Width.getInt(input),
				BlockParameter.Height.getInt(input),
				BlockParameter.Background.getString(input));
		this.diagramHelper = createDiagramHelper(key);

		// Set the current diagram (TODO silly method):
		input.getState().setDiagram(
				serviceFacade.getDiagramService().getDiagram(key));

		new CanvasFrameWriter(diagramHelper,
				serviceFacade.getBlockPluginRepository())
				.printCanvasFrame(input);
	}

	@Override
	protected String getContentType() {
		return CONTENT_TYPE_XML;
	}
}
