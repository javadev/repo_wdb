package org.wdbuilder.serialize.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.gui.IUIAction;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.gui.IUIActionId;
import org.wdbuilder.input.InputParameter;
import org.wdbuilder.jaxbhtml.HtmlElement;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.Img;
import org.wdbuilder.jaxbhtml.element.Table;
import org.wdbuilder.jaxbhtml.element.Td;
import org.wdbuilder.jaxbhtml.element.Tr;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.plugin.IRenderContext;
import org.wdbuilder.service.DiagramHelper;
import org.wdbuilder.service.IPluginFacadeRepository;
import org.wdbuilder.web.ApplicationState;
import org.wdbuilder.web.base.DiagramServiceServlet;
import org.wdbuilder.web.base.FrameServlet;
import org.wdbuilder.web.base.ServletInput;

import static org.wdbuilder.web.base.FrameServlet.getURLPartToAvoidCaching;

public class DiagramImage {

	private static final String ID_IMAGE_MAP = "diagramImageMap";

	private final DiagramHelper diagramHelper;
	private IPluginFacadeRepository<Block, IBlockPluginFacade, IRenderContext> pluginFacadeRepository;

	public DiagramImage(
			final DiagramHelper diagramHelper,
			IPluginFacadeRepository<Block, IBlockPluginFacade, IRenderContext> pluginFacadeRepository) {
		this.diagramHelper = diagramHelper;
		this.pluginFacadeRepository = pluginFacadeRepository;
	}

	public void printCanvasFrame(ServletInput input) throws JAXBException,
			IOException {
		final ApplicationState state = input.getState();
		final HtmlWriter writer = new HtmlWriter(input.getResponse()
				.getWriter());

		final Diagram diagram = diagramHelper.getDiagram();

		final String diagramKey = "'" + diagram.getKey() + "'";

		Img img = new FrameServlet.Image(diagram, null, "frameImage",
				ID_IMAGE_MAP);
		img.setOnMouseOver("hideCaret()");

		Tr tr = new Tr();

		Td td = new Td();
		td.add(img);

		final DiagramImageMap map = DiagramImageMap.create(state);

		td.add(map);
		tr.add(td);

		SectionHeader header = createToolbar(state, diagram, diagramKey);
		Td headerTd = new Td();
		headerTd.setStyle("vertical-align:top");
		headerTd.add(header);
		tr.add(headerTd);

		Table table = new Table();
		table.add(tr);

		writer.write(table);
	}

	private SectionHeader createToolbar(final ApplicationState state,
			final Diagram diagram, final String diagramKey)
			throws JAXBException {
		return new SectionHeader() {

			@Override
			public Iterable<IUIAction> getIcons() {
				final List<IUIAction> result = new ArrayList<IUIAction>(4);
				final ApplicationState.Mode mode = state.getMode();

				// Check for number of blocks:
				if (!diagram.getBlocks().isEmpty()) {
					result.add(createSwitchModeIcon(diagramKey, mode));
				}
				if (!ApplicationState.Mode.BLOCK.equals(mode)) {
					return result;
				}
				for (final IBlockPluginFacade pluginFacade : pluginFacadeRepository
						.getPlugins()) {
					result.add(pluginFacade.getUIActionCreate(diagramKey));
				}

				result.add(createEditDiagramIcon(diagramKey));
				result.add(createExportIcon());
				result.add(createDeleteDiagramIcon(diagramKey));
				return result;
			}

			private IUIActionURL createExportIcon() {
				return new IUIActionURL() {

					@Override
					public String getTitle() {
						return "Export Diagram";
					}

					@Override
					public String getResourceId() {
						return "icon-download";
					}

					@Override
					public String getURL() {
						return prepareUrlForExport();
					}

					@Override
					public void setActionToHTMLElement(HtmlElement element) {
						if (!A.class.isInstance(element)) {
							return;
						}
						A a = A.class.cast(element);
						a.setHref(getURL());
					}

					@Override
					public String getClassName() {
						return "";
					}
				};
			}

			private IUIActionClick createDeleteDiagramIcon(
					final String diagramKey) {
				return new IUIActionClick() {

					@Override
					public String getTitle() {
						return "Delete Diagram";
					}

					@Override
					public String getResourceId() {
						return "icon-remove";
					}

					@Override
					public String getOnClickHandler() {
						return "deleteCanvas(" + diagramKey + ")";
					}

					@Override
					public String getClassName() {
						return "btn-danger";
					}
				};
			}

			private IUIActionClick createEditDiagramIcon(final String diagramKey) {
				return new IUIActionClick() {

					@Override
					public String getTitle() {
						return "Edit Diagram";
					}

					@Override
					public String getResourceId() {
						return "icon-edit";
					}

					@Override
					public String getOnClickHandler() {
						return "openEditDiagramDialog(" + diagramKey + ")";
					}
				};
			}

			private IUIActionClick createSwitchModeIcon(
					final String diagramKey, final ApplicationState.Mode mode) {

				return new IUIActionClickUI() {

					@Override
					public String getTitle() {
						return mode.getTitle();
					}

					@Override
					public String getResourceId() {
						return mode.getResourceId();
					}

					@Override
					public String getId() {
						return "switchModeImg";
					}

					@Override
					public String getOnClickHandler() {
						return "switchMode(" + diagramKey + ")";
					}
				};
			}
		};
	}

	protected String prepareUrlForExport() {
		StringBuilder sb = new StringBuilder(256)
				.append("exported/")
				.append(prepareNameForURL(diagramHelper.getDiagram().getName()))
				.append(".zip?").append(getURLPartToAvoidCaching());
		DiagramServiceServlet.addParameter(sb, InputParameter.DiagramKey
				.getName(), diagramHelper.getDiagram().getKey());
		return sb.toString();
	}

	private static String prepareNameForURL(String name) {
		return name.replace(' ', '_').replace('.', '_').replace(':', '_')
				.replace('*', '_').replace('?', '_');
	}
}

abstract class IUIActionClickUI extends IUIActionClick implements IUIActionId {

}
