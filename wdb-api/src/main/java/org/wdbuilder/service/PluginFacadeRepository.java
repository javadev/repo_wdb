package org.wdbuilder.service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.wdbuilder.domain.Entity;
import org.wdbuilder.plugin.IPluginFacade;

public class PluginFacadeRepository<T extends Entity, U extends IPluginFacade<T, S>, S>
        implements IPluginFacadeRepository<T, U, S> {

    private final Map<Class<?>, U> plugins = new LinkedHashMap<Class<?>, U>(2);

    public PluginFacadeRepository(Collection<U> plugins) {
        for (U plugin : plugins) {
            this.plugins.put(plugin.getEntityClass(), plugin);
        }
    }

    @Override
    public Iterable<U> getPlugins() {
        return this.plugins.values();
    }

    @Override
    public U getFacade(Class<?> klass) {
        return this.plugins.get(klass);
    }

    @Override
    public Collection<Class<?>> getEntityClasses() {
        return this.plugins.keySet();
    }

}
