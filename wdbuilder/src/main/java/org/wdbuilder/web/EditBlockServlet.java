package org.wdbuilder.web;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.jaxbhtml.element.Div;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.serialize.html.ConnectedLinkList;
import org.wdbuilder.serialize.html.SectionHeader;
import org.wdbuilder.web.base.DiagramHelperFormServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/edit-block")
public class EditBlockServlet extends DiagramHelperFormServlet {
	private static final long serialVersionUID = 1L;

	private static final Map<Boolean, String> LINK_TITLES = new LinkedHashMap<Boolean, String>(
			2);

	static {
		LINK_TITLES.put(true, "Link begins (to delete):");
		LINK_TITLES.put(false, "Link ends (to delete):");
	}

	@Override
	protected void do4DiagramHelperForm(ServletInput input) throws Exception {
		final PrintWriter writer = input.getResponse().getWriter();
		final String diagramId = "'" + diagramHelper.getDiagram().getKey()
				+ "'";

		// Get existing block data:
		final Block block = diagramHelper
				.findBlockByKey(BlockParameter.BlockKey.getString(input));

		final HtmlWriter htmlWriter = new HtmlWriter(writer);

		IBlockPluginFacade pluginFacade = pluginFacadeRepository.getFacade(block
				.getClass());
		if (null == pluginFacade) {
			return;
		}
		final UIExistingEntityFormFactory<Block> formFactory = pluginFacade
				.getEditBlockFormFactory(diagramHelper.getDiagram().getKey(),
						block);

		String submitFunctionCall = formFactory.getSubmitCall();

		String closeHandler = "loadCanvas(" + diagramId + ",'" + block.getKey()
				+ "')";

		htmlWriter.write(new SectionHeader(formFactory.getTitle()));

		final TwoColumnForm form = formFactory.getForm().addFooter(
				submitFunctionCall, closeHandler);

		htmlWriter.write(form);
		
		Div header = new Div();
		header.setText("Connected Links:");
		htmlWriter.write(header);
		
		htmlWriter.write(new ConnectedLinkList(diagramHelper.getDiagram(), block));
	}

}
