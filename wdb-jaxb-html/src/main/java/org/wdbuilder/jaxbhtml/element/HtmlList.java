package org.wdbuilder.jaxbhtml.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.wdbuilder.jaxbhtml.AHtmlContainer;

class HtmlList extends AHtmlContainer<Li> {

	@XmlElement
	private List<Li> li = new ArrayList<Li>(8);

	HtmlList() {
		super();
	}

	HtmlList(String className) {
		super(className);
	}

	@Override
	public Collection<Li> getContent() {
		return this.li;
	}
}
