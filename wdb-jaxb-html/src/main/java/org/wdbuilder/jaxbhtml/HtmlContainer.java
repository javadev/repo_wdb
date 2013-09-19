package org.wdbuilder.jaxbhtml;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAnyElement;

public class HtmlContainer extends AHtmlContainer<IHtml> {

    private Collection<IHtml> list = new ArrayList<IHtml>(8);

    public HtmlContainer() {
        super();
    }

    public HtmlContainer(String className) {
        super(className);
    }

    @Override
    @XmlAnyElement
    public Collection<IHtml> getContent() {
        return list;
    }

}
