package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import static org.wdbuilder.input.InputParameter.DiagramKey;
import static org.wdbuilder.input.InputParameter.LinkKey;
import static org.wdbuilder.input.InputParameter.X;
import static org.wdbuilder.input.InputParameter.Y;
import org.wdbuilder.web.base.EmptyOutputServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/move-link-pivot")
public class MoveLinkPivotServlet extends EmptyOutputServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void do4DiagramService(ServletInput input) throws Exception {

        serviceFacade
                .getDiagramService()
                .getLinkService(DiagramKey.getString(input))
                .setPosition(LinkKey.getString(input), X.getInt(input),
                        Y.getInt(input));
    }
}
