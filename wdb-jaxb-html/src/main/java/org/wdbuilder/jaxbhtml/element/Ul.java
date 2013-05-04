package org.wdbuilder.jaxbhtml.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.AHtmlContainer;


@XmlRootElement(name = "ul")
public class Ul extends AHtmlContainer<Li> {
	
	@XmlElement
	private List<Li> li = new ArrayList<Li>( 8 );
	
	public Ul() {
		super();
    }
	

	public Ul(String className ) {
		super( className );
    }

	@Override
	public Collection<Li> getContent() {
		return this.li;
	}
}
