package org.wdbuilder.plugin.common;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.domain.Block;
import org.wdbuilder.gui.PredefinedSelect;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.input.IParameter;
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
				CommonBlock.Background.values(), CommonBlock.Background.Grey);

		final TwoColumnForm form = new TwoColumnForm("edit-common-block-save")
				.addHiddenField(BlockParameter.DiagramKey, diagramKey)
				.addReadOnlyField(BlockParameter.BlockKey, entity.getKey())
				.addTextField(BlockParameter.Name, entity.getName())
				.addSelectField(Parameter.Shape, String.valueOf(activeShape),
						shapeSelectField)

				.addTextField(BlockParameter.Width,
						String.valueOf(entity.getSize().getWidth()))
				.addTextField(BlockParameter.Height,
						String.valueOf(entity.getSize().getHeight()))

				.addSelectField(Parameter.Background,
						String.valueOf(commonBlock.getBackground()),
						selectField);

		return form;
	}

	@Override
	public String getSubmitCall() {
		StringBuilder sb = new StringBuilder(128).append("submitEditBlock('")
				.append(diagramKey).append("','").append(entity.getKey())
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
