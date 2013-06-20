package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Block;
import static org.wdbuilder.input.InputParameter.DiagramKey;
import static org.wdbuilder.input.InputParameter.BlockKey;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/edit-block-save")
public class EditBlockSaveServlet extends DiagramServlet {

	@Override
	protected void do4Frame(ServletInput input) throws Exception {

		final String diagramKey = DiagramKey.getString(input);
		final String blockKey = BlockKey.getString(input);
		final Block persistedBlock = diagramHelper.getDiagram().getBlock(blockKey);
		if (null == persistedBlock) {
			return;
		}

		IBlockPluginFacade pluginFacade = serviceFacade
				.getBlockPluginRepository()
				.getFacade(persistedBlock.getClass());
		Block block = pluginFacade.create(input);

		serviceFacade.getDiagramService().getBlockService(diagramKey)
				.update(blockKey, block);
		printCanvasFrame(input);

	}

}
