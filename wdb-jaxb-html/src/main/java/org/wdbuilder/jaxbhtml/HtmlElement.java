package org.wdbuilder.jaxbhtml;

import javax.xml.bind.annotation.XmlAttribute;

public class HtmlElement implements IHtml {

    private String id;

    private String className;

    private String style;

    private String onClick;

    private String onMouseOver;

    private String title;

    private String dataToggle;

    private String dataOriginalTitle;

    public HtmlElement() {
        this(null);
    }

    public HtmlElement(String className) {
        this.className = className;
    }

    @XmlAttribute(name = "class")
    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @XmlAttribute(name = "style")
    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @XmlAttribute(name = "id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "onclick")
    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    @XmlAttribute(name = "onmouseover")
    public String getOnMouseOver() {
        return onMouseOver;
    }

    public void setOnMouseOver(String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }

    @XmlAttribute(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlAttribute(name = "data-toggle")
    public String getDataToggle() {
        return dataToggle;
    }

    public void setDataToggle(String dataToggle) {
        this.dataToggle = dataToggle;
    }

    @XmlAttribute(name = "data-original-title")
    public String getDataOriginalTitle() {
        return dataOriginalTitle;
    }

    public void setDataOriginalTitle(String dataOriginalTitle) {
        this.dataOriginalTitle = dataOriginalTitle;
    }
}
