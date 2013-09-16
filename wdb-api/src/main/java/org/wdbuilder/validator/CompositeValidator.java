package org.wdbuilder.validator;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Entity;

public abstract class CompositeValidator<T extends Entity> implements
        IValidator<T> {
    private final IValidator<T> baseValidator;

    protected abstract Iterable<IValidator<T>> getNestedValidators();

    public CompositeValidator(IValidator<T> baseValidator) {
        this.baseValidator = baseValidator;
    }

    @Override
    public void validate(Diagram diagram, T entity) {
        if (null != baseValidator) {
            baseValidator.validate(diagram, entity);
        }
        for (final IValidator<T> validator : getNestedValidators()) {
            validator.validate(diagram, entity);
        }
    }

}
