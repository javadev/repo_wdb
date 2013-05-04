package org.wdbuilder.service.validator;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.validator.CompositeBlockValidator;
import org.wdbuilder.validator.IBlockValidator;

public class DiagramValidator extends CompositeBlockValidator {

  private static final int MIN_NAME_LENGTH = 3;
	private static final int MAX_NAME_LENGTH = 128;

	public static final Dimension MIN_SIZE = new Dimension(320, 240);
	private static final Dimension MAX_SIZE = new Dimension(1280, 1024);

	public DiagramValidator() {
		super(null);
	}

	@Override
	protected Iterable<IBlockValidator> getNestedValidators() {
		List<IBlockValidator> result = new ArrayList<IBlockValidator>(2);
		result.add(new IBlockValidator() {
			@Override
			public void validate(Diagram diagram, Block entity)
					throws IllegalArgumentException {
				if (null == diagram) {
					throw new IllegalArgumentException("Diagram can't be null");
				}
			}
		});

		result.add(new IBlockValidator() {
			@Override
			public void validate(Diagram diagram, Block entity)
					throws IllegalArgumentException {
				String name = diagram.getName();
				if (null == name) {
					throw new IllegalArgumentException(
							"Diagram's name can't be null");
				}
				int len = name.trim().length();
				if (MIN_NAME_LENGTH > len) {
					throw new IllegalArgumentException(
							"Diagram's name is too short");
				}
				if (MAX_NAME_LENGTH < len) {
					throw new IllegalArgumentException(
							"Diagrams's name is too long");
				}
			}
		});

		result.add(new IBlockValidator() {

			@Override
			public void validate(Diagram diagram, Block entity)
					throws IllegalArgumentException {
				int width = diagram.getSize().getWidth();

				if (MIN_SIZE.getWidth() > width) {
					throw new IllegalArgumentException(
							"Diagram's width is too small");
				}
				if (MAX_SIZE.getWidth() < width) {
					throw new IllegalArgumentException(
							"Diagram's width is too large");
				}
			}
		});
		
		result.add(new IBlockValidator() {

			@Override
			public void validate(Diagram diagram, Block entity)
					throws IllegalArgumentException {
				int height = diagram.getSize().getHeight();

				if (MIN_SIZE.getHeight() > height) {
					throw new IllegalArgumentException(
							"Diagram's height is too small");
				}
				if (MAX_SIZE.getHeight() < height) {
					throw new IllegalArgumentException(
							"Diagram's height is too large");
				}
			}
		});
		


		return result;
	}

}
