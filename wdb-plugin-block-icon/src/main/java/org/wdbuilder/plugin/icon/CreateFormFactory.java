package org.wdbuilder.plugin.icon;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UINewBlockFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.input.IParameter;
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
		StringBuilder sb = new StringBuilder(128).append("submitCreateBlock('")
				.append(diagramKey).append("','")
				.append(blockClass.getCanonicalName()).append("',");
		appendFieldNames(sb, getParameters());
		sb.append(')');
		return sb.toString();
	}

	@Override
	public String getTitle() {
		return "Create Icon Block";
	}

	private static Iterable<IParameter> getParameters() {
		List<IParameter> result = new ArrayList<IParameter>(2);
		result.add(BlockParameter.Name);
		result.add(Parameter.IconID);
		return result;
	}

}
