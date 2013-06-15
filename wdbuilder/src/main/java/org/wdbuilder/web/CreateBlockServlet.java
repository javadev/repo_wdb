package org.wdbuilder.web;

import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;

import org.apache.commons.lang.StringUtils;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.gui.UINewBlockFormFactory;
import static org.wdbuilder.input.InputParameter.DiagramKey;
import static org.wdbuilder.input.InputParameter.BlockClass;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.web.base.DiagramHelperFormServlet;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/create-block")
public class CreateBlockServlet extends DiagramHelperFormServlet {

	@Override
	protected void do4DiagramHelperForm(ServletInput input) throws Exception {
		final PrintWriter writer = input.getResponse().getWriter();

		final String str = DiagramKey.getString(input);
		final String diagramId = "'" + str + "'";

		final String blockClassStr = BlockClass.getString(input);
		if (StringUtils.isEmpty(blockClassStr)) {
			return;
		}

		IBlockPluginFacade pluginFacade = serviceFacade
				.getBlockPluginRepository().getFacade(
						Class.forName(blockClassStr));
		if (null == pluginFacade) {
			return;
		}
		final UINewBlockFormFactory formFactory = pluginFacade
				.getCreateFormFactory(str);

		final String submitFunctionCall = formFactory.getSubmitCall();

		String closeHandler = "loadCanvas(" + diagramId + ")";

		final TwoColumnForm form = formFactory.getForm().addFooter(
				submitFunctionCall, closeHandler);

		new HtmlWriter(writer).write(form);
	}

}
