package org.wdbuilder.jaxbhtml.element;

import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.HtmlContainer;

@XmlRootElement(name = "button")
public class Button extends HtmlContainer {
  public Button() {
        super();
    }
    public Button(String className) {
        super(className);
    }
}
