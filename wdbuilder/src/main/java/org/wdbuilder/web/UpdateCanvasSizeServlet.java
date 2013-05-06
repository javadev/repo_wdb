package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.web.base.EmptyOutputServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/update-canvas-size")
public class UpdateCanvasSizeServlet extends EmptyOutputServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		serviceFacade.getDiagramService().updateDiagramSize(
				BlockParameter.DiagramKey.getString(input),
				BlockParameter.Width.getInt(input),
				BlockParameter.Height.getInt(input));
	}
}
