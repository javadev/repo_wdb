package org.wdbuilder.web;

import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.DiagramBackground;
import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.service.validator.DiagramValidator;
import org.wdbuilder.web.base.DiagramServiceServlet;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/create-diagram")
public class CreateDiagramServlet extends DiagramServiceServlet {

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		final PrintWriter writer = input.getResponse().getWriter();

		final PredefinedSelect<DiagramBackground> selectField = new PredefinedSelect<DiagramBackground>(
				DiagramBackground.values(), DiagramBackground.White);

		final TwoColumnForm form = new TwoColumnForm("create-diagram-save", "New Diagram")
				.addTextField(BlockParameter.Name, "")
				.addTextField(BlockParameter.Width,
						String.valueOf(DiagramValidator.MIN_SIZE.getWidth()))
				.addTextField(BlockParameter.Height,
						String.valueOf(DiagramValidator.MIN_SIZE.getHeight()))
				.addSelectField(BlockParameter.Background, selectField)
				.addFooter("submitCreateCanvas()", "refreshDiagramList()");
		new HtmlWriter(writer).write(form);
	}

	@Override
	protected String getContentType() {
		return DiagramServlet.CONTENT_TYPE_XML;
	}
}
