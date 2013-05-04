package org.wdbuilder.utility;

import org.wdbuilder.plugin.IPluginFacade;

public interface IPluginFacadeRepository {
  
	public Iterable<IPluginFacade> getBlockPlugins();

	public IPluginFacade getFacade(Class<?> klass);
	
	public Iterable<Class<?>> getBlockClasses();
}
