package org.wdbuilder.web;

import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Link;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import static org.wdbuilder.input.InputParameter.LinkKey;
import static org.wdbuilder.input.InputParameter.DiagramKey;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.plugin.ILinkPluginFacade;
import org.wdbuilder.web.base.DiagramHelperFormServlet;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/edit-link")
public class EditLinkServlet extends DiagramHelperFormServlet {

	@Override
	protected void do4DiagramHelperForm(ServletInput input) throws Exception {
		final PrintWriter writer = input.getResponse().getWriter();
		final String diagramKey = DiagramKey.getString(input);

		// Get existing block data:
		final Link link = getDiagram(input).getLink(LinkKey.getString(input));

		final HtmlWriter htmlWriter = new HtmlWriter(writer);

		ILinkPluginFacade pluginFacade = serviceFacade
				.getLinkPluginRepository().getFacade(link.getClass());
		if (null == pluginFacade) {
			return;
		}
		final UIExistingEntityFormFactory<Link> formFactory = pluginFacade
				.getEditFormFactory(diagramKey, link);

		String submitFunctionCall = formFactory.getSubmitCall();

		String closeHandler = "loadCanvas('" + diagramKey + "')";

		final TwoColumnForm form = formFactory.getForm().addFooter(
				submitFunctionCall, closeHandler);

		htmlWriter.write(form);
	}

}
