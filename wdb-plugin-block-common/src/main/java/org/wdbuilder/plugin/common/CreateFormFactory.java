package org.wdbuilder.plugin.common;

import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UINewBlockFormFactory;
import org.wdbuilder.input.BlockParameter;
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

		final TwoColumnForm form = new TwoColumnForm("create-common-block-save", "Create Common Block" )
				.addHiddenField(BlockParameter.DiagramKey, this.diagramKey )
				.addHiddenField(BlockParameter.BlockClass, CommonBlock.class.getCanonicalName() )
				.addTextField(BlockParameter.Name, "")
				.addSelectField(Parameter.Shape, shapeSelectField)
				.addTextField(BlockParameter.Width, "70")
				.addTextField(BlockParameter.Height, "40")
				.addSelectField(Parameter.Background, backgroundSelectField);
		return form;
	}

	@Override
	public String getTitle() {
		return "Create Common Block";
	}
}
