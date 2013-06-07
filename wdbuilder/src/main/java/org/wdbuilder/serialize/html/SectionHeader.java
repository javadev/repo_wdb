package org.wdbuilder.serialize.html;

import java.util.Collections;

import javax.xml.bind.JAXBException;

import org.wdbuilder.gui.IUIAction;
import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.Div;
import org.wdbuilder.jaxbhtml.element.I;

public class SectionHeader extends Div {

	public SectionHeader() throws JAXBException {
		setClassName("btn-group btn-group-vertical");
		
		for (final IUIAction icon : getIcons()) {
			add(createButton(icon));
		}
	}

	// May be overridden
	public Iterable<IUIAction> getIcons() {
		return Collections.emptyList();
	}

	private static A createButton(IUIAction uiAction) {
		I i = new I();
		i.setClassName( uiAction.getResourceId() );
		i.setText("");
		
		A result = new A();
		result.setDataToggle("tooltip");
		result.setClassName("btn btn-mini");
		result.setTitle( uiAction.getTitle() );
		result.setDataOriginalTitle(uiAction.getTitle());
		result.add(i);
		uiAction.setActionToHTMLElement(result);
		return result;
	}
}
