package org.wdbuilder.plugin.icon;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.plugin.icon.IconBlockPluginFacade.Parameter;
import org.wdbuilder.plugin.icon.domain.IconBlock;

@Deprecated
class ViewFormFactory extends UIExistingEntityFormFactory<Block> {

	ViewFormFactory(String diagramKey, Block block) {
		super(diagramKey, block);
	}

	@Override
	public TwoColumnForm getForm() {
		if (!IconBlock.class.isInstance(entity)) {
			return null;
		}

		final IconBlock iconBlock = IconBlock.class.cast(entity);

		final TwoColumnForm form = new TwoColumnForm("none", "View Icon Block Details")
				.addReadOnlyField(BlockParameter.BlockKey, entity.getKey())
				.addReadOnlyField(BlockParameter.Name, entity.getName())
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
