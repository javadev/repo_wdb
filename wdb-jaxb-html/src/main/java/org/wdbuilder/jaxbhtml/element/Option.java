package org.wdbuilder.jaxbhtml.element;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.HtmlContainedText;


@XmlRootElement(name = "option")
public class Option extends HtmlContainedText {
	
	@XmlAttribute
	private String value;

	@XmlAttribute
	private String selected;
	
	public Option() {
		
	}

	public Option(String text) {
		super(text);
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setSelected(boolean selected) {
		if( selected ) {
			this.selected = String.valueOf(true);
		}
	}
}
