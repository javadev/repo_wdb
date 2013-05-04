package org.wdbuilder.plugin.icon;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.gui.UIExistingBlockFormFactory;
import org.wdbuilder.gui.UINewBlockFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.input.IParameter;
import org.wdbuilder.input.InputAdapter;
import org.wdbuilder.plugin.IPluginFacade;
import org.wdbuilder.plugin.IRenderer;
import org.wdbuilder.plugin.icon.domain.IconBlock;
import org.wdbuilder.validator.BlockValidator;
import org.wdbuilder.validator.IValidator;

public class IconBlockPluginFacade implements IPluginFacade {

	static enum Parameter implements IParameter {
		IconID("iconID", "Icon");

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
		final String iconIdStr = Parameter.IconID.getString(input);
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
	public IValidator getValidator() {
		return new BlockValidator();
	}

	@Override
	public UINewBlockFormFactory getCreateBlockFormFactory(String diagramKey) {
		return new CreateFormFactory(diagramKey, getBlockClass());
	}

	@Override
	public UIExistingBlockFormFactory getViewBlockFormFactory(
			String diagramKey, Block block) {
		return new ViewFormFactory(diagramKey, block);
	}

	@Override
	public UIExistingBlockFormFactory getEditBlockFormFactory(
			String diagramKey, Block block) {
		return new EditFormFactory(diagramKey, block);
	}
}
