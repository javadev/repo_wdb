package org.wdbuilder.web;

import org.wdbuilder.domain.Diagram;

public class ApplicationState {

	public enum Mode {
		LINE("mode-block", "Switch to block mode", "WDB.LineDraw.mouseDown"), BLOCK(
				"mode-line", "Switch to line mode", "WDB.BlockDrag.mouseDown");
		private final String title;
		private final String resourceId;
		private final String jsDragStart;

		private Mode(String resourceId, String title, String jsDragStart) {
			this.resourceId = resourceId;
			this.title = title;
			this.jsDragStart = jsDragStart;
		}

		public String getResourceId() {
			return resourceId;
		}

		public String getTitle() {
			return title;
		}

		public String getJsDragStart() {
			return jsDragStart;
		}
	}

	private Mode mode = Mode.BLOCK;
	private Diagram diagram;
	private String selectedBlockKey = null;

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

	public String getSelectedBlockKey() {
		return selectedBlockKey;
	}

	public void setSelectedBlockKey(String selectedBlockKey) {
		this.selectedBlockKey = selectedBlockKey;
	}

	public boolean isBlockMode() {
		return Mode.BLOCK.equals(mode);
	}
}
