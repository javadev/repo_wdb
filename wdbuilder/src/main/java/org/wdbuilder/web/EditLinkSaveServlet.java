package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Link;
import static org.wdbuilder.input.InputParameter.DiagramKey;
import static org.wdbuilder.input.InputParameter.LinkKey;
import org.wdbuilder.plugin.ILinkPluginFacade;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/edit-link-save")
public class EditLinkSaveServlet extends DiagramServlet {

	@Override
	protected void do4Frame(ServletInput input) throws Exception {

		final String diagramKey = DiagramKey.getString(input);
		final String linkKey = LinkKey.getString(input);
		final Link persistedLink = diagramHelper.getDiagram().getLink(linkKey);
		if (null == persistedLink) {
			return;
		}

		ILinkPluginFacade pluginFacade = serviceFacade
				.getLinkPluginRepository().getFacade(persistedLink.getClass());
		Link link = pluginFacade.create(input);

		serviceFacade.getDiagramService().getLinkService(diagramKey)
				.update(linkKey, link);
		printCanvasFrame(input);

	}

}
