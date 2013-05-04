package org.wdbuilder.web.base;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.wdbuilder.plugin.IPluginFacade;
import org.wdbuilder.service.DiagramService;
import org.wdbuilder.service.StaticDiagramService;
import org.wdbuilder.service.StaticPluginFacadeRepository;
import org.wdbuilder.utility.DiagramHelper;
import org.wdbuilder.utility.IPluginFacadeRepository;

public abstract class DiagramServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger LOG = Logger.getLogger(DiagramServiceServlet.class);

	public static final String CONTENT_TYPE_XML = "text/xml;charset=UTF-8";

	protected DiagramService service = null;
	protected IPluginFacadeRepository pluginFacadeRepository = null;

	protected abstract void do4DiagramService(ServletInput input)
			throws Exception;

	protected abstract String getContentType();

	@Override
	public void init() {
		// TODO replace this ugly code by some inversion of control (2013/05/03)
		StaticDiagramService serviceImpl = (StaticDiagramService) StaticDiagramService.getInstance();
		this.service = serviceImpl;

		PluginRepositoryConfiguration config = new PluginRepositoryConfiguration();

		this.pluginFacadeRepository = StaticPluginFacadeRepository.getInstance(
				config.getBlockPlugins());
		serviceImpl.setPluginRepository(pluginFacadeRepository);
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
		return new DiagramHelper(service.getDiagram(key));
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

	private class PluginRepositoryConfiguration {
		private Collection<IPluginFacade> getBlockPlugins() {
			Set<IPluginFacade> result = new HashSet<IPluginFacade>(2);

			String contextParamStr = getServletContext().getInitParameter(
					"block-plugins");
			if (StringUtils.isEmpty(contextParamStr)) {
				return result;
			}
			String[] pairs = contextParamStr.split(",");
			for (final String str : pairs) {
				Class<?> klass = getClass(str);
				if (null != klass) {
					try {
						Object obj = klass.newInstance();
						if (IPluginFacade.class.isInstance(obj)) {
							IPluginFacade facade = IPluginFacade.class
									.cast(obj);
							result.add(facade);

							LOG.info("plugin " + klass.getName() + " ("
									+ facade.getBlockClass().getName() + ") - OK");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}

			return result;
		}

		private Class<?> getClass(String str) {
			try {
				// TODO: doubtful code (2013/04/28)
				Class<?> result = Class.forName(str.trim());
				return result;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}

		}

	}
}
