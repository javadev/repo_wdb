package org.wdbuilder.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

import static org.apache.commons.lang.StringUtils.isEmpty;

@SuppressWarnings("serial")
public class Entity implements Serializable {

    private String key;
    private String name;

    @XmlAttribute
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        if (isEmpty(key)) {
            throw new IllegalArgumentException("Key can't be empty");
        }
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
