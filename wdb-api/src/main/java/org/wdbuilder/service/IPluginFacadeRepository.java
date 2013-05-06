package org.wdbuilder.service;

import java.util.Collection;

import org.wdbuilder.domain.Entity;
import org.wdbuilder.plugin.IPluginFacade;

public interface IPluginFacadeRepository<T extends Entity, S extends IPluginFacade<T>> {

	public Iterable<S> getPlugins();

	public S getFacade(Class<?> klass);

	public Collection<Class<?>> getEntityClasses();
}
