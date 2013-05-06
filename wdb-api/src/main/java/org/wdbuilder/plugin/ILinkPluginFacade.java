package org.wdbuilder.plugin;

import org.wdbuilder.domain.Link;

public interface ILinkPluginFacade extends IPluginFacade<Link> {
	public IRenderer<Link,ILinkRenderContext> getRenderer();
}
