package org.wdbuilder.plugin.common;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingBlockFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.input.IParameter;
import org.wdbuilder.plugin.common.CommonBlockPluginFacade.Parameter;
import org.wdbuilder.plugin.common.domain.CommonBlock;

class EditFormFactory extends UIExistingBlockFormFactory {

	EditFormFactory(String diagramKey, Block block) {
		super(diagramKey, block);
	}

	@Override
	public TwoColumnForm getForm() {
		if (!CommonBlock.class.isInstance(block)) {
			return null;
		}

		final CommonBlock commonBlock = CommonBlock.class.cast(block);

		final CommonBlock.Shape activeShape = commonBlock.getShape();
		final CommonBlock.Shape[] compatibleShapes = activeShape
				.getCompatible();
		final PredefinedSelect<CommonBlock.Shape> shapeSelectField = new PredefinedSelect<CommonBlock.Shape>(
				compatibleShapes, activeShape);

		final PredefinedSelect<CommonBlock.Background> selectField = new PredefinedSelect<CommonBlock.Background>(
				CommonBlock.Background.values(), CommonBlock.Background.Grey);

		final TwoColumnForm form = new TwoColumnForm("edit-common-block-save")
				.addHiddenField(BlockParameter.DiagramKey, diagramKey)
				.addReadOnlyField(BlockParameter.BlockKey, block.getKey())
				.addTextField(BlockParameter.Name, block.getName())
				.addSelectField(Parameter.Shape, String.valueOf(activeShape),
						shapeSelectField)

				.addTextField(BlockParameter.Width,
						String.valueOf(block.getSize().getWidth()))
				.addTextField(BlockParameter.Height,
						String.valueOf(block.getSize().getHeight()))

				.addSelectField(Parameter.Background,
						String.valueOf(commonBlock.getBackground()),
						selectField);

		return form;
	}

	@Override
	public String getSubmitCall() {
		StringBuilder sb = new StringBuilder(128).append("submitEditBlock('")
				.append(diagramKey).append("','").append(block.getKey())
				.append("',");
		appendFieldNames(sb, getParameters());
		sb.append(')');
		return sb.toString();
	}

	@Override
	public String getTitle() {
		return "Edit Common Block";
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
