package org.wdbuilder.plugin.defaultlink;

import org.wdbuilder.domain.Link;
import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.InputParameter;
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
				.addHiddenField(InputParameter.DiagramKey, diagramKey)
				.addHiddenField(InputParameter.LinkKey, entity.getKey())
				.addTextField(InputParameter.Name, entity.getName())
				.addSelectField(Parameter.LineColor, lineColorSelect)
				.addSelectField(Parameter.LineStyle, lineStyleSelect)
				.addSelectField(Parameter.StartType, beginTypeSelect)
				.addSelectField(Parameter.EndType, endTypeSelect);
		return form;
	}

	@Override
	public String getSubmitCall() {
		StringBuilder sb = new StringBuilder(128).append("submitEditLink('")
				.append(diagramKey).append("')");
		return sb.toString();
	}

	@Override
	public String getTitle() {
		return "Edit Link";
	}
}
