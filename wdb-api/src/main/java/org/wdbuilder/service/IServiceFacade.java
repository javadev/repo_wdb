package org.wdbuilder.service;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Link;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.plugin.ILinkPluginFacade;

public interface IServiceFacade {

	DiagramService getDiagramService();

	IPluginFacadeRepository<Block, IBlockPluginFacade> getBlockPluginRepository();

	IPluginFacadeRepository<Link, ILinkPluginFacade> getLinkPluginRepository();
}
