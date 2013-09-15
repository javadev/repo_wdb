package org.wdbuilder.gui;

import org.wdbuilder.jaxbhtml.HtmlElement;

public interface IUIAction {

    String getResourceId();

    String getTitle();

    void setActionToHTMLElement(HtmlElement element);

    String getClassName();
}
