package org.wdbuilder.plugin;

public interface IPluginFacade<T, S> {  
    IRenderer<T, S> getRenderer();
    Class<?> getEntityClass();
}
