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
import org.wdbuilder.jaxbhtml.element.Img;
import org.wdbuilder.jaxbhtml.element.Map;
import org.wdbuilder.jaxbhtml.element.Table;
import org.wdbuilder.jaxbhtml.element.Td;
import org.wdbuilder.jaxbhtml.element.Tr;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.serialize.html.SectionHeader;
import org.wdbuilder.serialize.html.IUIActionURL;
import org.wdbuilder.service.DiagramService;
import org.wdbuilder.service.IPluginFacadeRepository;
import org.wdbuilder.service.validator.DiagramValidator;
import org.wdbuilder.utility.DiagramHelper;
import org.wdbuilder.utility.Utility;
import org.wdbuilder.web.ApplicationState;

public class CanvasFrameWriter {

	private static final String ID_IMAGE_MAP = "diagramImageMap";

	private final DiagramHelper diagramHelper;
	private IPluginFacadeRepository<Block, IBlockPluginFacade> pluginFacadeRepository;

	public CanvasFrameWriter(
			final DiagramHelper diagramHelper,
			IPluginFacadeRepository<Block, IBlockPluginFacade> pluginFacadeRepository) {
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
		
		Img img = new FrameServlet.Image(diagram, null, "frameImage", ID_IMAGE_MAP);
		img.setOnMouseOver( "hideCaret()" );
		
		Tr tr = new Tr();

		Td td = new Td();
		td.add( img );

		final ImageMap map = state.isBlockMode() ? new ImageMapForBlock(state)
				: new ImageMapForLine(state);

		td.add( map );
		tr.add(td);
		
		SectionHeader header = createToolbar(state, diagram, diagramKey);
		Td headerTd = new Td();
		headerTd.setStyle("vertical-align:top");
		headerTd.add(header);
		tr.add(headerTd);
		
		Table table = new Table();
		table.add( tr );
		
		writer.write( table );		
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
						return "icon-briefcase";
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
						return "icon-remove";
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

			/*
			String dragStartMethod = appState.getMode().getJsDragStart();

			String onMouseDown = getOnMouseDownFunctionCall(dragStartMethod,
					diagramHelper.getDiagram().getKey(), entity.getKey(),
					topLeft);
			*/
			String onMouseOver = getJsOnMouseOver( topLeft, size,
					diagramHelper.getDiagram().getKey(), entity.getKey() );
			

			final Area.Rect area = new Area.Rect(topLeft, size.toAWT());
			// area.setOnMouseDown(onMouseDown);
			area.setOnMouseOver( onMouseOver );
			area.setTitle(entity.getName());
			area.setId("area-" + entity.getKey());
			return area;
		}

		private String getJsOnMouseOver(java.awt.Point topLeft, Dimension size,
				String diagramKey, String blockKey) {
			StringBuilder result = new StringBuilder( 128 );
			result.append( "setCaret('");
			result.append( diagramKey );
			result.append( "','" );
			result.append( blockKey );
			result.append( "'," );
			result.append( topLeft.x );
			result.append( "," );
			result.append( topLeft.y );
			result.append( "," );
			result.append( size.getWidth() );
			result.append( "," );
			result.append( size.getHeight() );
			result.append( ")");
			
			return result.toString();
		}

		private Area createResizeArea() {
			final Diagram diagram = diagramHelper.getDiagram();

			final Point offset = new Point(diagram.getSize().getWidth()
					- RESIZE_AREA.getWidth(), diagram.getSize().getHeight()
					- RESIZE_AREA.getHeight());
			final String onMouseDownCall = getOnMouseDownFunctionCall(
					// "DiagramResize.start", diagram.getKey(),
					"WDB.DiagramResize.mouseDown", diagram.getKey(),
					"(none)", DiagramValidator.MIN_SIZE.getWidth(),
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
			final LinkSocket beginSocket = link.getSockets().get(0);
			final LinkSocket endSocket = link.getSockets().get(1);

			boolean isHorizontal = beginSocket.isHorizontal();

			Point beginPoint = diagramHelper.getOffset(beginSocket);
			Point endPoint = diagramHelper.getOffset(endSocket);

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
