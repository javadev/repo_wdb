package org.wdbuilder.serialize.html;

import java.util.Collections;

import javax.xml.bind.JAXBException;

import org.wdbuilder.gui.IUIAction;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.Div;
import org.wdbuilder.jaxbhtml.element.I;
import org.wdbuilder.jaxbhtml.element.Span;

public class SectionHeader extends Div {

	public SectionHeader(String title) throws JAXBException {
		
		Div div = new Div( "btn-group" );

		// TODO: not the best way
		Span span = new Span();
		span.setText(title);
		div.add( span );
		
		for (final IUIAction icon : getIcons()) {
			div.add(createButton(icon));
		}
		add(div);
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
		result.setClassName("btn btn-small");
		result.setTitle( uiAction.getTitle() );
		result.add(i);
		if (IUIActionClick.class.isInstance(uiAction)) {
			result.setOnClick(IUIActionClick.class.cast(uiAction)
					.getOnClickHandler());
		} else if (IUIActionURL.class.isInstance(uiAction)) {
			result.setHref(IUIActionURL.class.cast(uiAction).getURL());
		}
		return result;
	}
}
