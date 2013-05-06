package org.wdbuilder.serialize.html;

import java.util.Collection;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.Li;
import org.wdbuilder.jaxbhtml.element.Ol;
import org.wdbuilder.jaxbhtml.element.Span;
import org.wdbuilder.utility.DiagramHelper;

import static org.wdbuilder.gui.TwoColumnForm.CLASS_LINKBUTTON;
import static org.apache.commons.lang.StringUtils.isEmpty;

public class ConnectedLinkList extends Ol {

	private final DiagramHelper diagramHelper;

	public ConnectedLinkList(Diagram diagram, Block block) {
		this.diagramHelper = new DiagramHelper(diagram);

		setClassName(CLASS_LINKBUTTON);

		Collection<Link> links = this.diagramHelper.getConnectedLinks(block);
		for (final Link link : links) {
			add(createListItem(link));
		}
	}

	private Li createListItem(Link link) {
		String key = link.getKey();
		Li result = new Li();
		result.add( createNameDiv(link));
		result.add(createEditButton(key));
		result.add(createDeleteButton(key));

		return result;
	}
	
	private Span createNameDiv(Link link) {
		Span result = new Span();
		String text = isEmpty( link.getName() ) ? "(" + link.getKey() + ")" : link.getName();
		result.setText( text );
		return result;
	}	

	private A createEditButton(String key) {
		Span span = new Span();
		span.setText("Edit");
		String onClick = "openEditLinkDialog('"
				+ diagramHelper.getDiagram().getKey() + "', '" + key + "')";
		A result = new A(CLASS_LINKBUTTON);
		result.add(span);
		result.setOnClick(onClick);
		return result;
	}

	private A createDeleteButton(String key) {
		Span span = new Span();
		span.setText("Delete");
		String onClick = "deleteLink('" + diagramHelper.getDiagram().getKey()
				+ "', '" + key + "')";
		A result = new A(CLASS_LINKBUTTON);
		result.add(span);
		result.setOnClick(onClick);
		return result;
	}
}
