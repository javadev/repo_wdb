package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Block;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.web.base.FrameServlet;
import org.wdbuilder.web.base.ServletInput;


import static org.apache.commons.lang.StringUtils.isEmpty;

@WebServlet("/moving-block")
public class MovingBlockFrameServlet extends FrameServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4Frame(ServletInput input) throws Exception {
		final String blockKey = BlockParameter.BlockKey.getString(input);
		if (isEmpty(blockKey)) {
			return;
		}
		final Block block = diagramHelper.findBlockByKey(blockKey);
		if (null == block) {
			return;
		}
		FrameServlet.Image image = new FrameServlet.Image(diagramHelper.getDiagram(), block, "carImage", blockKey, null);
		new HtmlWriter(input.getResponse().getWriter()).write(image);
	}
}
