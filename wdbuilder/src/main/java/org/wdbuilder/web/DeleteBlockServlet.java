package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.web.base.EmptyOutputServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/delete-block")
public class DeleteBlockServlet extends EmptyOutputServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		serviceFacade.getDiagramService()
				.getBlockService(BlockParameter.DiagramKey.getString(input))
				.delete(BlockParameter.BlockKey.getString(input));
	}
}
