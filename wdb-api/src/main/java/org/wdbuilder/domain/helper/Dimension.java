package org.wdbuilder.domain.helper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;


public class Dimension implements Serializable, AWTAware<java.awt.Dimension> {
    private static final long serialVersionUID = 1L;

    private int width;
    private int height;

    public Dimension() {

    }

    public Dimension(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    @XmlAttribute
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (0 > width) {
            throw new IllegalArgumentException("Width can't be less than 0");
        }
        this.width = width;
    }

    @XmlAttribute
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (0 > height) {
            throw new IllegalArgumentException("Height can't be less than 0");
        }
        this.height = height;
    }

    @Override
    public java.awt.Dimension toAWT() {
        return new java.awt.Dimension(this.width, this.height);
    }

}
