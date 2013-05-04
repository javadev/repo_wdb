package org.wdbuilder.utility;

import org.wdbuilder.plugin.IBlockPluginFacade;

public interface IPluginFacadeRepository {
  
	public Iterable<IBlockPluginFacade> getBlockPlugins();

	public IBlockPluginFacade getFacade(Class<?> klass);
	
	public Iterable<Class<?>> getBlockClasses();
}
