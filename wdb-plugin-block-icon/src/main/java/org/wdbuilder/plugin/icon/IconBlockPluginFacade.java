package org.wdbuilder.plugin.icon;

import org.wdbuilder.domain.Block;
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
import org.wdbuilder.plugin.icon.domain.IconBlock;
import org.wdbuilder.validator.BlockValidator;
import org.wdbuilder.validator.IBlockValidator;

public class IconBlockPluginFacade implements IPluginFacade {

	private static enum IconBlockParameter implements IParameter {
		IconID("iconID", "Icon");

		private final String name;
		private final String label;

		IconBlockParameter(String name, String label) {
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
		return IconBlock.class;
	}

	@Override
	public IRenderer getRenderer() {
		return new IconBlockRenderer();
	}

	@Override
	public Block create(InputAdapter input) {
		final IconBlock result = new IconBlock();
		final String iconIdStr = IconBlockParameter.IconID.getString(input);
		final Icon icon = Icon.valueOf(iconIdStr);
		Dimension size = icon.getSize();
		result.setSize(size);
		result.setName(BlockParameter.Name.getString(input));
		result.setIcon(icon);
		return result;
	}

	@Override
	public IUIActionClick getUIActionCreate(final String diagramKey) {
		return new IUIActionClick() {

			@Override
			public String getTitle() {
				return "New Icon Diagram Block";
			}

			@Override
			public String getResourceId() {
				return "new-image-block";
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
				if (!IconBlock.class.isInstance(entity)) {
					return null;
				}

				final IconBlock block = IconBlock.class.cast(entity);

				final TwoColumnForm form = new TwoColumnForm("none")
						.addReadOnlyField(BlockParameter.BlockKey,
								block.getKey())
						.addReadOnlyField(BlockParameter.Name, block.getName())
						.addReadOnlyField(IconBlockParameter.IconID,
								String.valueOf(block.getIcon()));
				return form;
			}

			@Override
			public TwoColumnForm getEditHTML(String diagramKey, Block entity) {
				if (!IconBlock.class.isInstance(entity)) {
					return null;
				}

				final IconBlock block = IconBlock.class.cast(entity);

				final PredefinedSelect<Icon> iconSelectField = new PredefinedSelect<Icon>(
						Icon.values(), Icon.Avatar);

				final TwoColumnForm form = new TwoColumnForm(
						"edit-icon-block-save")
						.addHiddenField(BlockParameter.DiagramKey, diagramKey)
						.addReadOnlyField(BlockParameter.BlockKey,
								block.getKey())
						.addTextField(BlockParameter.Name, block.getName())
						.addSelectField(IconBlockParameter.IconID,
								String.valueOf(block.getIcon()),
								iconSelectField);

				return form;
			}

			@Override
			public String getEditSubmitCall(String diagramKey, String blockKey) {
				StringBuilder sb = new StringBuilder(128)
						.append("submitEditBlock('").append(diagramKey)
						.append("','").append(blockKey).append("',");
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

			private void appendFieldNames(StringBuilder sb) {
				sb.append('[');
				for (IParameter param : new IParameter[] { BlockParameter.Name,
						BlockParameter.Width, BlockParameter.Height,
						IconBlockParameter.IconID }) {
					sb.append("'").append(param.getName()).append("',");
				}
				int len = sb.length();
				sb.replace(len - 1, len, "]");
			}

			@Override
			public TwoColumnForm getCreateHTML(String diagramKey) {

				final PredefinedSelect<Icon> iconSelectField = new PredefinedSelect<Icon>(
						Icon.values(), Icon.Avatar);
				final TwoColumnForm form = new TwoColumnForm(
						"create-icon-block-save")
						.addHiddenField(BlockParameter.DiagramKey, diagramKey)
						.addTextField(BlockParameter.Name, "")
						.addSelectField(IconBlockParameter.IconID,
								String.valueOf(Icon.Avatar), iconSelectField);
				return form;
			}

			@Override
			public String getCreateBlockTitle() {
				return "New Icon Block";
			}

			@Override
			public String getEditBlockTitle() {
				return "Edit Icon Block";
			}
		};
	}

	@Override
	public IBlockValidator getValidator() {
		return new BlockValidator();
	}
}
