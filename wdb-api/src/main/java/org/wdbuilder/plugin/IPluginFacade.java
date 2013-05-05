package org.wdbuilder.plugin;

import org.wdbuilder.domain.Entity;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.validator.IValidator;

public interface IPluginFacade<T extends Entity> {

	public Class<?> getEntityClass();

	public IValidator getValidator();

	public UIExistingEntityFormFactory<T> getEditFormFactory(
			String diagramKey, T entity);

}
