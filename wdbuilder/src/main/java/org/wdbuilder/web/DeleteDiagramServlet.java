package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import static org.wdbuilder.input.InputParameter.DiagramKey;
import org.wdbuilder.web.base.EmptyOutputServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/delete-diagram")
public class DeleteDiagramServlet extends EmptyOutputServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void do4DiagramService(ServletInput input) throws Exception {
        serviceFacade.getDiagramService().delete(DiagramKey.getString(input));
    }
}
