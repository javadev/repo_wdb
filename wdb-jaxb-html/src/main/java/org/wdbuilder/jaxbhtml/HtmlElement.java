package org.wdbuilder.jaxbhtml;

import javax.xml.bind.annotation.XmlAttribute;

public class HtmlElement implements IHtml {

	@XmlAttribute
	private String id;
	
	@XmlAttribute(name = "class")
	private String className;
	
	@XmlAttribute
	private String style;
	
	@XmlAttribute(name = "onclick")
	private String onClick;
	
	private String onMouseOver;	

	public HtmlElement() {
		this(null);
	}

	public HtmlElement(String className) {
		this.className = className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setOnClick( String onClick ) {
		this.onClick = onClick;
	}

	@XmlAttribute(name = "onmouseover")	
	public String getOnMouseOver() {
		return onMouseOver;
	}

	public void setOnMouseOver(String onMouseOver) {
		this.onMouseOver = onMouseOver;
	}
}
