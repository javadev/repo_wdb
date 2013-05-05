package org.wdbuilder.validator;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Entity;

public interface IValidator<T extends Entity> {
  void validate(Diagram diagram, T entity)
			throws IllegalArgumentException;
}
