package org.wdbuilder.jaxbhtml.element;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.HtmlElement;


@XmlRootElement(name = "select")
public class Select extends HtmlElement {

	public Select() {
		super();
	}

	public Select(String className) {
		super(className);
	}

	@XmlAttribute
	private String name;

	@XmlElement
	private Collection<Option> option = new ArrayList<Option>(4);

	public void add(Option option) {
		this.option.add(option);
	}
	
	public void setName( String name ) {
		this.name = name;
	}

}
