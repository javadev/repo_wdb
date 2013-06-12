package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.utility.DiagramHelper;
import org.wdbuilder.web.base.CanvasFrameWriter;
import org.wdbuilder.web.base.DiagramServiceServlet;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/import-diagram-save")
public class ImportDiagramSaveServlet extends DiagramServiceServlet {

  private DiagramHelper diagramHelper = null;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		
		// TODO: extract the content from file and unmarshall it from ZIP/XML:

		/*
		new CanvasFrameWriter(diagramHelper,
				serviceFacade.getBlockPluginRepository())
				.printCanvasFrame(input);
		*/
	}

	@Override
	protected String getContentType() {
		return CONTENT_TYPE_XML;
	}
}
