package org.wdbuilder.view;

import org.wdbuilder.domain.DisplayNameAware;

// TODO rename this into "some descriptor" (2013/05/08)
public interface ILineEnd<T extends ILineRendererContextBase> extends DisplayNameAware {
  ILineRenderer<T> getRenderer();
}
