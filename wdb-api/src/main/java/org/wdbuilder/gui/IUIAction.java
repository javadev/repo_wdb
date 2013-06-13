package org.wdbuilder.gui;

import org.wdbuilder.jaxbhtml.HtmlElement;

public interface IUIAction {

	public String getResourceId();

	public String getTitle();
	
	public void setActionToHTMLElement( HtmlElement element );
	
	public String getClassName();
}
