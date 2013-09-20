package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Block;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.web.base.FrameServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/moving-block")
public class MovingBlockFrameServlet extends FrameServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void do4Frame(ServletInput input) throws Exception {
        final Block block = getBlock(input);
        if (null == block) {
            return;
        }
        FrameServlet.Image image = new FrameServlet.Image(
                getDiagram(input), block, "carImage", null);
        new HtmlWriter(input.getResponse().getWriter()).write(image);
    }
}
