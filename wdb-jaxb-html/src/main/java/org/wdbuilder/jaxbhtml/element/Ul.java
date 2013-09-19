package org.wdbuilder.jaxbhtml.element;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "ul")
public class Ul extends HtmlList {
    public Ul() {
        super();
    }

    public Ul(String className) {
        super(className);
    }
}
