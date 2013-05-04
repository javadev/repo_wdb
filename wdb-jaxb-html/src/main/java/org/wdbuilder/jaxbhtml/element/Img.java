package org.wdbuilder.jaxbhtml.element;

import java.awt.Dimension;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.HtmlElement;


@SuppressWarnings("unused")
@XmlRootElement(name = "img")
public class Img extends HtmlElement {

	@XmlAttribute
	private String src;
	
	@XmlAttribute
	private int width;
	
	@XmlAttribute
	private int height;
	
	@XmlAttribute
	private String alt;
	
	@XmlAttribute
	private String title;
	
	@XmlAttribute
	private int border = 0;
	
	@XmlAttribute(name = "usemap")
	private String useMap;
	
	public Img() {
		super();
	}
	
	public Img(String className) {
		super(className);
	}	
	
	public void setSize( Dimension size ) {
		this.width = size.width;
		this.height = size.height;
	}
	
	public void setSrc( String src ) {
		this.src = src;
	}
	
	public void setUseMap( String useMap ) {
		this.useMap = useMap;
	}
	
	public void setTitle( String title ) {
		this.title = this.alt = title;
	}

}
