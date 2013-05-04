package org.wdbuilder.jaxbhtml.element;

import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.HtmlContainer;


@XmlRootElement(name = "div")
public class Div extends HtmlContainer {
	public Div() {
		super();
	}
	public Div( String className ) {
		super( className );
	}
}
