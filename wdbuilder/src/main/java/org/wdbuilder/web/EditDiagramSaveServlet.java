package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/edit-diagram-save")
public class EditDiagramSaveServlet extends DiagramServlet {

	@Override
	protected void do4Frame(ServletInput input) throws Exception {
		serviceFacade.getDiagramService().update(
				BlockParameter.DiagramKey.getString(input),
				BlockParameter.Name.getString(input),
				BlockParameter.Background.getString(input));
		printCanvasFrame(input);
	}
}
