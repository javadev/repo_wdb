package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import static org.wdbuilder.input.InputParameter.DiagramKey;
import static org.wdbuilder.input.InputParameter.Name;
import static org.wdbuilder.input.InputParameter.Background;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/edit-diagram-save")
public class EditDiagramSaveServlet extends DiagramServlet {

	@Override
	protected void do4Frame(ServletInput input) throws Exception {
		serviceFacade.getDiagramService().update(DiagramKey.getString(input),
				Name.getString(input), Background.getString(input));
		printCanvasFrame(input);
	}
}
