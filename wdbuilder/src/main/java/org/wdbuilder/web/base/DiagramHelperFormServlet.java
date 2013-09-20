package org.wdbuilder.web.base;

import org.wdbuilder.web.DiagramServlet;

public abstract class DiagramHelperFormServlet extends DiagramHelperServlet {
    private static final long serialVersionUID = 1L;

    protected abstract void do4DiagramHelperForm(ServletInput input)
            throws Exception;

    @Override
    protected void do4DiagramHelper(ServletInput input) throws Exception {
        do4DiagramHelperForm(input);
    }

    @Override
    protected String getContentType() {
        return DiagramServlet.CONTENT_TYPE_XML;
    }

}
