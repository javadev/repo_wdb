package org.wdbuilder.gui;

abstract class UIEntityFormFactory {

    public abstract TwoColumnForm getForm();

    public abstract String getSubmitCall();

    public abstract String getTitle();

    protected final String diagramKey;

    UIEntityFormFactory(String diagramKey) {
        this.diagramKey = diagramKey;
    }
}
