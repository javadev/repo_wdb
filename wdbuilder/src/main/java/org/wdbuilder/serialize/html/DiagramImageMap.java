package org.wdbuilder.serialize.html;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.jaxbhtml.element.Map;
import org.wdbuilder.utility.DiagramHelper;
import org.wdbuilder.web.ApplicationState;

abstract class DiagramImageMap extends Map {

  private static final String ID_IMAGE_MAP = "diagramImageMap";

	protected final DiagramHelper diagramHelper;

	protected DiagramImageMap(Diagram diagram) {
		super();
		this.diagramHelper = new DiagramHelper(diagram);
		setName(ID_IMAGE_MAP);
		setId(ID_IMAGE_MAP);
	}

	static DiagramImageMap create(ApplicationState appState) {
		Diagram diagram = appState.getDiagram();
		switch (appState.getMode()) {
		case BLOCK:
			return new BlockDiagramImageMap(diagram);
		case LINE:
			return new LineDiagramImageMap(diagram, appState.getMode()
					.getJsDragStart());
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
