package org.wdbuilder.serialize.html;

import java.awt.Dimension;
import java.util.Collections;

import javax.xml.bind.JAXBException;

import org.wdbuilder.gui.IUIAction;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.gui.IUIActionId;
import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.Div;
import org.wdbuilder.jaxbhtml.element.Img;
import org.wdbuilder.jaxbhtml.element.Table;
import org.wdbuilder.jaxbhtml.element.Td;
import org.wdbuilder.jaxbhtml.element.Tr;

public class SectionHeader extends Div {

	private static final String CLASS = "header";
	private static final String CLASS_ICON = "header-icon";
	private static final String STYLE_FULL_WIDTH = "width:100%";

	private static final Dimension ICON_SIZE = new Dimension(16, 16);

	private final String title;

	public SectionHeader(String title) throws JAXBException {
		this.title = title;
		Tr tr = new Tr();
		tr.add(createTdTitle());
		for (final IUIAction icon : getIcons()) {
			tr.add(createTdIcon(icon));
		}
		Table table = new Table(CLASS);
		table.setStyle(STYLE_FULL_WIDTH);
		table.add(tr);

		setClassName(CLASS);
		add(table);
	}

	// May be overridden
	public Iterable<IUIAction> getIcons() {
		return Collections.emptyList();
	}

	private Td createTdTitle() {
		Td result = new Td();
		result.setStyle(STYLE_FULL_WIDTH);
		result.setText(title);
		return result;
	}

	private static Td createTdIcon(IUIAction uiAction) {
		Img img = new Img(CLASS_ICON);
		img.setSize(ICON_SIZE);
		img.setSrc(getImageURL(uiAction));
		img.setTitle(uiAction.getTitle());

		Td result = new Td(CLASS_ICON);
		A a = new A();
		a.add(img);
		if (IUIActionId.class.isInstance(uiAction)) {
			img.setId(IUIActionId.class.cast(uiAction).getId());
		}
		if (IUIActionClick.class.isInstance(uiAction)) {
			a.setOnClick(IUIActionClick.class.cast(uiAction)
					.getOnClickHandler());
		} else if (IUIActionURL.class.isInstance(uiAction)) {
			a.setHref(IUIActionURL.class.cast(uiAction).getURL());
		}
		result.add(a);
		return result;
	}

	public static String getImageURL(IUIAction uiAction) {
		return "resources/" + uiAction.getResourceId() + ".gif";
	}

}
