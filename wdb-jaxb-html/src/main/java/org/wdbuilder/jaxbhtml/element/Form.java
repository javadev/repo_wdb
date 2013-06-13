package org.wdbuilder.jaxbhtml.element;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.HtmlContainer;

@XmlRootElement(name = "form")
public class Form extends HtmlContainer {

	private String encodeType = null;

	public Form() {
		super();
	}

	public Form(String className) {
		super(className);
	}

	@XmlAttribute(name = "enctype")
	public String getEncodeType() {
		return encodeType;
	}

	public void setEncodeType(String encodeType) {
		this.encodeType = encodeType;
	}

}
