package org.wdbuilder.web.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdbuilder.service.IServiceFacade;
import org.wdbuilder.service.ServletRelatedStaticServiceFacade;
import org.wdbuilder.utility.DiagramHelper;

public abstract class DiagramServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String CONTENT_TYPE_XML = "text/xml;charset=UTF-8";

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

	protected final DiagramHelper createDiagramHelper(final String key) {
		return new DiagramHelper(serviceFacade.getDiagramService().getDiagram(
				key));
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
}
