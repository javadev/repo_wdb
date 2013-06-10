package org.wdbuilder.jaxbhtml;

import javax.xml.bind.annotation.XmlValue;

public class HtmlContainedText implements IHtml {

	private String text;

	public HtmlContainedText() {

	}

	public HtmlContainedText(String text) {
		this.text = text;
	}

	@XmlValue
	public String getText() {
		return this.text;
	}
}
