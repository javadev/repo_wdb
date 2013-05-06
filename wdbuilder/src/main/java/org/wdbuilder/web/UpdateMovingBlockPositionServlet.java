package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.web.base.EmptyOutputServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/update-moving-block-position")
public class UpdateMovingBlockPositionServlet extends EmptyOutputServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		serviceFacade.getDiagramService().updateBlockPosition(
				BlockParameter.DiagramKey.getString(input),
				BlockParameter.BlockKey.getString(input),
				BlockParameter.X.getInt(input), BlockParameter.Y.getInt(input));
	}
}
