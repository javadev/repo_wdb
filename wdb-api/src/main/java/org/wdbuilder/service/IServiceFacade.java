package org.wdbuilder.service;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Link;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.plugin.ILinkPluginFacade;

// TODO "the root object" for diagram service and repository of plugins (2013/05/06)
public interface IServiceFacade {

	DiagramService getDiagramService();

	IPluginFacadeRepository<Block, IBlockPluginFacade> getBlockPluginRepository();

	IPluginFacadeRepository<Link, ILinkPluginFacade> getLinkPluginRepository();
}
