package org.wdbuilder.jaxbhtml.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.AHtmlContainer;


@XmlRootElement(name = "tr")
public class Tr extends AHtmlContainer<Td> {

    @XmlElement
    private final List<Td> td = new ArrayList<Td>(4);

    @Override
    public Collection<Td> getContent() {
        return this.td;
    }

}
