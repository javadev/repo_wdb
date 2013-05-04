package org.wdbuilder.service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.wdbuilder.plugin.IPluginFacade;
import org.wdbuilder.utility.IPluginFacadeRepository;

public class StaticPluginFacadeRepository implements IPluginFacadeRepository {

	// TODO: dull method (2013/05/01)
	private static IPluginFacadeRepository instance = null;

	public static IPluginFacadeRepository getInstance(
			Collection<IPluginFacade> blockPlugins) {
		if (null == instance) {
			instance = new StaticPluginFacadeRepository(blockPlugins);
		}
		return instance;
	}

	private final Map<Class<?>, IPluginFacade> blockPlugins = new LinkedHashMap<Class<?>, IPluginFacade>(
			2);

	private StaticPluginFacadeRepository(Collection<IPluginFacade> blockPlugins) {
		for (IPluginFacade blockPlugin : blockPlugins) {
			this.blockPlugins.put(blockPlugin.getBlockClass(), blockPlugin);
		}
	}

	@Override
	public Iterable<IPluginFacade> getBlockPlugins() {
		return this.blockPlugins.values();
	}

	@Override
	public IPluginFacade getFacade(Class<?> klass) {
		return this.blockPlugins.get(klass);
	}

	@Override
	public Iterable<Class<?>> getBlockClasses() {
		return this.blockPlugins.keySet();
	}

}
