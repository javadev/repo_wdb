package org.wdbuilder.jaxbhtml.element;

import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.HtmlContainer;


@XmlRootElement(name = "li")
public class Li extends HtmlContainer {

    public Li() {
        super();
    }

    public Li(String className) {
        super(className);
    }

}
