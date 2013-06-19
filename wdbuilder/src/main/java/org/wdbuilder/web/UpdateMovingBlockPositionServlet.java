package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import static org.wdbuilder.input.InputParameter.DiagramKey;
import static org.wdbuilder.input.InputParameter.BlockKey;
import static org.wdbuilder.input.InputParameter.X;
import static org.wdbuilder.input.InputParameter.Y;
import org.wdbuilder.web.base.EmptyOutputServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/update-moving-block-position")
public class UpdateMovingBlockPositionServlet extends EmptyOutputServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		serviceFacade
				.getDiagramService()
				.getBlockService(DiagramKey.getString(input))
				.setPosition(BlockKey.getString(input), X.getInt(input),
						Y.getInt(input));
	}
}
