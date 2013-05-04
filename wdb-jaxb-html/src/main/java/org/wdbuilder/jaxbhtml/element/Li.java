package org.wdbuilder.jaxbhtml.element;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.HtmlContainer;


@XmlRootElement(name = "li")
@SuppressWarnings("unused")
public class Li extends HtmlContainer {

	public Li() {
		super();
    }
	
	public Li(String className) {
		super( className );
    }

}
