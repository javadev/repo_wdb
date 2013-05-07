package org.wdbuilder.view.line.end;

import org.wdbuilder.domain.DisplayNameAware;
import org.wdbuilder.view.ILineEndRenderer;

public interface ILineEnd extends DisplayNameAware {
  ILineEndRenderer getRenderer();
}
