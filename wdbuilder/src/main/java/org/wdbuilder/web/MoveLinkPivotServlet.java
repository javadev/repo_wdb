package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.web.base.EmptyOutputServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/move-link-pivot")
public class MoveLinkPivotServlet extends EmptyOutputServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {

		serviceFacade
				.getDiagramService()
				.getLinkService(BlockParameter.DiagramKey.getString(input))
				.setPivot(BlockParameter.LinkKey.getString(input),
						BlockParameter.X.getInt(input),
						BlockParameter.Y.getInt(input));
	}
}
