package org.wdbuilder.view;

public interface ILineRenderer<T extends ILineRendererContextBase> {
  public void draw( T renderCtx);
}
