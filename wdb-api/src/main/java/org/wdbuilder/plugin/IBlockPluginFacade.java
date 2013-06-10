package org.wdbuilder.plugin;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.gui.UINewBlockFormFactory;

public interface IBlockPluginFacade extends IEntityPluginFacade<Block, IRenderContext> {

	public IUIActionClick getUIActionCreate(String diagramKey);

	public UINewBlockFormFactory getCreateFormFactory(String diagramKey);
}
