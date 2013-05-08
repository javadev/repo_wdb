package org.wdbuilder.view.line.end;

import org.wdbuilder.domain.DisplayNameAware;
import org.wdbuilder.view.ILineEndRendererContext;
import org.wdbuilder.view.ILineRenderer;

public interface ILineEnd extends DisplayNameAware {
  ILineRenderer<ILineEndRendererContext> getRenderer();
}
