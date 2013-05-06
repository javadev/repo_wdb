package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Link;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.plugin.ILinkPluginFacade;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/edit-link-save")
public class EditLinkSaveServlet extends DiagramServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4Frame(ServletInput input) throws Exception {

		final String diagramKey = BlockParameter.DiagramKey.getString(input);
		final String linkKey = BlockParameter.LinkKey.getString(input);
		final Link persistedLink = diagramHelper.findLinkByKey(linkKey);
		if (null == persistedLink) {
			return;
		}

		ILinkPluginFacade pluginFacade = serviceFacade
				.getLinkPluginRepository()
				.getFacade(persistedLink.getClass());
		Link link = pluginFacade.create(input);

		serviceFacade.getDiagramService().updateLink(diagramKey, linkKey,
				link);
		printCanvasFrame(input, null);

	}

}
