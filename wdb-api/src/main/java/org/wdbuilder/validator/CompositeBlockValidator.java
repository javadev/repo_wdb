package org.wdbuilder.validator;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;

public abstract class CompositeBlockValidator implements
		IBlockValidator {
	private final IBlockValidator baseValidator;

	protected abstract Iterable<IBlockValidator> getNestedValidators();

	public CompositeBlockValidator(IBlockValidator baseValidator) {
		this.baseValidator = baseValidator;
	}
	
	@Override
	public void validate( Diagram diagram, Block entity ) {
		if( null!=baseValidator ) {
			baseValidator.validate(diagram, entity);
		}
		for( final IBlockValidator validator : getNestedValidators()) {
			validator.validate(diagram, entity);
		}
	}

}
