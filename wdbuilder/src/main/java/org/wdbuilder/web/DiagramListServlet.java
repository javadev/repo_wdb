package org.wdbuilder.web;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.gui.IUIAction;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.Li;
import org.wdbuilder.jaxbhtml.element.Ul;
import org.wdbuilder.serialize.html.SectionHeader;
import org.wdbuilder.web.base.DiagramServiceServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/diagram-list")
public class DiagramListServlet extends DiagramServiceServlet {
	private static final long serialVersionUID = 1L;

	private static final String CLASS = "nav nav-list";

	private static final IUIAction[] ICONS_FULL = { new IUIActionClick() {

		@Override
		public String getTitle() {
			return "New Diagram";
		}

		@Override
		public String getResourceId() {
			return "icon-calendar";
		}

		@Override
		public String getOnClickHandler() {
			return "openCreateCanvasDialog()";
		}
	}, new IUIActionClick() {

		@Override
		public String getResourceId() {
			return "icon-refresh";
		}

		@Override
		public String getTitle() {
			return "Refresh Diagram List";
		}

		@Override
		public String getOnClickHandler() {
			return "refreshDiagramList()";
		}
	}, new IUIActionClick() {

		@Override
		public String getResourceId() {
			return "icon-off";
		}

		@Override
		public String getTitle() {
			return "Hide Diagram List";
		}

		@Override
		public String getOnClickHandler() {
			return "loadDiagramList(false, '')";
		}
	} };

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		final PrintWriter writer = input.getResponse().getWriter();
		final boolean full = BlockParameter.Full.getBoolean(input);
		final String activeKey = input.getState().getDiagram().getKey();

		final HtmlWriter htmlWriter = new HtmlWriter(writer);

		final SectionHeader sectionHeader = new SectionHeader("") {
			@Override
			public Iterable<IUIAction> getIcons() {
				IUIAction[] icons = full ? ICONS_FULL : getIconsForCondensed(activeKey);
				return Arrays.asList(icons);
			}
		};
		htmlWriter.write(sectionHeader);

		if (full) {
			htmlWriter.write(new DiagramList(activeKey));
		}
	}

	private static final IUIAction[] getIconsForCondensed(final String activeKey) {
		IUIAction[] result = new IUIAction[1];
		result[0] = new IUIActionClick() {

			@Override
			public String getTitle() {
				return "Show Diagram List";
			}

			@Override
			public String getResourceId() {
				return "icon-list-alt";
			}

			@Override
			public String getOnClickHandler() {
				return "loadDiagramList(true, '" + activeKey + "')";
			}
		};
		return result;

	}

	@Override
	protected String getContentType() {
		return CONTENT_TYPE_XML;
	}

	private class DiagramList extends Ul {

		private final String activeKey;

		public DiagramList(String activeKey) throws JAXBException {
			super(CLASS);
			this.activeKey = activeKey;
			final Collection<Diagram> list = serviceFacade.getDiagramService()
					.getDiagrams();
			for (final Diagram obj : list) {
				add(createItem(obj));
			}
		}

		private Li createItem(Diagram obj) {
			final String onClick = "loadCanvas('" + obj.getKey() + "')";
			A a = new A();
			a.setOnClick(onClick);
			a.setText(obj.getName());
			Li result = new Li();
			if (!StringUtils.isEmpty(activeKey)) {
				result.setClassName("active");
			}
			result.add(a);
			return result;
		}

	}

}
