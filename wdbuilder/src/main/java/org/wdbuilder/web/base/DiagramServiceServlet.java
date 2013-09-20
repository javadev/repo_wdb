package org.wdbuilder.web.base;

import static org.wdbuilder.input.InputParameter.BlockKey;
import static org.wdbuilder.input.InputParameter.DiagramKey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.service.IServiceFacade;
import org.wdbuilder.service.ServletRelatedStaticServiceFacade;

public abstract class DiagramServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static final String CONTENT_TYPE_XML = "text/xml;charset=UTF-8";

    @Inject
    protected IServiceFacade serviceFacade;

    protected abstract void do4DiagramService(ServletInput input)
            throws Exception;

    protected abstract String getContentType();

    @Override
    public void init() {
        serviceFacade = ServletRelatedStaticServiceFacade
                .getInstance(getServletConfig());
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            do4DiagramService(new ServletInput(request, response));
            response.setStatus(200);
            response.setContentType(getContentType());
            flush(response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
    

    protected final Diagram getDiagram(final String key) {
        return serviceFacade.getDiagramService().get(key);
    }   

    protected void flush(HttpServletResponse response) throws IOException {
        response.flushBuffer();
    }

    public static final void addParameter(StringBuilder sb, String name,
            Object value) {
        sb.append('&');
        sb.append(name);
        sb.append('=');
        sb.append(value);
    }
    
    protected Diagram getDiagram(ServletInput input) {
        return getDiagram(DiagramKey.getString(input));
    }

    protected Block getBlock(ServletInput input) {
        Diagram diagram = getDiagram(input);
        if (null == diagram) {
            return null;
        }
        return diagram.getBlock(BlockKey.getString(input));
    }
    
    protected Class<?>[] getClassesForMarshaling() {
        List<Class<?>> list = new ArrayList<Class<?>>(4);
        list.addAll(serviceFacade.getBlockPluginRepository().getEntityClasses());
        list.addAll(serviceFacade.getLinkPluginRepository().getEntityClasses());
        list.add(Diagram.class);

        Class<?>[] result = new Class<?>[list.size()];
        return list.toArray(result);

    }
}
