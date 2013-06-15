package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import static org.wdbuilder.input.InputParameter.DiagramKey;
import static org.wdbuilder.input.InputParameter.LinkKey;
import org.wdbuilder.web.base.EmptyOutputServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/delete-link")
public class DeleteLinkServlet extends EmptyOutputServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		serviceFacade.getDiagramService()
				.getLinkService(DiagramKey.getString(input))
				.delete(LinkKey.getString(input));
	}
}
