package org.wdbuilder.service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.utility.IPluginFacadeRepository;

public class StaticPluginFacadeRepository implements IPluginFacadeRepository {

	// TODO: dull method (2013/05/01)
	private static IPluginFacadeRepository instance = null;

	public static IPluginFacadeRepository getInstance(
			Collection<IBlockPluginFacade> blockPlugins) {
		if (null == instance) {
			instance = new StaticPluginFacadeRepository(blockPlugins);
		}
		return instance;
	}

	private final Map<Class<?>, IBlockPluginFacade> blockPlugins = new LinkedHashMap<Class<?>, IBlockPluginFacade>(
			2);

	private StaticPluginFacadeRepository(Collection<IBlockPluginFacade> blockPlugins) {
		for (IBlockPluginFacade blockPlugin : blockPlugins) {
			this.blockPlugins.put(blockPlugin.getEntityClass(), blockPlugin);
		}
	}

	@Override
	public Iterable<IBlockPluginFacade> getBlockPlugins() {
		return this.blockPlugins.values();
	}

	@Override
	public IBlockPluginFacade getFacade(Class<?> klass) {
		return this.blockPlugins.get(klass);
	}

	@Override
	public Iterable<Class<?>> getBlockClasses() {
		return this.blockPlugins.keySet();
	}

}
