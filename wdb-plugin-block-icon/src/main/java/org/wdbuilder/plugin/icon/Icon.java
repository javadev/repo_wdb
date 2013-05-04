package org.wdbuilder.plugin.icon;

import org.wdbuilder.domain.DisplayNameAware;
import org.wdbuilder.domain.helper.Dimension;

public enum Icon implements DisplayNameAware {
	Avatar("avatar", new Dimension(90, 110)), Beer("beer", new Dimension(110,
			120)), LegoBlocks("lego-blocks", new Dimension(90, 100)), Plane(
			"plane", new Dimension(110, 120)), Recorder("recorder",
			new Dimension(100, 110));
	private final String id;
	private final Dimension size;

	private Icon(String id, Dimension size) {
		this.id = id;
		this.size = size;
	}

	public String getPath() {
		return "org/wdbuilder/plugin/icon/images/" + id + ".png";
	}

	public Dimension getSize() {
		return this.size;
	}

	@Override
	public String getDisplayName() {
		return String.valueOf(this);
	}
}
