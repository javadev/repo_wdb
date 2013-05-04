package org.wdbuilder.validator;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;

public interface IBlockValidator {
  void validate(Diagram diagram, Block entity)
			throws IllegalArgumentException;
}
