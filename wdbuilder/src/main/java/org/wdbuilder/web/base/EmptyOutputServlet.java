package org.wdbuilder.web.base;



public abstract class EmptyOutputServlet extends DiagramServiceServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected String getContentType() {
        return "text/plain";
    }

}
