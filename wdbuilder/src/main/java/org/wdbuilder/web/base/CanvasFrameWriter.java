package org.wdbuilder.web.base;

import static org.wdbuilder.service.DiagramService.RESIZE_AREA;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.gui.IUIAction;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.gui.IUIActionId;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.jaxbhtml.element.Area;
import org.wdbuilder.jaxbhtml.element.Map;
import org.wdbuilder.plugin.IPluginFacade;
import org.wdbuilder.serialize.html.SectionHeader;
import org.wdbuilder.serialize.html.IUIActionURL;
import org.wdbuilder.service.DiagramService;
import org.wdbuilder.service.validator.DiagramValidator;
import org.wdbuilder.utility.DiagramHelper;
import org.wdbuilder.utility.IPluginFacadeRepository;
import org.wdbuilder.utility.Utility;
import org.wdbuilder.web.ApplicationState;

public class CanvasFrameWriter {

	private static final String ID_IMAGE_MAP = "diagramImageMap";

	private final DiagramHelper diagramHelper;
	private IPluginFacadeRepository pluginFacadeRepository;

	public CanvasFrameWriter(final DiagramHelper diagramHelper,
			IPluginFacadeRepository pluginFacadeRepository) {
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

		SectionHeader header = new SectionHeader(diagram.getName()) {

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
				for (final IPluginFacade pluginFacade : pluginFacadeRepository
						.getBlockPlugins()) {
					result.add(pluginFacade.getUIActionCreate(diagramKey));
				}

				result.add(createEditBlockIcon(diagramKey));
				result.add(createExportIcon());
				result.add(createDeleteBlockIcon(diagramKey));
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
						return "export";
					}

					@Override
					public String getURL() {
						return prepareUrlForExport();
					}
				};
			}

			private IUIActionClick createDeleteBlockIcon(final String diagramKey) {
				return new IUIActionClick() {

					@Override
					public String getTitle() {
						return "Delete Diagram";
					}

					@Override
					public String getResourceId() {
						return "delete";
					}

					@Override
					public String getOnClickHandler() {
						return "deleteCanvas(" + diagramKey + ")";
					}
				};
			}

			private IUIActionClick createEditBlockIcon(final String diagramKey) {
				return new IUIActionClick() {

					@Override
					public String getTitle() {
						return "Edit Diagram";
					}

					@Override
					public String getResourceId() {
						return "edit_diagram";
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
		writer.write(header);

		writer.write(new FrameServlet.Image(diagram, null, "frameImage", input
				.getState().getSelectedBlockKey(), ID_IMAGE_MAP));

		final ImageMap map = state.isBlockMode() ? new ImageMapForBlock(state)
				: new ImageMapForLine(state);

		writer.write(map);
	}

	private abstract class ImageMap extends Map {

		protected final ApplicationState appState;

		public ImageMap(ApplicationState appState) {
			super();
			this.appState = appState;
		}

	}

	private class ImageMapForBlock extends ImageMap {

		public ImageMapForBlock(ApplicationState appState) {
			super(appState);

			setName(ID_IMAGE_MAP);
			setId(ID_IMAGE_MAP);
			final Collection<Block> blocks = diagramHelper.getDiagram()
					.getBlocks();
			if (null != blocks) {
				for (final Block block : blocks) {
					add(createBlockArea(block));
				}
			}
			add(createResizeArea());
		}

		private Area createBlockArea(Block entity) {
			final Dimension size = entity.getSize();

			final java.awt.Point topLeft = new java.awt.Point(entity
					.getLocation().getX() - size.getWidth() / 2, entity
					.getLocation().getY() - size.getHeight() / 2);

			String dragStartMethod = appState.getMode().getJsDragStart();

			String onMouseDown = getOnMouseDownFunctionCall(dragStartMethod,
					diagramHelper.getDiagram().getKey(), entity.getKey(),
					topLeft);

			final Area.Rect area = new Area.Rect(topLeft, size.toAWT());
			area.setOnMouseDown(onMouseDown);
			area.setTitle(entity.getName());
			area.setId("area-" + entity.getKey());
			return area;
		}

		private Area createResizeArea() {
			final Diagram diagram = diagramHelper.getDiagram();

			final Point offset = new Point(diagram.getSize().getWidth()
					- RESIZE_AREA.getWidth(), diagram.getSize().getHeight()
					- RESIZE_AREA.getHeight());
			final String selectedBlockKey = appState.getSelectedBlockKey();
			final String onMouseDownCall = getOnMouseDownFunctionCall(
					// "DiagramResize.start", diagram.getKey(),
					"WDB.DiagramResize.mouseDown", diagram.getKey(),

					selectedBlockKey, DiagramValidator.MIN_SIZE.getWidth(),
					DiagramValidator.MIN_SIZE.getHeight());

			Area.Rect area = new Area.Rect(offset.toAWT(), RESIZE_AREA.toAWT());
			area.setOnMouseDown(onMouseDownCall);
			area.setTitle("Resize Diagram");
			return area;
		}

	}

	private class ImageMapForLine extends ImageMap {

		public ImageMapForLine(ApplicationState appState) {
			super(appState);
			setId(ID_IMAGE_MAP);
			setName(ID_IMAGE_MAP);
			final Collection<Block> blocks = diagramHelper.getDiagram()
					.getBlocks();
			if (null != blocks) {
				for (final Block entity : blocks) {
					createLinkSocketAreasForBlock(entity);
				}
			}
			final Collection<Link> links = diagramHelper.getDiagram()
					.getLinks();
			if (null != links) {
				for (final Link link : links) {
					add(createLinkPivotArea(link));
				}
			}
		}

		private Area.Rect createLinkPivotArea(Link link) {
			final Point pivot = link.getPivot();
			final Point topLeft = new Point(pivot.getX()
					- DiagramService.LINE_AREA.getWidth() / 2, pivot.getY()
					- DiagramService.LINE_AREA.getHeight() / 2);

			final Area.Rect area = new Area.Rect(topLeft.toAWT(),
					DiagramService.LINE_AREA.toAWT());
			area.setOnMouseDown(createOnMouseDownHandler(link));
			area.setTitle(link.getKey());
			return area;
		}

		private String createOnMouseDownHandler(Link link) {
			boolean isHorizontal = link.getBeginSocket().isHorizontal();

			Point beginPoint = diagramHelper.getOffset(link.getBeginSocket(),
					link.getBeginKey());
			Point endPoint = diagramHelper.getOffset(link.getEndSocket(),
					link.getEndKey());

			final String result = getOnMouseDownFunctionCall(
					"WDB.LinkArrange.mouseDown", diagramHelper.getDiagram()
							.getKey(), link.getKey(), beginPoint, endPoint,
					isHorizontal);
			return result;
		}

		private void createLinkSocketAreasForBlock(Block block) {
			final Collection<LinkSocket> sockets = LinkSocket.getAvailable(
					diagramHelper.getUsedLinkSockets(block), block);

			// Add possible line start and end points
			for (final LinkSocket socket : sockets) {
				Rectangle rect = socket.getArea(block);

				String dragStartMethod = appState.getMode().getJsDragStart();

				String id = block.getKey() + ":"
						+ String.valueOf(socket.getDirection()) + ":"
						+ socket.getIndex();

				String onMouseDown = getOnMouseDownFunctionCall(
						dragStartMethod, diagramHelper.getDiagram().getKey(),
						id, block.getLocation());

				Area.Rect area = new Area.Rect(rect.getLocation(),
						rect.getSize());
				area.setOnMouseDown(onMouseDown);
				area.setTitle(block.getName());
				area.setId(id);
				add(area);
			}
		}
	}

	private static String getOnMouseDownFunctionCall(String functionName,
			Object... args) {
		StringBuilder sb = new StringBuilder(64);
		sb.append(functionName);
		sb.append("(event");
		if (null != args) {
			for (final Object arg : args) {
				sb.append(',');
				if (null == arg) {
					sb.append("''");
				} else {
					if (String.class.isInstance(arg)) {
						sb.append('\'');
						sb.append(arg);
						sb.append('\'');
					} else if (java.awt.Point.class.isInstance(arg)) {
						java.awt.Point p = java.awt.Point.class.cast(arg);
						sb.append(p.getX());
						sb.append(',');
						sb.append(p.getY());
					} else if (Point.class.isInstance(arg)) {
						Point p = Point.class.cast(arg);
						sb.append(p.getX());
						sb.append(',');
						sb.append(p.getY());
					} else {
						sb.append(arg);
					}
				}
			}
		}
		sb.append(")");
		return sb.toString();
	}

	protected String prepareUrlForExport() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("exported/");
		sb.append(prepareNameForURL(diagramHelper.getDiagram().getName()));
		sb.append(".zip?");
		sb.append(Utility.getURLPartToAvoidCaching());
		DiagramServiceServlet.addParameter(sb, BlockParameter.DiagramKey
				.getName(), diagramHelper.getDiagram().getKey());
		return sb.toString();
	}

	private static String prepareNameForURL(String name) {
		return name.replace(' ', '_').replace('.', '_').replace(':', '_')
				.replace('*', '_').replace('?', '_');
	}
}

interface IUIActionClickUI extends IUIActionClick, IUIActionId {

}
