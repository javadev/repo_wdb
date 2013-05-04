package org.wdbuilder.jaxbhtml.element;

import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.HtmlContainer;


@XmlRootElement(name = "form")
public class Form extends HtmlContainer {

	public Form() {
		super();
	}

	public Form(String className) {
		super(className);
	}
}
