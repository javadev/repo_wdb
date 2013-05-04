package org.wdbuilder.plugin.icon;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingBlockFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.plugin.icon.IconBlockPluginFacade.Parameter;
import org.wdbuilder.plugin.icon.domain.IconBlock;

class ViewFormFactory extends UIExistingBlockFormFactory {

	ViewFormFactory(String diagramKey, Block block) {
		super(diagramKey, block);
	}

	@Override
	public TwoColumnForm getForm() {
		if (!IconBlock.class.isInstance(block)) {
			return null;
		}

		final IconBlock iconBlock = IconBlock.class.cast(block);

		final TwoColumnForm form = new TwoColumnForm("none")
				.addReadOnlyField(BlockParameter.BlockKey, block.getKey())
				.addReadOnlyField(BlockParameter.Name, block.getName())
				.addReadOnlyField(Parameter.IconID,
						String.valueOf(iconBlock.getIcon()));
		return form;
	}

	@Override
	public String getSubmitCall() {
		return null;
	}

	@Override
	public String getTitle() {
		return "View Icon Block";
	}

}
