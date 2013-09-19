package org.wdbuilder.jaxbhtml.element;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.wdbuilder.jaxbhtml.AHtmlContainer;


@XmlRootElement(name = "map")
public class Map extends AHtmlContainer<Area> {

    private String name;

    private Collection<Area> area = new ArrayList<Area>(4);

    @Override
    @XmlElement(name = "area")
    public Collection<Area> getContent() {
        return this.area;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getName() {
        return this.name;
    }

}
