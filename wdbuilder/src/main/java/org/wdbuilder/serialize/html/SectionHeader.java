package org.wdbuilder.serialize.html;

import java.util.Collections;

import javax.xml.bind.JAXBException;

import org.wdbuilder.gui.IUIAction;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.Div;
import org.wdbuilder.jaxbhtml.element.I;

public class SectionHeader extends Div {

	private static final String STYLE_FULL_WIDTH = "width:100%";

	private final String title;

	public SectionHeader(String title) throws JAXBException {
		this.title = title;
		
		Div div = new Div( "btn-group" );
		// TODO: how to add the title
		
		for (final IUIAction icon : getIcons()) {
			div.add(createLink(icon));
		}
		add(div);
	}

	// May be overridden
	public Iterable<IUIAction> getIcons() {
		return Collections.emptyList();
	}

	private static A createLink(IUIAction uiAction) {
		I i = new I();
		i.setClassName( "icon-white " + uiAction.getResourceId() );
		
		A a = new A("btn btn-mini");
		a.setTitle( uiAction.getTitle() );
		a.add(i);
		if (IUIActionClick.class.isInstance(uiAction)) {
			a.setOnClick(IUIActionClick.class.cast(uiAction)
					.getOnClickHandler());
		} else if (IUIActionURL.class.isInstance(uiAction)) {
			a.setHref(IUIActionURL.class.cast(uiAction).getURL());
		}
		return a;
	}

	public static String getImageURL(IUIAction uiAction) {
		return "resources/" + uiAction.getResourceId() + ".gif";
	}

}
