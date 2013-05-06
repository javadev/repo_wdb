package org.wdbuilder.web;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBException;

import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.web.base.CanvasFrameWriter;
import org.wdbuilder.web.base.FrameServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/diagram")
public class DiagramServlet extends FrameServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4Frame(ServletInput input) throws Exception {
		printCanvasFrame(input, BlockParameter.BlockKey.getString(input));
	}

	protected void printCanvasFrame(ServletInput input, String selectedBlockKey)
			throws JAXBException, IOException {
		input.getState().setSelectedBlockKey(selectedBlockKey);
		new CanvasFrameWriter(diagramHelper,
				serviceFacade.getBlockPluginRepository())
				.printCanvasFrame(input);
	}
}
