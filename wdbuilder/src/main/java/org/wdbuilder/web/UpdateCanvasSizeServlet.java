package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import static org.wdbuilder.input.InputParameter.DiagramKey;
import static org.wdbuilder.input.InputParameter.Width;
import static org.wdbuilder.input.InputParameter.Height;
import org.wdbuilder.web.base.EmptyOutputServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/update-canvas-size")
public class UpdateCanvasSizeServlet extends EmptyOutputServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		serviceFacade.getDiagramService().setSize(DiagramKey.getString(input),
				Width.getInt(input), Height.getInt(input));
	}
}
