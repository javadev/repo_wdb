package org.wdbuilder.view.line;

import org.wdbuilder.view.ILineEnd;
import org.wdbuilder.view.ILineRenderer;
import org.wdbuilder.view.ILineRendererContext;

public enum LineStyle implements ILineEnd<ILineRendererContext> {
  SOLID("solid") {
		@Override
		public ILineRenderer<ILineRendererContext> getRenderer() {
			return new SolidLineRenderer();
		}

	};
	private final String displayName;

	private LineStyle(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
}
