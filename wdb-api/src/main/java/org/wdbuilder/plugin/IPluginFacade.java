package org.wdbuilder.plugin;

public interface IPluginFacade<T,S> {	
	public IRenderer<T,S> getRenderer();
}
