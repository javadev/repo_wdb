package org.wdbuilder.gui;

import org.wdbuilder.domain.Entity;

public abstract class UIExistingEntityFormFactory<T extends Entity> extends
        UIEntityFormFactory {

    protected final T entity;

    public UIExistingEntityFormFactory(String diagramKey, T entity) {
        super(diagramKey);
        this.entity = entity;
    }
}
