package org.wdbuilder.plugin.common;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.gui.UINewBlockFormFactory;
import org.wdbuilder.input.InputParameter;
import org.wdbuilder.input.IParameter;
import org.wdbuilder.input.InputAdapter;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.plugin.IRenderContext;
import org.wdbuilder.plugin.IRenderer;
import org.wdbuilder.plugin.common.domain.CommonBlock;
import org.wdbuilder.plugin.common.domain.CommonBlock.Background;
import org.wdbuilder.plugin.common.domain.CommonBlock.Shape;
import org.wdbuilder.validator.BlockValidator;
import org.wdbuilder.validator.CompositeValidator;
import org.wdbuilder.validator.IValidator;

public class CommonBlockPluginFacade implements IBlockPluginFacade {

	private static final Dimension MIN_BLOCK_SIZE = new Dimension(70, 40);

	static enum Parameter implements IParameter {
		Shape("shape", "Shape"), Background("background", "Block Background");

		private final String name;
		private final String label;

		Parameter(String name, String label) {
			this.name = name;
			this.label = label;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getString(InputAdapter input) {
			return input.getString(this);
		}

		@Override
		public int getInt(InputAdapter input) {
			return input.getInt(this);
		}

		@Override
		public boolean getBoolean(InputAdapter input) {
			return input.getBoolean(this);
		}

		@Override
		public String getDisplayName() {
			return label;
		}
	}

	@Override
	public Class<?> getEntityClass() {
		return CommonBlock.class;
	}

	@Override
	public IRenderer<Block,IRenderContext> getRenderer() {
		return new CommonBlockRenderer();
	}

	@Override
	public Block create(InputAdapter input) {
		final CommonBlock result = new CommonBlock();
		Dimension size = new Dimension(InputParameter.Width.getInt(input),
				InputParameter.Height.getInt(input));
		result.setSize(size);
		result.setName(InputParameter.Name.getString(input));
		result.setBackground(Background.valueOf(Parameter.Background
				.getString(input)));
		result.setShape(Shape.valueOf(Parameter.Shape.getString(input)));
		return result;
	}

	@Override
	public IUIActionClick getUIActionCreate(final String diagramKey) {
		return new IUIActionClick() {

			@Override
			public String getTitle() {
				return "New Common Diagram Block";
			}

			@Override
			public String getResourceId() {
				return "icon-cog";
			}

			@Override
			public String getOnClickHandler() {
				return "openCreateBlockDialog(" + diagramKey + ", '"
						+ getEntityClass().getCanonicalName() + "' )";
			}
			
			@Override
			public String getClassName() {
				return "btn-success";
			}

		};
	}

	@Override
	public IValidator<Block> getValidator() {
		return new CompositeValidator<Block>(new BlockValidator()) {

			@Override
			protected Iterable<IValidator<Block>> getNestedValidators() {
				List<IValidator<Block>> result = new ArrayList<IValidator<Block>>(
						2);
				result.add(new IValidator<Block>() {

					@Override
					public void validate(Diagram diagram, Block entity)
							throws IllegalArgumentException {
						if (entity.getSize().getWidth() < MIN_BLOCK_SIZE
								.getWidth()) {
							throw new IllegalArgumentException(
									"Block width is too small");
						}
					}
				});
				result.add(new IValidator<Block>() {

					@Override
					public void validate(Diagram diagram, Block entity)
							throws IllegalArgumentException {
						if (entity.getSize().getHeight() < MIN_BLOCK_SIZE
								.getHeight()) {
							throw new IllegalArgumentException(
									"Block height is too small");
						}
					}
				});
				return result;
			}
		};
	}

	@Override
	public UINewBlockFormFactory getCreateFormFactory(String diagramKey) {
		return new CreateFormFactory(diagramKey, getEntityClass());
	}

	@Override
	public UIExistingEntityFormFactory<Block> getEditFormFactory(
			String diagramKey, Block block) {
		return new EditFormFactory(diagramKey, block);
	}

}
