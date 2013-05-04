package org.wdbuilder.jaxbhtml.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.AHtmlContainer;


@XmlRootElement(name = "table")
public class Table extends AHtmlContainer<Tr> {

	@XmlElement
	private List<Tr> tr = new ArrayList<Tr>(2);
	
	public Table() {
		super();
    }	

	public Table(String className) {
		super( className );
    }

	@Override
	public Collection<Tr> getContent() {
		return this.tr;
	}

}
