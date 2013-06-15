package org.wdbuilder.plugin.common;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.InputParameter;
import org.wdbuilder.plugin.common.CommonBlockPluginFacade.Parameter;
import org.wdbuilder.plugin.common.domain.CommonBlock;

class EditFormFactory extends UIExistingEntityFormFactory<Block> {

	EditFormFactory(String diagramKey, Block block) {
		super(diagramKey, block);
	}

	@Override
	public TwoColumnForm getForm() {
		if (!CommonBlock.class.isInstance(entity)) {
			return null;
		}

		final CommonBlock commonBlock = CommonBlock.class.cast(entity);

		final CommonBlock.Shape activeShape = commonBlock.getShape();
		final CommonBlock.Shape[] compatibleShapes = activeShape
				.getCompatible();
		final PredefinedSelect<CommonBlock.Shape> shapeSelectField = new PredefinedSelect<CommonBlock.Shape>(
				compatibleShapes, activeShape);

		final PredefinedSelect<CommonBlock.Background> selectField = new PredefinedSelect<CommonBlock.Background>(
				CommonBlock.Background.values(), commonBlock.getBackground() );

		final TwoColumnForm form = new TwoColumnForm("edit-common-block-save", "Edit Common Block")
				.addHiddenField(InputParameter.DiagramKey, diagramKey)
				.addReadOnlyField(InputParameter.BlockKey, entity.getKey())
				.addTextField(InputParameter.Name, entity.getName())
				.addSelectField(Parameter.Shape, shapeSelectField)

				.addTextField(InputParameter.Width,
						String.valueOf(entity.getSize().getWidth()))
				.addTextField(InputParameter.Height,
						String.valueOf(entity.getSize().getHeight()))

				.addSelectField(Parameter.Background, selectField);

		return form;
	}

	@Override
	public String getSubmitCall() {
		return "submitEditBlock()";
	}

	@Override
	public String getTitle() {
		return "Edit Common Block";
	}

}
