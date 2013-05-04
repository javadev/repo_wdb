package org.wdbuilder.plugin.common;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.plugin.common.CommonBlockPluginFacade.Parameter;
import org.wdbuilder.plugin.common.domain.CommonBlock;

class ViewFormFactory extends UIExistingEntityFormFactory<Block> {

	ViewFormFactory(String diagramKey, Block block) {
		super(diagramKey, block);
	}

	@Override
	public TwoColumnForm getForm() {
		if (!CommonBlock.class.isInstance(entity)) {
			return null;
		}

		final CommonBlock commonBlock = CommonBlock.class.cast(entity);

		final TwoColumnForm form = new TwoColumnForm("none")
				.addReadOnlyField(BlockParameter.BlockKey, entity.getKey())
				.addReadOnlyField(BlockParameter.Name, entity.getName())
				.addReadOnlyField(Parameter.Shape,
						commonBlock.getShape().getDisplayName());
		return form;
	}

	@Override
	public String getSubmitCall() {
		return null;
	}

	@Override
	public String getTitle() {
		return "View Common Block";
	}

}
