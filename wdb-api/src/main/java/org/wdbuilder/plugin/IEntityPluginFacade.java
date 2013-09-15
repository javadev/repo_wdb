package org.wdbuilder.plugin;

import org.wdbuilder.domain.Entity;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.InputAdapter;
import org.wdbuilder.validator.IValidator;

public interface IEntityPluginFacade<T extends Entity, S> extends
        IPluginFacade<T, S> {

    IValidator<T> getValidator();

    UIExistingEntityFormFactory<T> getEditFormFactory(String diagramKey,
            T entity);

    T create(InputAdapter input);

}
