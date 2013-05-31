package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.DiagramBackground;
import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.web.base.DiagramHelperFormServlet;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/edit-diagram")
public class EditDiagramServlet extends DiagramHelperFormServlet {

	@Override
	protected void do4DiagramHelperForm(ServletInput input) throws Exception {
		final Diagram diagram = diagramHelper.getDiagram();

		final HtmlWriter htmlWriter = new HtmlWriter(input.getResponse()
				.getWriter());

		String submitHandler = "submitEditCanvas()";
		String closeHandler = "closeDialog('properties')";

		final PredefinedSelect<DiagramBackground> selectField = new PredefinedSelect<DiagramBackground>(
				DiagramBackground.values(), diagram.getBackground());

		final TwoColumnForm form = new TwoColumnForm("edit-diagram-save", "Edit Diagram")
				.addReadOnlyField(BlockParameter.DiagramKey, diagram.getKey())
				.addTextField(BlockParameter.Name, diagram.getName())
				.addSelectField(BlockParameter.Background, selectField)
				.addFooter(submitHandler, closeHandler);
		htmlWriter.write(form);
	}

}
