package org.wdbuilder.web;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBException;

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

	private static final String CLASS = "menu";

	private static final IUIAction[] ICONS_FULL = { new IUIActionClick() {

		@Override
		public String getTitle() {
			return "New Diagram";
		}

		@Override
		public String getResourceId() {
			return "new_diagram";
		}

		@Override
		public String getOnClickHandler() {
			return "openCreateCanvasDialog()";
		}
	}, new IUIActionClick() {

		@Override
		public String getResourceId() {
			return "refresh";
		}

		@Override
		public String getTitle() {
			// TODO Auto-generated method stub
			return "Refresh Diagram List";
		}

		@Override
		public String getOnClickHandler() {
			return "refreshDiagramList()";
		}
	}, new IUIActionClick() {

		@Override
		public String getResourceId() {
			return "left";
		}

		@Override
		public String getTitle() {
			return "Hide Diagram List";
		}

		@Override
		public String getOnClickHandler() {
			return "loadDiagramList(false)";
		}
	} };
	private static final IUIAction[] ICONS_CONDENSED = { new IUIActionClick() {

		@Override
		public String getTitle() {
			return "Show Diagram List";
		}

		@Override
		public String getResourceId() {
			return "right";
		}

		@Override
		public String getOnClickHandler() {
			return "loadDiagramList(true)";
		}
	} };

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		final PrintWriter writer = input.getResponse().getWriter();
		final boolean full = BlockParameter.Full.getBoolean(input);

		final HtmlWriter htmlWriter = new HtmlWriter(writer);

		final SectionHeader sectionHeader = new SectionHeader("") {
			@Override
			public Iterable<IUIAction> getIcons() {
				IUIAction[] icons = full ? ICONS_FULL : ICONS_CONDENSED;
				return Arrays.asList(icons);
			}
		};
		htmlWriter.write(sectionHeader);

		if (full) {
			htmlWriter.write(new DiagramList());
		}
	}

	@Override
	protected String getContentType() {
		return CONTENT_TYPE_XML;
	}

	private class DiagramList extends Ul {

		public DiagramList() throws JAXBException {
			super(CLASS);
			final Collection<Diagram> list = serviceFacade.getDiagramService()
					.getDiagrams();
			for (final Diagram obj : list) {
				add(createItem(obj));
			}
		}

		private Li createItem(Diagram obj) {
			final String onClick = "loadCanvas('" + obj.getKey() + "',null)";
			A a = new A();
			a.setOnClick(onClick);
			a.setText(obj.getName());
			Li result = new Li(CLASS);
			result.add(a);
			return result;
		}

	}

}
