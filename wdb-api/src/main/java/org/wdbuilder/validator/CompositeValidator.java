package org.wdbuilder.validator;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;

public abstract class CompositeValidator implements
		IValidator {
	private final IValidator baseValidator;

	protected abstract Iterable<IValidator> getNestedValidators();

	public CompositeValidator(IValidator baseValidator) {
		this.baseValidator = baseValidator;
	}
	
	@Override
	public void validate( Diagram diagram, Block entity ) {
		if( null!=baseValidator ) {
			baseValidator.validate(diagram, entity);
		}
		for( final IValidator validator : getNestedValidators()) {
			validator.validate(diagram, entity);
		}
	}

}
