package org.wdbuilder.gui;

import org.wdbuilder.jaxbhtml.HtmlElement;

public abstract class IUIActionClick implements IUIAction {

    public abstract String getOnClickHandler();

    @Override
    public void setActionToHTMLElement(HtmlElement element) {
        element.setOnClick(getOnClickHandler());
    }

    @Override
    public String getClassName() {
        return "";
    }
}
