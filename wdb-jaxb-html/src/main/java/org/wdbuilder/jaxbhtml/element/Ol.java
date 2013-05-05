package org.wdbuilder.jaxbhtml.element;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "ol")
public class Ol extends HtmlList {
	
	public Ol() {
		super();
    }
	

	public Ol(String className ) {
		super( className );
    }
}
