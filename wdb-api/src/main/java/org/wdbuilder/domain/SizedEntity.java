package org.wdbuilder.domain;

import org.wdbuilder.domain.helper.Dimension;

public class SizedEntity extends Entity {
	private static final long serialVersionUID = 1L;

	private Dimension size;

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		if (null == size) {
			throw new IllegalArgumentException("Size can't be null");
		}
		this.size = size;
	}
}
