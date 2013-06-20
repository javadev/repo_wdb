package org.wdbuilder.web;

import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import static org.wdbuilder.input.InputParameter.BlockKey;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.web.base.DiagramHelperFormServlet;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/edit-block")
public class EditBlockServlet extends DiagramHelperFormServlet {

	@Override
	protected void do4DiagramHelperForm(ServletInput input) throws Exception {
		final PrintWriter writer = input.getResponse().getWriter();

		// Get existing block data:
		final Block block = diagramHelper.getDiagram().getBlock(
				BlockKey.getString(input));

		final HtmlWriter htmlWriter = new HtmlWriter(writer);

		IBlockPluginFacade pluginFacade = serviceFacade
				.getBlockPluginRepository().getFacade(block.getClass());
		if (null == pluginFacade) {
			return;
		}
		final UIExistingEntityFormFactory<Block> formFactory = pluginFacade
				.getEditFormFactory(diagramHelper.getDiagram().getKey(), block);

		String submitFunctionCall = formFactory.getSubmitCall();

		String closeHandler = "hideProperties()";

		final TwoColumnForm form = formFactory.getForm().addFooter(
				submitFunctionCall, closeHandler);

		htmlWriter.write(form);

	}

}
