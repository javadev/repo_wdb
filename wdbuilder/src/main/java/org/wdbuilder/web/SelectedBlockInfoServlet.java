package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.web.base.DiagramHelperFormServlet;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/selected-block-info")
public class SelectedBlockInfoServlet extends DiagramHelperFormServlet {

	@Override
	protected void do4DiagramHelperForm(ServletInput input) throws Exception {
		final Block block = diagramHelper
				.findBlockByKey(BlockParameter.BlockKey.getString(input));
		if (null == block) {
			return;
		}
		final HtmlWriter writer = new HtmlWriter(input.getResponse()
				.getWriter());

		IBlockPluginFacade pluginFacade = serviceFacade.getBlockPluginRepository().getFacade(block
				.getClass());
		if (null == pluginFacade) {
			return;
		}
		final UIExistingEntityFormFactory<Block> formFactory = pluginFacade
				.getViewFormFactory(
						BlockParameter.DiagramKey.getString(input), block);
		TwoColumnForm form = formFactory.getForm();
		String openDialogMethodJS = "openEditBlockDialog";

		String blockKey = "'" + block.getKey() + "'";
		String diagramKey = "'" + diagramHelper.getDiagram().getKey() + "'";

		form.addLinks(new TwoColumnForm.LinkButton[] {
				new TwoColumnForm.LinkButton("Edit", openDialogMethodJS + "("
						+ diagramKey + "," + blockKey + ")"),
				new TwoColumnForm.LinkButton("Delete", "deleteBlock("
						+ diagramKey + "," + blockKey + ")") });
		writer.write(form);
	}
}
