package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Block;
import static org.wdbuilder.input.InputParameter.BlockKey;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.web.base.FrameServlet;
import org.wdbuilder.web.base.ServletInput;

import static org.apache.commons.lang.StringUtils.isEmpty;

@WebServlet("/moving-block")
public class MovingBlockFrameServlet extends FrameServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4Frame(ServletInput input) throws Exception {
		final String blockKey = BlockKey.getString(input);
		if (isEmpty(blockKey)) {
			return;
		}
		final Block block = diagramHelper.getDiagram().getBlock(blockKey);
		if (null == block) {
			return;
		}
		FrameServlet.Image image = new FrameServlet.Image(
				diagramHelper.getDiagram(), block, "carImage", null);
		new HtmlWriter(input.getResponse().getWriter()).write(image);
	}
}
