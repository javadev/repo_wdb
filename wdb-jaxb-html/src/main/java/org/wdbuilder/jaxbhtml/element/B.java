package org.wdbuilder.jaxbhtml.element;

import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.HtmlContainer;

@XmlRootElement(name = "b")
public class B extends HtmlContainer {
  public B() {
		super();
	}
	public B( String className ) {
		super( className );
	}
}
