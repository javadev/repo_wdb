package org.wdbuilder.plugin.common;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UINewBlockFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.input.IParameter;
import org.wdbuilder.plugin.common.CommonBlockPluginFacade.Parameter;
import org.wdbuilder.plugin.common.domain.CommonBlock;

class CreateFormFactory extends UINewBlockFormFactory {

	CreateFormFactory(String diagramKey, Class<?> blockClass) {
		super(diagramKey, blockClass);
	}

	@Override
	public TwoColumnForm getForm() {
		final PredefinedSelect<CommonBlock.Shape> shapeSelectField = new PredefinedSelect<CommonBlock.Shape>(
				CommonBlock.Shape.values(), CommonBlock.Shape.Rectangle);

		final PredefinedSelect<CommonBlock.Background> backgroundSelectField = new PredefinedSelect<CommonBlock.Background>(
				CommonBlock.Background.values(), CommonBlock.Background.Grey);

		final TwoColumnForm form = new TwoColumnForm("create-common-block-save")
				.addTextField(BlockParameter.Name, "")
				.addSelectField(Parameter.Shape, "", shapeSelectField)
				.addTextField(BlockParameter.Width, "70")
				.addTextField(BlockParameter.Height, "40")
				.addSelectField(Parameter.Background,
						String.valueOf(CommonBlock.Background.Grey),
						backgroundSelectField);
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
		return "Create Common Block";
	}

	private static Iterable<IParameter> getParameters() {
		List<IParameter> result = new ArrayList<IParameter>(2);
		result.add(BlockParameter.Name);
		result.add(BlockParameter.Width);
		result.add(BlockParameter.Height);
		result.add(Parameter.Background);
		result.add(Parameter.Shape);
		return result;
	}

}
