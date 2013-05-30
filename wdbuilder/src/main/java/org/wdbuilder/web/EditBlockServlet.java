package org.wdbuilder.web;

import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.jaxbhtml.element.Div;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.serialize.html.ConnectedLinkList;
import org.wdbuilder.web.base.DiagramHelperFormServlet;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/edit-block")
public class EditBlockServlet extends DiagramHelperFormServlet {

	@Override
	protected void do4DiagramHelperForm(ServletInput input) throws Exception {
		final PrintWriter writer = input.getResponse().getWriter();

		// Get existing block data:
		final Block block = diagramHelper
				.findBlockByKey(BlockParameter.BlockKey.getString(input));

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

		Div header = new Div();
		header.setText("Connected Links:");
		htmlWriter.write(header);

		htmlWriter.write(new ConnectedLinkList(diagramHelper.getDiagram(),
				block));
	}

}
