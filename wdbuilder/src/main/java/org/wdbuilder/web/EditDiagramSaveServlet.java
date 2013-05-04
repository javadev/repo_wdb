package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/edit-diagram-save")
public class EditDiagramSaveServlet extends DiagramServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4Frame(ServletInput input) throws Exception {
		service.updateDiagram(BlockParameter.DiagramKey.getString(input),
				BlockParameter.Name.getString(input),
				BlockParameter.Background.getString(input));

		printCanvasFrame(input, null);
	}
}
