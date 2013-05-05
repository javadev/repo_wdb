package org.wdbuilder.plugin;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.gui.UINewBlockFormFactory;
import org.wdbuilder.input.InputAdapter;

public interface IBlockPluginFacade extends IPluginFacade<Block> {

	public IRenderer getRenderer();

	public IUIActionClick getUIActionCreate(String diagramKey);

	public UINewBlockFormFactory getCreateFormFactory(String diagramKey);

	public UIExistingEntityFormFactory<Block> getViewFormFactory(
			String diagramKey, Block block);

	public Block create(InputAdapter input);
}
