package org.wdbuilder.serialize.html;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.Li;
import org.wdbuilder.jaxbhtml.element.Ol;
import org.wdbuilder.jaxbhtml.element.Span;
import org.wdbuilder.utility.DiagramHelper;

import static org.wdbuilder.gui.TwoColumnForm.CLASS_LINKBUTTON;

public class ConnectedLinkList extends Ol {

	private static final Map<Boolean, String> LINK_TITLES = new LinkedHashMap<Boolean, String>(
			2);

	static {
		LINK_TITLES.put(true, "Link begins (to delete):");
		LINK_TITLES.put(false, "Link ends (to delete):");
	}

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
		result.add( createNameDiv(link.getName()));
		result.add(createEditButton(key));
		result.add(createDeleteButton(key));

		return result;
	}
	
	private Span createNameDiv(String name) {
		Span result = new Span();
		result.setText( name );
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
