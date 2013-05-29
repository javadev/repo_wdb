package org.wdbuilder.jaxbhtml.element;

import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.HtmlContainer;

@XmlRootElement(name = "i")
public class I extends HtmlContainer {
	public I() {
		super();
	}
	public I( String className ) {
		super( className );
	}
}
