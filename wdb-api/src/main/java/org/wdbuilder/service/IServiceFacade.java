package org.wdbuilder.service;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Link;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.plugin.ILinkPluginFacade;
import org.wdbuilder.plugin.ILinkRenderContext;
import org.wdbuilder.plugin.IRenderContext;

public interface IServiceFacade {

	DiagramService getDiagramService();

	IPluginFacadeRepository<Block, IBlockPluginFacade, IRenderContext> getBlockPluginRepository();

	IPluginFacadeRepository<Link, ILinkPluginFacade, ILinkRenderContext> getLinkPluginRepository();
}
