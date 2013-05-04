package org.wdbuilder.jaxbhtml.element;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.AHtmlContainer;
import org.wdbuilder.jaxbhtml.HtmlElement;


@SuppressWarnings("unused")
@XmlRootElement(name = "a")
public class A extends AHtmlContainer<HtmlElement> {

	@XmlAttribute(name = "href")
	private String href;

	@XmlAttribute(name = "onmouseover")
	private String onMouseOver;

	@XmlAttribute(name = "onmouseout")
	private String onMouseOut;

	private Collection<HtmlElement> list = new ArrayList<HtmlElement>(2);
	
	public A() {
		super();
	}
	
	public A(String className) {
		super(className);
	}		

	private A(HtmlElement content) {
		this.add(content);
	}	

	@Override
	@XmlAnyElement
	public Collection<HtmlElement> getContent() {
		return this.list;
	}

	public void setOnMouseOver(String onMouseOver) {
		this.onMouseOver = onMouseOver;
	}
	

	public void setOnMouseOut(String onMouseOut) {
		this.onMouseOut = onMouseOut;
	}

	public void setHref(String href) {
		super.setOnClick(null);
		this.href = href;
	}

	@Override
	public void setOnClick(String onClick) {
		super.setOnClick(onClick);
		this.href = "#";
	}
}
