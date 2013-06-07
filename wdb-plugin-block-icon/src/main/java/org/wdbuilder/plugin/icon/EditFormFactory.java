package org.wdbuilder.plugin.icon;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.plugin.icon.IconBlockPluginFacade.Parameter;
import org.wdbuilder.plugin.icon.domain.IconBlock;

class EditFormFactory extends UIExistingEntityFormFactory<Block> {

	EditFormFactory(String diagramKey, Block block) {
		super(diagramKey, block);
	}

	@Override
	public TwoColumnForm getForm() {
		if (!IconBlock.class.isInstance(entity)) {
			return null;
		}

		final IconBlock iconBlock = IconBlock.class.cast(entity);

		final PredefinedSelect<Icon> iconSelectField = new PredefinedSelect<Icon>(
				Icon.values(), iconBlock.getIcon());

		final TwoColumnForm form = new TwoColumnForm("edit-icon-block-save", "Edit Icon Block")
				.addHiddenField(BlockParameter.DiagramKey, diagramKey)
				.addReadOnlyField(BlockParameter.BlockKey, entity.getKey())
				.addTextField(BlockParameter.Name, entity.getName())
				.addSelectField(Parameter.IconID, iconSelectField);

		return form;
	}

	@Override
	public String getSubmitCall() {
		return "submitEditBlock()'";
	}

	@Override
	public String getTitle() {
		return "Edit Icon Block";
	}

}
