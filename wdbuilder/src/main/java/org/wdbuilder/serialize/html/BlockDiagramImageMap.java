package org.wdbuilder.serialize.html;

import static org.wdbuilder.service.DiagramService.RESIZE_AREA;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.jaxbhtml.element.Area;
import org.wdbuilder.service.validator.DiagramValidator;
import org.wdbuilder.view.DivAnalog;

public class BlockDiagramImageMap extends DiagramImageMap {

	protected BlockDiagramImageMap(Diagram diagram) {
		super(diagram);
		add(createResizeArea());
	}

	@Override
	protected void addForLink(Link link) {
		final Area result = new Area.Poly(
				getLinkAreaPoints(getLinkBasePoints(link)));
		result.setTitle(link.getName());
		result.setId("link-" + link.getKey());
		String diagramKey = diagram.getKey();
		String linkKey = link.getKey();
		result.setOnMouseOver(getJsOnMouseOverLink(diagramKey, linkKey));

		add(result);
	}

	private static List<java.awt.Point> getLinkAreaPoints(Point[] basePoints) {
		final int offset = 4;
		List<java.awt.Point> result = new ArrayList<java.awt.Point>(
				basePoints.length * 2);
		for (Point point : basePoints) {
			result.add(new java.awt.Point(point.getX() + offset, point.getY()
					+ offset));
		}
		for (Point point : basePoints) {
			result.add(new java.awt.Point(point.getX() - offset, point.getY()
					- offset));
		}
		return result;
	}

	private Point[] getLinkBasePoints(Link link) {
		final LinkSocket socket0 = link.getSockets().get(0);
		final LinkSocket socket1 = link.getSockets().get(1);

		final Block block0 = diagram.getBlock(socket0.getBlockKey());
		final Block block1 = diagram.getBlock(socket1.getBlockKey());

		Point[] basePoints = DivAnalog.getLine(link, block0, block1);
		return basePoints;
	}

	@Override
	protected void addForBlock(Block entity) {
		final Dimension size = entity.getSize();

		final java.awt.Point topLeft = new java.awt.Point(entity.getLocation()
				.getX() - size.getWidth() / 2, entity.getLocation().getY()
				- size.getHeight() / 2);

		String onMouseOver = getJsOnMouseOverBlock(topLeft, size,
				diagram.getKey(), entity.getKey());

		final Area.Rect area = new Area.Rect(topLeft, size.toAWT());
		area.setOnMouseOver(onMouseOver);
		area.setTitle(entity.getName());
		area.setId("area-" + entity.getKey());

		add(area);
	}

	private String getJsOnMouseOverLink(String diagramKey, String linkKey) {
		StringBuilder result = new StringBuilder(128);
		result.append("setCaretLink( event, '");
		result.append(diagramKey);
		result.append("','");
		result.append(linkKey);
		result.append("')");
		return result.toString();
	}

	private String getJsOnMouseOverBlock(java.awt.Point topLeft,
			Dimension size, String diagramKey, String blockKey) {
		StringBuilder result = new StringBuilder(128);
		result.append("setCaret('").append(diagramKey).append("','")
				.append(blockKey).append("',").append(topLeft.x).append(",")
				.append(topLeft.y).append(",").append(size.getWidth())
				.append(",").append(size.getHeight()).append(")");

		return result.toString();
	}

	private Area createResizeArea() {

		int minWidth = new DiagramValidator(diagram).getMinWidth();
		int minHeight = new DiagramValidator(diagram).getMinHeight();

		final Point offset = new Point(diagram.getSize().getWidth()
				- RESIZE_AREA.getWidth(), diagram.getSize().getHeight()
				- RESIZE_AREA.getHeight());
		final String onMouseDownCall = getOnMouseDownFunctionCall(
				"WDB.DiagramResize.mouseDown", diagram.getKey(), "(none)",
				minWidth, minHeight);

		Area.Rect area = new Area.Rect(offset.toAWT(), RESIZE_AREA.toAWT());
		area.setOnMouseDown(onMouseDownCall);
		area.setTitle("Resize Diagram");
		return area;
	}

}
