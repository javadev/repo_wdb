package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.utility.DiagramHelper;
import org.wdbuilder.web.base.CanvasFrameWriter;
import org.wdbuilder.web.base.DiagramServiceServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/create-diagram-save")
public class CreateDiagramSaveServlet extends DiagramServiceServlet {
	private static final long serialVersionUID = 1L;

	private DiagramHelper diagramHelper = null;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		final String key = service.persistDiagram(
				BlockParameter.Name.getString(input),
				BlockParameter.Width.getInt(input),
				BlockParameter.Height.getInt(input),
				BlockParameter.Background.getString(input));
		this.diagramHelper = createDiagramHelper(key);
		// Skip block selection:
		input.getState().setSelectedBlockKey(null);
		new CanvasFrameWriter(diagramHelper, pluginFacadeRepository)
				.printCanvasFrame(input);
	}

	@Override
	protected String getContentType() {
		return CONTENT_TYPE_XML;
	}
}
