package org.wdbuilder.plugin;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.gui.UINewBlockFormFactory;

public interface IBlockPluginFacade extends IEntityPluginFacade<Block, IRenderContext> {

    IUIActionClick getUIActionCreate(String diagramKey);

    UINewBlockFormFactory getCreateFormFactory(String diagramKey);
}
