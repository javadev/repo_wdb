package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Block;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.plugin.IPluginFacade;
import org.wdbuilder.web.base.ServletInput;

// TODO: set "/edit-block-save" here

@WebServlet("/edit-block-save")
public class EditBlockSaveServlet extends DiagramServlet {
  private static final long serialVersionUID = 1L;

	@Override
	protected void do4Frame(ServletInput input) throws Exception {

		final String diagramKey = BlockParameter.DiagramKey.getString(input); 
		final String blockKey = BlockParameter.BlockKey.getString(input);
		final Block persistedBlock = diagramHelper.findBlockByKey(blockKey);
		if( null==persistedBlock ) {
			return;
		}
		
		IPluginFacade pluginFacade = pluginFacadeRepository
				.getFacade(persistedBlock.getClass());
		Block block = pluginFacade.create(input);
		
		service.updateBlock(diagramKey, blockKey, block);
		printCanvasFrame(input, blockKey);
		
	}

}
