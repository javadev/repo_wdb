package org.wdbuilder.service.validator;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.validator.CompositeValidator;
import org.wdbuilder.validator.IValidator;

public class DiagramValidator extends CompositeValidator<Diagram> {

  private static final int MIN_NAME_LENGTH = 3;
	private static final int MAX_NAME_LENGTH = 128;
	public static final Dimension MIN_SIZE = new Dimension(320, 240);
	private static final Dimension MAX_SIZE = new Dimension(1280, 1024);

	private static final int MARGIN = 10;

	private final Diagram diagram;

	public DiagramValidator(Diagram diagram) {
		super(null);
		this.diagram = diagram;
	}

	@Override
	protected Iterable<IValidator<Diagram>> getNestedValidators() {
		List<IValidator<Diagram>> result = new ArrayList<IValidator<Diagram>>(2);
		result.add(new IValidator<Diagram>() {
			@Override
			public void validate(Diagram diagram, Diagram entity) // TODO ????
					throws IllegalArgumentException {
				if (null == diagram) {
					throw new IllegalArgumentException("Diagram can't be null");
				}
			}
		});

		result.add(new IValidator<Diagram>() {
			@Override
			public void validate(Diagram diagram, Diagram entity)
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

		result.add(new IValidator<Diagram>() {

			@Override
			public void validate(Diagram diagram, Diagram entity)
					throws IllegalArgumentException {
				int width = diagram.getSize().getWidth();

				if ( getMinWidth() > width) {
					throw new IllegalArgumentException(
							"Diagram's width is too small");
				}
				if (MAX_SIZE.getWidth() < width) {
					throw new IllegalArgumentException(
							"Diagram's width is too large");
				}
			}
		});

		result.add(new IValidator<Diagram>() {

			@Override
			public void validate(Diagram diagram, Diagram entity)
					throws IllegalArgumentException {
				int height = diagram.getSize().getHeight();

				if ( getMinHeight() > height) {
					throw new IllegalArgumentException(
							"Diagram's height is too small");
				}
				if ( MAX_SIZE.getHeight() < height) {
					throw new IllegalArgumentException(
							"Diagram's height is too large");
				}
			}
		});

		return result;
	}

	public int getMinHeight() {
		int result = MIN_SIZE.getHeight();
		for (Block block : diagram.getBlocks()) {
			int y = 2 * MARGIN + block.getLocation().getY()
					+ block.getSize().getHeight() / 2;
			if( y>result ) {
				result = y;
			}
		}

		return result;
	}
	
	public int getMinWidth() {
		int result = MIN_SIZE.getWidth();
		for (Block block : diagram.getBlocks()) {
			int x = 2 * MARGIN + block.getLocation().getX()
					+ block.getSize().getWidth() / 2;
			if( x>result ) {
				result = x;
			}
		}

		return result;
	}
	

}
