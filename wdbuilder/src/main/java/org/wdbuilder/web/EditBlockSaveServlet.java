package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Block;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/edit-block-save")
public class EditBlockSaveServlet extends DiagramServlet {

	@Override
	protected void do4Frame(ServletInput input) throws Exception {

		final String diagramKey = BlockParameter.DiagramKey.getString(input);
		final String blockKey = BlockParameter.BlockKey.getString(input);
		final Block persistedBlock = diagramHelper.findBlockByKey(blockKey);
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
