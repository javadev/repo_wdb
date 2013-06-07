package org.wdbuilder.plugin.icon;

import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UINewBlockFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.plugin.icon.IconBlockPluginFacade.Parameter;
import org.wdbuilder.plugin.icon.domain.IconBlock;

class CreateFormFactory extends UINewBlockFormFactory {

	CreateFormFactory(String diagramKey, Class<?> blockClass) {
		super(diagramKey, blockClass);
	}

	@Override
	public TwoColumnForm getForm() {

		final PredefinedSelect<Icon> iconSelectField = new PredefinedSelect<Icon>(
				Icon.values(), Icon.Avatar);
		final TwoColumnForm form = new TwoColumnForm("create-icon-block-save", "Create Icon Block")
				.addHiddenField(BlockParameter.DiagramKey, diagramKey)
				.addHiddenField(BlockParameter.BlockClass, IconBlock.class.getCanonicalName())
				.addTextField(BlockParameter.Name, "")
				.addSelectField(Parameter.IconID, iconSelectField);
		return form;
	}

	@Override
	public String getSubmitCall() {
		return "submitCreateBlock()";
	}

	@Override
	public String getTitle() {
		return "Create Icon Block";
	}

}
