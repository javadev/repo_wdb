package org.wdbuilder.jaxbhtml.element;

import java.awt.Dimension;
import java.awt.Point;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.HtmlElement;


@XmlRootElement(name = "area")
public class Area extends HtmlElement {

	@XmlAttribute
	private String shape;
	@XmlAttribute
	private String href;
	@XmlAttribute
	private String coords;
	@XmlAttribute
	private String alt;
	@XmlAttribute
	private String title;
	@XmlAttribute(name = "onmousedown")
	private String onMouseDown;
	
	private Area() {
		super();
	}	

	private Area(String shape) {
		super();
		this.shape = shape;
	}

	public void setTitle(String title) {
		this.title = this.alt = title;
	}

	public void setOnMouseDown(String onMouseDown) {
		this.href = "#";
		this.onMouseDown = onMouseDown;
	}

	public void setHref(String href) {
		this.href = href;
		this.onMouseDown = null;
	}

	protected void setCoords(String coords) {
		this.coords = coords;
	}

	public static class Circle extends Area {
		public Circle(Point center, int radius) {
			super("circle");
			setCoords(String.valueOf(center.x) + ',' + String.valueOf(center.y) + ',' + String.valueOf(radius));
		}
	}

	public static class Rect extends Area {
		public Rect(Point topLeft, Dimension size) {
			super("rect");
			setCoords(String.valueOf(topLeft.x) + ',' + String.valueOf(topLeft.y) + ','
					+ String.valueOf(topLeft.x + size.width) + ',' + String.valueOf(topLeft.y + size.height));
		}
	}

}
