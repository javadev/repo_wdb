package org.wdbuilder.validator;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;

import static org.wdbuilder.service.DiagramService.LINE_OFFSET;

public class BlockValidator extends CompositeValidator<Block> {

    private final class NestedValidator5IValidator implements IValidator<Block> {
    @Override
    public void validate(Diagram diagram, Block entity)
            throws IllegalArgumentException {
        int bottomSide = entity.getLocation().getY()
                + entity.getSize().getHeight() / 2;
        if (diagram.getSize().getHeight() - LINE_OFFSET < bottomSide) {
            throw new IllegalArgumentException(
                    "Block height is too large for it's location");
        }
    }
  }

    private final class NestedValidator4IValidator implements IValidator<Block> {
    @Override
    public void validate(Diagram diagram, Block entity)
            throws IllegalArgumentException {
        int topSide = entity.getLocation().getY()
                - entity.getSize().getHeight() / 2;
        if (LINE_OFFSET > topSide) {
            throw new IllegalArgumentException(
                    "Block height is too large for it's location");
        }
    }
  }

    private final class NestedValidator3IValidator implements IValidator<Block> {
    @Override
    public void validate(Diagram diagram, Block entity)
            throws IllegalArgumentException {
        int rightSide = entity.getLocation().getX()
                + entity.getSize().getWidth() / 2;
        if (diagram.getSize().getWidth() - LINE_OFFSET < rightSide) {
            throw new IllegalArgumentException(
                    "Block width is too large for it's location");
        }
    }
  }

    private final class NestedValidator2IValidator implements IValidator<Block> {
    @Override
    public void validate(Diagram diagram, Block entity)
            throws IllegalArgumentException {
        int leftSide = entity.getLocation().getX()
                - entity.getSize().getWidth() / 2;
        if (LINE_OFFSET > leftSide) {
            throw new IllegalArgumentException(
                    "Block width is too large for it's location");
        }
    }
  }

    private final class NestedValidator1IValidator implements IValidator<Block> {
    @Override
    public void validate(Diagram diagram, Block entity)
            throws IllegalArgumentException {
        String name = entity.getName();
        if (null == name) {
            throw new IllegalArgumentException(
                    "Block's name can't be null");
        }
        int len = name.trim().length();
        if (MIN_NAME_LENGTH > len) {
            throw new IllegalArgumentException(
                    "Block's name is too short");
        }
        if (MAX_NAME_LENGTH < len) {
            throw new IllegalArgumentException(
                    "Block's name is too long");
        }
    }
  }

    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 64;

    public BlockValidator() {
        super(null);
    }

    @Override
    protected Iterable<IValidator<Block>> getNestedValidators() {
        List<IValidator<Block>> result = new ArrayList<IValidator<Block>>(2);
        result.add(new IValidator<Block>() {
            @Override
            public void validate(Diagram diagram, Block entity)
                    throws IllegalArgumentException {
                if (null == entity) {
                    throw new IllegalArgumentException("Block can't be null");
                }
            }
        });

        result.add(new NestedValidator1IValidator());

        result.add(new NestedValidator2IValidator());

        result.add(new NestedValidator3IValidator());

        result.add(new NestedValidator4IValidator());

        result.add(new NestedValidator5IValidator());

        return result;
    }

}
