package org.wdbuilder.validator;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;

public interface IValidator {
  void validate(Diagram diagram, Block entity)
			throws IllegalArgumentException;
}
