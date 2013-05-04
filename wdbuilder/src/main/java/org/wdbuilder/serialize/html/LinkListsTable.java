package org.wdbuilder.serialize.html;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.Div;
import org.wdbuilder.jaxbhtml.element.Li;
import org.wdbuilder.jaxbhtml.element.Table;
import org.wdbuilder.jaxbhtml.element.Td;
import org.wdbuilder.jaxbhtml.element.Tr;
import org.wdbuilder.jaxbhtml.element.Ul;
import org.wdbuilder.utility.DiagramHelper;


public class LinkListsTable extends Table {
	
	private static final Map<Boolean, String> LINK_TITLES = new LinkedHashMap<Boolean, String>(2);
	
	static {
		LINK_TITLES.put(true, "Link begins (to delete):");
		LINK_TITLES.put(false, "Link ends (to delete):");
	}	

	private final Block block;
	private final DiagramHelper diagramHelper;

	public LinkListsTable(Diagram diagram, Block block) {
		this.block = block;
		this.diagramHelper = new DiagramHelper(diagram);

		add(createColumnHeaders());
		add(createColumns());
	}

	private Tr createColumns() {
		Tr tr = new Tr();
		for (final Boolean begin : LINK_TITLES.keySet()) {
			tr.add(createList(begin));
		}
		return tr;
	}

	private Td createList(final Boolean begin) {
		Td td = new Td(TwoColumnForm.CLASS_LINKBUTTON);
		final Ul list = new Ul();
		list.setClassName(TwoColumnForm.CLASS_LINKBUTTON);
		td.add(list);
		Map<String, Block> blocks = diagramHelper.getBlocksConnectedTo(this.block, begin);
		for (String linkKey : blocks.keySet()) {
			Block connectedBlock = blocks.get(linkKey);

			String name = connectedBlock.getName();

			Div nameDiv = new Div();
			nameDiv.setText(name);
			String onClick = "deleteLink('" + diagramHelper.getDiagram().getKey() + "', '" + linkKey + "')";
			A a = new A();
			a.add(nameDiv);
			a.setOnClick(onClick);

			Li item = new Li();
			item.add(a);
			list.add(item);
		}
		return td;
	}

	private Tr createColumnHeaders() {
		Tr tr = new Tr();
		for (final Entry<Boolean, String> entry : LINK_TITLES.entrySet()) {
			Td td = new Td();
			tr.add(td);
			td.setText(entry.getValue());
			td.setClassName(TwoColumnForm.CLASS);
		}
		return tr;
	}
}
