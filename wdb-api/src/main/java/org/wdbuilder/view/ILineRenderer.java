package org.wdbuilder.view;

public interface ILineRenderer<T extends ILineRendererContextBase> {
  void draw(T renderCtx);
}
