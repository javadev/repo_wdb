package org.wdbuilder.plugin.defaultlink;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.domain.Link;
import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.input.IParameter;

class EditFormFactory extends UIExistingEntityFormFactory<Link> {

	public EditFormFactory(String diagramKey, Link entity) {
		super(diagramKey, entity);
	}

	@Override
	public TwoColumnForm getForm() {
		PredefinedSelect<Link.LineColor> lineColorSelect = new PredefinedSelect<Link.LineColor>(
				Link.LineColor.values(), Link.LineColor.Black);

		final TwoColumnForm form = new TwoColumnForm("edit-link-save")
				.addHiddenField(BlockParameter.DiagramKey, diagramKey)
				.addTextField(BlockParameter.Name, entity.getName())
				.addSelectField(BlockParameter.LineColor,
						String.valueOf(entity.getLineColor()), lineColorSelect);
		return form;
	}

	@Override
	public String getSubmitCall() {
		StringBuilder sb = new StringBuilder(128).append("submitEditLink('")
				.append(diagramKey).append("','").append(entity.getKey())
				.append("',");
		appendFieldNames(sb, getParameters());
		sb.append(')');
		return sb.toString();
	}

	@Override
	public String getTitle() {
		return "Edit Link";
	}

	private static Iterable<IParameter> getParameters() {
		List<IParameter> result = new ArrayList<IParameter>(2);
		result.add(BlockParameter.Name);
		result.add(BlockParameter.LineColor);
		return result;
	}

}
