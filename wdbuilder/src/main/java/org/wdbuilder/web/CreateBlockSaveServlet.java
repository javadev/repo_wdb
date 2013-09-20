package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Block;
import org.wdbuilder.input.InputParameter;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/create-block-save")
public class CreateBlockSaveServlet extends DiagramServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void do4Frame(ServletInput input) throws Exception {
        final String diagramKey = InputParameter.DiagramKey.getString(input);

        final String blockClassStr = InputParameter.BlockClass.getString(input);
        final Class<?> blockClass = Class.forName(blockClassStr);

        final IBlockPluginFacade pluginFacade = serviceFacade
                .getBlockPluginRepository().getFacade(blockClass);
        final Block block = pluginFacade.create(input);
        serviceFacade.getDiagramService().getBlockService(diagramKey)
                .persist(block);
        printCanvasFrame(input);
    }
}
