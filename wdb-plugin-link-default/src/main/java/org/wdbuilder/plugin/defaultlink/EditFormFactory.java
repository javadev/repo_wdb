package org.wdbuilder.plugin.defaultlink;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.domain.Link;
import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.input.IParameter;
import org.wdbuilder.plugin.defaultlink.DefaultLinkPluginFacade.Parameter;
import org.wdbuilder.view.line.LineStyle;
import org.wdbuilder.view.line.end.LineEnd;

class EditFormFactory extends UIExistingEntityFormFactory<Link> {

	public EditFormFactory(String diagramKey, Link entity) {
		super(diagramKey, entity);
	}

	@Override
	public TwoColumnForm getForm() {
		PredefinedSelect<Link.LineColor> lineColorSelect = new PredefinedSelect<Link.LineColor>(
				Link.LineColor.values(), Link.LineColor.Black);

		PredefinedSelect<LineEnd> beginTypeSelect = new PredefinedSelect<LineEnd>(
				LineEnd.values(), LineEnd.SIMPLE);
		PredefinedSelect<LineEnd> endTypeSelect = new PredefinedSelect<LineEnd>(
				LineEnd.values(), LineEnd.SOLID_ARROW);

		PredefinedSelect<LineStyle> lineStyleSelect = new PredefinedSelect<LineStyle>(
				LineStyle.values(), LineStyle.SOLID);

		LineEnd begin = entity.getSockets().get(0).getLineEnd();
		LineEnd end = entity.getSockets().get(1).getLineEnd();

		final TwoColumnForm form = new TwoColumnForm("edit-link-save")
				.addHiddenField(BlockParameter.DiagramKey, diagramKey)
				.addTextField(BlockParameter.Name, entity.getName())
				.addSelectField(Parameter.LineColor,
						String.valueOf(entity.getLineColor()), lineColorSelect)
				.addSelectField(Parameter.LineStyle,
						String.valueOf(entity.getLineStyle()), lineStyleSelect)
				.addSelectField(Parameter.StartType, String.valueOf(begin),
						beginTypeSelect)
				.addSelectField(Parameter.EndType, String.valueOf(end),
						endTypeSelect);
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
		result.add(Parameter.LineColor);
		result.add(Parameter.LineStyle);
		result.add(Parameter.StartType);
		result.add(Parameter.EndType);
		return result;
	}

}
