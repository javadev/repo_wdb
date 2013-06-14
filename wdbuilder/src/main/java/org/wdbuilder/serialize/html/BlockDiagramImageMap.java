package org.wdbuilder.serialize.html;

import static org.wdbuilder.service.DiagramService.RESIZE_AREA;

import java.util.ArrayList;
import java.util.Collection;
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
		super( diagram );
		
		final Collection<Block> blocks = diagramHelper.getDiagram()
				.getBlocks();
		if (null != blocks) {
			for (final Block block : blocks) {
				add(createBlockArea(block));
			}
		}

		final Collection<Link> links = diagramHelper.getDiagram()
				.getLinks();
		if (null != links) {
			for (final Link link : links) {
				Area area = createLinkArea(link);
				if (null != area) {
					add(area);
				}
			}
		}

		add(createResizeArea());		
	}
	
	private Area createLinkArea(Link link) {
		final LinkSocket socket0 = link.getSockets().get(0);
		if (null == socket0) {
			return null;
		}
		final LinkSocket socket1 = link.getSockets().get(1);
		if (null == socket1) {
			return null;
		}

		final Block block0 = diagramHelper.findBlockByKey(socket0
				.getBlockKey());
		if (null == block0) {
			return null;
		}
		final Block block1 = diagramHelper.findBlockByKey(socket1
				.getBlockKey());
		if (null == block1) {
			return null;
		}
		Point[] basePoints = DivAnalog.getLine(link, block0, block1);

		final int offset = 4;
		List<java.awt.Point> points = new ArrayList<java.awt.Point>(
				basePoints.length * 2);
		for (Point point : basePoints) {
			points.add(new java.awt.Point(point.getX() + offset, point
					.getY() + offset));
		}
		for (Point point : basePoints) {
			points.add(new java.awt.Point(point.getX() - offset, point
					.getY() - offset));
		}
		Area area = new Area.Poly(points);
		area.setTitle(link.getName());
		area.setId("link-" + link.getKey());
		String diagramKey = diagramHelper.getDiagram().getKey();
		String linkKey = link.getKey();
		area.setOnMouseOver(getJsOnMouseOverLink(diagramKey, linkKey));
		return area;
	}

	private Area createBlockArea(Block entity) {
		final Dimension size = entity.getSize();

		final java.awt.Point topLeft = new java.awt.Point(entity
				.getLocation().getX() - size.getWidth() / 2, entity
				.getLocation().getY() - size.getHeight() / 2);

		String onMouseOver = getJsOnMouseOverBlock(topLeft, size,
				diagramHelper.getDiagram().getKey(), entity.getKey());

		final Area.Rect area = new Area.Rect(topLeft, size.toAWT());
		area.setOnMouseOver(onMouseOver);
		area.setTitle(entity.getName());
		area.setId("area-" + entity.getKey());
		return area;
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
		result.append("setCaret('");
		result.append(diagramKey);
		result.append("','");
		result.append(blockKey);
		result.append("',");
		result.append(topLeft.x);
		result.append(",");
		result.append(topLeft.y);
		result.append(",");
		result.append(size.getWidth());
		result.append(",");
		result.append(size.getHeight());
		result.append(")");

		return result.toString();
	}

	private Area createResizeArea() {
		final Diagram diagram = diagramHelper.getDiagram();
		
		int minWidth = new DiagramValidator(diagram).getMinWidth();
		int minHeight = new DiagramValidator(diagram).getMinHeight();

		final Point offset = new Point(diagram.getSize().getWidth()
				- RESIZE_AREA.getWidth(), diagram.getSize().getHeight()
				- RESIZE_AREA.getHeight());
		final String onMouseDownCall = getOnMouseDownFunctionCall(
				// "DiagramResize.start", diagram.getKey(),
				"WDB.DiagramResize.mouseDown", diagram.getKey(), "(none)",
				minWidth, minHeight );

		Area.Rect area = new Area.Rect(offset.toAWT(), RESIZE_AREA.toAWT());
		area.setOnMouseDown(onMouseDownCall);
		area.setTitle("Resize Diagram");
		return area;
	}
	

}
