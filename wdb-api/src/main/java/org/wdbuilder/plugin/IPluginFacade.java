package org.wdbuilder.plugin;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.gui.UIExistingBlockFormFactory;
import org.wdbuilder.gui.UINewBlockFormFactory;
import org.wdbuilder.input.InputAdapter;
import org.wdbuilder.validator.IValidator;

public interface IPluginFacade {

	public Class<?> getBlockClass();

	public IRenderer getRenderer();

	public IUIActionClick getUIActionCreate(String diagramKey);

	public UINewBlockFormFactory getCreateBlockFormFactory(String diagramKey);

	public UIExistingBlockFormFactory getViewBlockFormFactory(
			String diagramKey, Block block);
	
	public UIExistingBlockFormFactory getEditBlockFormFactory(
			String diagramKey, Block block);
	

	public Block create(InputAdapter input);

	public IValidator getValidator();
}
