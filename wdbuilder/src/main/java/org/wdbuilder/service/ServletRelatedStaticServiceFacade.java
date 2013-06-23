package org.wdbuilder.service;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Link;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.plugin.ILinkPluginFacade;
import org.wdbuilder.plugin.ILinkRenderContext;
import org.wdbuilder.plugin.IRenderContext;

// TODO this initialization should be substituted by some IoC in the future (2013/05/06)
public class ServletRelatedStaticServiceFacade implements IServiceFacade {
	private static final Logger LOG = Logger
			.getLogger(ServletRelatedStaticServiceFacade.class);

	public static IServiceFacade instance = null;

	public static IServiceFacade getInstance(ServletConfig servletConfig) {
		if (null == instance) {
			synchronized (ServletRelatedStaticServiceFacade.class) {
				if (null == instance) {
					instance = new ServletRelatedStaticServiceFacade(
							servletConfig);
				}
			}
		}
		return instance;
	}

	private final DiagramService diagramService = new StaticDiagramService(this);
	private final IPluginFacadeRepository<Block, IBlockPluginFacade, IRenderContext> blockPluginRepository;
	private final IPluginFacadeRepository<Link, ILinkPluginFacade, ILinkRenderContext> linkPluginRepository;

	private ServletRelatedStaticServiceFacade(ServletConfig servletConfig) {
		LOG.info("initialization by " + servletConfig);
		blockPluginRepository = new PluginFacadeRepository<Block, IBlockPluginFacade, IRenderContext>(
				getBlockPlugins(servletConfig));
		linkPluginRepository = new PluginFacadeRepository<Link, ILinkPluginFacade, ILinkRenderContext>(
				getLinkPlugins(servletConfig));
		LOG.info("initialization: done");
	}

	@Override
	public DiagramService getDiagramService() {
		return diagramService;
	}

	@Override
	public IPluginFacadeRepository<Block, IBlockPluginFacade, IRenderContext> getBlockPluginRepository() {
		return blockPluginRepository;
	}

	@Override
	public IPluginFacadeRepository<Link, ILinkPluginFacade, ILinkRenderContext> getLinkPluginRepository() {
		return linkPluginRepository;
	}

	private static Collection<IBlockPluginFacade> getBlockPlugins(
			ServletConfig servletConfig) {
		Set<IBlockPluginFacade> result = new LinkedHashSet<IBlockPluginFacade>(
				2);

		String contextParamStr = servletConfig.getServletContext()
				.getInitParameter("block-plugins");

		if (StringUtils.isEmpty(contextParamStr)) {
			return result;
		}

		String[] pairs = contextParamStr.split(",");
		for (final String str : pairs) {
			Class<?> klass = getClass(str);
			if (null != klass) {
				try {
					Object obj = klass.newInstance();
					if (IBlockPluginFacade.class.isInstance(obj)) {
						IBlockPluginFacade facade = IBlockPluginFacade.class
								.cast(obj);
						result.add(facade);

						LOG.info("plugin " + klass.getName() + " ("
								+ facade.getEntityClass().getName() + ") - OK");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return result;
	}

	private static Collection<ILinkPluginFacade> getLinkPlugins(
			ServletConfig servletConfig) {
		Set<ILinkPluginFacade> result = new LinkedHashSet<ILinkPluginFacade>(2);

		String contextParamStr = servletConfig.getServletContext()
				.getInitParameter("link-plugins");

		if (StringUtils.isEmpty(contextParamStr)) {
			return result;
		}
		String[] pairs = contextParamStr.split(",");
		for (final String str : pairs) {
			Class<?> klass = getClass(str);
			if (null != klass) {
				try {
					Object obj = klass.newInstance();
					if (ILinkPluginFacade.class.isInstance(obj)) {
						ILinkPluginFacade facade = ILinkPluginFacade.class
								.cast(obj);
						result.add(facade);

						LOG.info("plugin " + klass.getName() + " ("
								+ facade.getEntityClass().getName() + ") - OK");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return result;
	}

	private static Class<?> getClass(String str) {
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
