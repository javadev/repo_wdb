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
	},
	DASHED("dashed") {

		@Override
		public ILineRenderer<ILineRendererContext> getRenderer() {
			return new DashedLineRenderer(5, 5);
		}

	},
	DOTTED("dotted") {

		@Override
		public ILineRenderer<ILineRendererContext> getRenderer() {
			return new DashedLineRenderer(7, 2);
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
