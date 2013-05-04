package org.wdbuilder.plugin;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.gui.IUIFormFactory;
import org.wdbuilder.input.InputAdapter;
import org.wdbuilder.validator.IBlockValidator;

public interface IPluginFacade {
	
	public Class<?> getBlockClass();
	
	public IRenderer getRenderer();

	public IUIActionClick getUIActionCreate(String diagramKey);

	public IUIFormFactory getFormFactory();
	
	public Block create( InputAdapter input );
	
	public IBlockValidator getValidator();
}
