package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Block;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.plugin.IPluginFacade;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/create-block-save")
public class CreateBlockSaveServlet extends DiagramServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4Frame(ServletInput input) throws Exception {
		final String diagramKey = BlockParameter.DiagramKey.getString(input);

		final String blockClassStr = BlockParameter.BlockClass.getString(input);
		final Class<?> blockClass = Class.forName(blockClassStr);

		final IPluginFacade pluginFacade = pluginFacadeRepository
				.getFacade(blockClass);
		final Block block = pluginFacade.create(input);
		final String blockKey = service.persistBlock(diagramKey, block);

		printCanvasFrame(input, blockKey);
	}
}
