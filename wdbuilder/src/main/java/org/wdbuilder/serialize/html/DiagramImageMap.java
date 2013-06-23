package org.wdbuilder.serialize.html;

import java.util.Collection;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.jaxbhtml.element.Map;
import org.wdbuilder.web.ApplicationState;

abstract class DiagramImageMap extends Map {

	private static final String ID_IMAGE_MAP = "diagramImageMap";

	protected final Diagram diagram;

	protected abstract void addForBlock(Block block);

	protected abstract void addForLink(Link link);

	protected DiagramImageMap(Diagram diagram) {
		super();
		this.diagram = diagram;
		setName(ID_IMAGE_MAP);
		setId(ID_IMAGE_MAP);

		final Collection<Block> blocks = diagram.getBlocks();
		if (null != blocks) {
			for (final Block block : blocks) {
				if (null != block) {
					addForBlock(block);
				}
			}
		}
		final Collection<Link> links = diagram.getLinks();
		if (null != links) {
			for (final Link link : links) {
				if (null != link) {
					addForLink(link);
				}
			}
		}

	}

	static DiagramImageMap create(ApplicationState appState) {
		final Diagram diagram = appState.getDiagram();
		switch (appState.getMode()) {
		case BLOCK:
			return new BlockDiagramImageMap(diagram);
		case LINE:
			return new LineDiagramImageMap(diagram);
		}
		return null;
	}

	protected final static String getOnMouseDownFunctionCall(
			String functionName, Object... args) {
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
}
