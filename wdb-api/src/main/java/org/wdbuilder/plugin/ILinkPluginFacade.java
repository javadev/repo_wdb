package org.wdbuilder.plugin;

import org.wdbuilder.domain.Link;

public interface ILinkPluginFacade extends IEntityPluginFacade<Link> {
	public IRenderer<Link,ILinkRenderContext> getRenderer();
}
