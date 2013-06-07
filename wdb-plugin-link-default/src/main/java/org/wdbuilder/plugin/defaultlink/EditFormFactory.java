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
				Link.LineColor.values(), entity.getLineColor());

		LineEnd begin = entity.getSockets().get(0).getLineEnd();
		LineEnd end = entity.getSockets().get(1).getLineEnd();

		PredefinedSelect<LineEnd> beginTypeSelect = new PredefinedSelect<LineEnd>(
				LineEnd.values(), begin);
		PredefinedSelect<LineEnd> endTypeSelect = new PredefinedSelect<LineEnd>(
				LineEnd.values(), end);

		PredefinedSelect<LineStyle> lineStyleSelect = new PredefinedSelect<LineStyle>(
				LineStyle.values(), entity.getLineStyle());

		final TwoColumnForm form = new TwoColumnForm("edit-link-save", "Edit Link")
				.addHiddenField(BlockParameter.DiagramKey, diagramKey)
				.addHiddenField(BlockParameter.LinkKey, entity.getKey())
				.addTextField(BlockParameter.Name, entity.getName())
				.addSelectField(Parameter.LineColor, lineColorSelect)
				.addSelectField(Parameter.LineStyle, lineStyleSelect)
				.addSelectField(Parameter.StartType, beginTypeSelect)
				.addSelectField(Parameter.EndType, endTypeSelect);
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
