package org.wdbuilder.service;

import java.util.Collection;

import org.wdbuilder.domain.Entity;
import org.wdbuilder.plugin.IPluginFacade;

public interface IPluginFacadeRepository<T extends Entity, U extends IPluginFacade<T, S>, S> {

    Iterable<U> getPlugins();

    U getFacade(Class<?> klass);

    Collection<Class<?>> getEntityClasses();
}
