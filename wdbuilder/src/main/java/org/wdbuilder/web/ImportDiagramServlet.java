package org.wdbuilder.web;

import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.gui.TwoColumnForm;
import static org.wdbuilder.input.InputParameter.DiagramKey;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.web.base.DiagramServiceServlet;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/import-diagram")
public class ImportDiagramServlet extends DiagramServiceServlet {

    @Override
    protected void do4DiagramService(ServletInput input) throws Exception {
        final PrintWriter writer = input.getResponse().getWriter();

        final TwoColumnForm form = new TwoColumnForm("import-diagram-save",
                "Import Diagram").addFileField(DiagramKey).addFooter(
                "submitImportDiagram()", "refreshDiagramList()");
        form.setEncodeType("multipart/form-data; boundary=--------------------6361092470262692186");

        new HtmlWriter(writer).write(form);
    }

    @Override
    protected String getContentType() {
        return DiagramServlet.CONTENT_TYPE_XML;
    }
}
