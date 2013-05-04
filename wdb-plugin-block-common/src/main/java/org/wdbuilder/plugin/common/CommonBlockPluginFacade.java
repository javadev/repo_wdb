package org.wdbuilder.plugin.common;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.gui.IUIFormFactory;
import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.input.IParameter;
import org.wdbuilder.input.InputAdapter;
import org.wdbuilder.plugin.IPluginFacade;
import org.wdbuilder.plugin.IRenderer;
import org.wdbuilder.plugin.common.domain.CommonBlock;

import org.wdbuilder.plugin.common.domain.CommonBlock.Background;
import org.wdbuilder.plugin.common.domain.CommonBlock.Shape;
import org.wdbuilder.validator.BlockValidator;
import org.wdbuilder.validator.CompositeBlockValidator;
import org.wdbuilder.validator.IBlockValidator;

public class CommonBlockPluginFacade implements IPluginFacade {

	private static final Dimension MIN_BLOCK_SIZE = new Dimension(70, 40);

	private static enum CommonBlockParameter implements IParameter {
		Shape("shape", "Shape"), Background("background", "Block Background");

		private final String name;
		private final String label;

		CommonBlockParameter(String name, String label) {
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
		public String getLabel() {
			return label;
		}
	}

	@Override
	public Class<?> getBlockClass() {
		return CommonBlock.class;
	}

	@Override
	public IRenderer getRenderer() {
		return new CommonBlockRenderer();
	}

	@Override
	public Block create(InputAdapter input) {
		final CommonBlock result = new CommonBlock();
		Dimension size = new Dimension(BlockParameter.Width.getInt(input),
				BlockParameter.Height.getInt(input));
		result.setSize(size);
		result.setName(BlockParameter.Name.getString(input));
		result.setBackground(Background.valueOf(CommonBlockParameter.Background
				.getString(input)));
		result.setShape(Shape.valueOf(CommonBlockParameter.Shape
				.getString(input)));
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
				return "new-common-block";
			}

			@Override
			public String getOnClickHandler() {
				return "openCreateBlockDialog(" + diagramKey + ", '"
						+ getBlockClass().getCanonicalName() + "' )";
			}

		};
	}

	@Override
	public IUIFormFactory getFormFactory() {
		return new IUIFormFactory() {

			@Override
			public TwoColumnForm getViewHTML(String diagramKey, Block entity) {

				if (!CommonBlock.class.isInstance(entity)) {
					return null;
				}

				final CommonBlock block = CommonBlock.class.cast(entity);

				final TwoColumnForm form = new TwoColumnForm("none")
						.addReadOnlyField(BlockParameter.BlockKey,
								block.getKey())
						.addReadOnlyField(BlockParameter.Name, block.getName())
						.addReadOnlyField(CommonBlockParameter.Shape,
								block.getShape().getDisplayName());
				return form;
			}

			@Override
			public TwoColumnForm getEditHTML(String diagramKey, Block entity) {
				// TODO: restrictions were skipped for a while (2013/04/28)

				if (!CommonBlock.class.isInstance(entity)) {
					return null;
				}

				final CommonBlock block = CommonBlock.class.cast(entity);

				final CommonBlock.Shape activeShape = block.getShape();
				final CommonBlock.Shape[] compatibleShapes = activeShape
						.getCompatible();
				final PredefinedSelect<CommonBlock.Shape> shapeSelectField = new PredefinedSelect<CommonBlock.Shape>(
						compatibleShapes, activeShape);

				final PredefinedSelect<CommonBlock.Background> selectField = new PredefinedSelect<CommonBlock.Background>(
						CommonBlock.Background.values(),
						CommonBlock.Background.Grey);

				final TwoColumnForm form = new TwoColumnForm(
						"edit-common-block-save")
						.addHiddenField(BlockParameter.DiagramKey, diagramKey)
						.addReadOnlyField(BlockParameter.BlockKey,
								block.getKey())
						.addTextField(BlockParameter.Name, block.getName())
						.addSelectField(CommonBlockParameter.Shape,
								String.valueOf(activeShape), shapeSelectField)

						.addTextField(BlockParameter.Width,
								String.valueOf(block.getSize().getWidth()))
						.addTextField(BlockParameter.Height,
								String.valueOf(block.getSize().getHeight()))

						.addSelectField(CommonBlockParameter.Background,
								String.valueOf(block.getBackground()),
								selectField);

				return form;
			}

			@Override
			public TwoColumnForm getCreateHTML(String diagramKey) {
				// TODO: restrictions were skipped for a while (2013/04/28)

				final PredefinedSelect<CommonBlock.Shape> shapeSelectField = new PredefinedSelect<CommonBlock.Shape>(
						CommonBlock.Shape.values(), CommonBlock.Shape.Rectangle);

				final PredefinedSelect<CommonBlock.Background> backgroundSelectField = new PredefinedSelect<CommonBlock.Background>(
						CommonBlock.Background.values(),
						CommonBlock.Background.Grey);

				final TwoColumnForm form = new TwoColumnForm(
						"create-common-block-save")
						.addTextField(BlockParameter.Name, "")
						.addSelectField(CommonBlockParameter.Shape, "",
								shapeSelectField)
						.addTextField(BlockParameter.Width, "70")
						.addTextField(BlockParameter.Height, "40")
						.addSelectField(CommonBlockParameter.Background,
								String.valueOf(CommonBlock.Background.Grey),
								backgroundSelectField);
				return form;
			}

			@Override
			public String getEditSubmitCall(String diagramKey, String blockKey) {
				StringBuilder sb = new StringBuilder(128)
						.append("submitEditBlock('").append(diagramKey)
						.append("','")
						.append( blockKey )
						.append("',");
				appendFieldNames(sb);
				sb.append(')');
				return sb.toString();
			}

			@Override
			public String getCreateSubmitCall(String diagramKey) {
				StringBuilder sb = new StringBuilder(128)
						.append("submitCreateBlock('").append(diagramKey)
						.append("','")
						.append(getBlockClass().getCanonicalName())
						.append("',");
				appendFieldNames(sb);
				sb.append(')');
				return sb.toString();
			}

			@Override
			public String getCreateBlockTitle() {
				return "Create Common Block";
			}

			@Override
			public String getEditBlockTitle() {
				return "Edit Common Block";
			}

			private void appendFieldNames(StringBuilder sb) {
				sb.append('[');
				for (IParameter param : new IParameter[] { BlockParameter.Name,
						BlockParameter.Width, BlockParameter.Height,
						CommonBlockParameter.Background,
						CommonBlockParameter.Shape }) {
					sb.append("'").append(param.getName()).append("',");
				}
				int len = sb.length();
				sb.replace(len - 1, len, "]");
			}
		};
	}

	@Override
	public IBlockValidator getValidator() {
		return new CompositeBlockValidator(new BlockValidator()) {

			@Override
			protected Iterable<IBlockValidator> getNestedValidators() {
				List<IBlockValidator> result = new ArrayList<IBlockValidator>(2);
				result.add(new IBlockValidator() {

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
				result.add(new IBlockValidator() {

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

}
