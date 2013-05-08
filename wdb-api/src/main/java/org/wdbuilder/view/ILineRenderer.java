package org.wdbuilder.view;

public interface ILineRenderer<T extends ILineRendererContext> {
  public void draw( T renderCtx);
}

