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
	private final IPluginFacadeRepository<Block, IBlockPluginFacade> blockPluginRepository;
	private final IPluginFacadeRepository<Link, ILinkPluginFacade> linkPluginRepository;

	private ServletRelatedStaticServiceFacade(ServletConfig servletConfig) {
		LOG.info("initialization by " + servletConfig);
		blockPluginRepository = new PluginFacadeRepository<Block, IBlockPluginFacade>(
				getBlockPlugins(servletConfig));
		linkPluginRepository = new PluginFacadeRepository<Link, ILinkPluginFacade>(
				getLinkPlugins(servletConfig));
		LOG.info("initialization: done");
	}

	@Override
	public DiagramService getDiagramService() {
		return diagramService;
	}

	@Override
	public IPluginFacadeRepository<Block, IBlockPluginFacade> getBlockPluginRepository() {
		return blockPluginRepository;
	}

	@Override
	public IPluginFacadeRepository<Link, ILinkPluginFacade> getLinkPluginRepository() {
		return linkPluginRepository;
	}

	private static Collection<IBlockPluginFacade> getBlockPlugins(
			ServletConfig servletConfig) {
		Set<IBlockPluginFacade> result = new LinkedHashSet<IBlockPluginFacade>(2);

		String contextParamStr = servletConfig.getServletContext()
				.getInitParameter("block-plugins");
		
		// TODO: TEMPORARY SOLUTION!!!!!!!
		contextParamStr = "org.wdbuilder.plugin.common.CommonBlockPluginFacade," +
		  		"org.wdbuilder.plugin.icon.IconBlockPluginFacade";
		
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
		
		// TODO: TEMPORARY SOLUTION!!!!!!!
		contextParamStr = "org.wdbuilder.plugin.defaultlink.DefaultLinkPluginFacade";
		
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
