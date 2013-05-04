package org.wdbuilder.jaxbhtml.element;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.HtmlContainer;


@XmlRootElement(name = "td")
@SuppressWarnings("unused")
public class Td extends HtmlContainer {

	public enum HAlign {
		left, center, right;
		public void set(Td td) {
			td.align = this.toString();
		}

	}

	public enum VAlign {
		top, middle, bottom;
		;
		public void set(Td td) {
			td.valign = this.toString();
		}
	}

	@XmlAttribute
	private String align;
	@XmlAttribute
	private String valign;
	@XmlAttribute
	private String colspan;
	@XmlAttribute
	private String rowspan;
	
	public Td() {
		super();
    }	

	public Td(String className) {
		super( className );
    }

	public void setColSpan(int n) {
		if (1 < n) {
			colspan = String.valueOf(n);
		}
	}

	public void setRowSpan(int n) {
		if (1 < n) {
			rowspan = String.valueOf(n);
		}
	}

}
