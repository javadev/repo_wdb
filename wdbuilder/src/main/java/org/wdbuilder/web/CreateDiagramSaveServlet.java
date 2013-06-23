package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.input.InputParameter;
import org.wdbuilder.serialize.html.DiagramImage;
import org.wdbuilder.web.base.DiagramServiceServlet;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/create-diagram-save")
public class CreateDiagramSaveServlet extends DiagramServiceServlet {

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		final String key = serviceFacade.getDiagramService().persist(
				InputParameter.Name.getString(input),
				InputParameter.Background.getString(input));

		Diagram diagram = getDiagram(key);

		// Set the current diagram (TODO silly method):
		input.getState().setDiagram(diagram);

		new DiagramImage(diagram, serviceFacade.getBlockPluginRepository())
				.printCanvasFrame(input);
	}

	@Override
	protected String getContentType() {
		return CONTENT_TYPE_XML;
	}
}
