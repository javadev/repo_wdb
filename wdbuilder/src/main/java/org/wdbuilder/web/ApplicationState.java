package org.wdbuilder.web;

import org.wdbuilder.domain.Diagram;

public class ApplicationState {

	public enum Mode {
		LINE("icon-book", "Switch to block mode"), BLOCK("icon-pencil",
				"Switch to line mode");
		private final String title;
		private final String resourceId;

		private Mode(String resourceId, String title) {
			this.resourceId = resourceId;
			this.title = title;
		}

		public String getResourceId() {
			return resourceId;
		}

		public String getTitle() {
			return title;
		}

	}

	private Mode mode = Mode.BLOCK;
	private Diagram diagram;

	public ApplicationState() {
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public Diagram getDiagram() {
		return diagram;
	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}

	public boolean isBlockMode() {
		return Mode.BLOCK.equals(mode);
	}
}
