package org.wdbuilder.jaxbhtml;

import javax.xml.bind.annotation.XmlValue;

public class HtmlContainedText implements IHtml {
    @XmlValue
	private String text;
	
	public HtmlContainedText() {
		
	}	
	
	public HtmlContainedText( String text ) {
		this.text = text;
	}
}
