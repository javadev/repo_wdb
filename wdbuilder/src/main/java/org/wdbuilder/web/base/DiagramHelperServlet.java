package org.wdbuilder.web.base;

import org.wdbuilder.input.InputParameter;

import static org.apache.commons.lang.StringUtils.isEmpty;

public abstract class DiagramHelperServlet extends DiagramServiceServlet {
    private static final long serialVersionUID = 1L;

    protected abstract void do4DiagramHelper(ServletInput input)
            throws Exception;

    @Override
    protected void do4DiagramService(ServletInput input) throws Exception {
        if (null == serviceFacade) {
            throw new IllegalArgumentException(
                    "Diagram service is not initialized");
        }
        final String key = InputParameter.DiagramKey.getString(input);
        if (isEmpty(key)) {
            throw new IllegalArgumentException("Diagram key is undefined");
        }
        do4DiagramHelper(input);
    }

}
