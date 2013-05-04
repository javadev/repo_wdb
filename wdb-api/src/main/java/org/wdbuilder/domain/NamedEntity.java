package org.wdbuilder.domain;

import static org.apache.commons.lang.StringUtils.isEmpty;


public class NamedEntity extends Entity {

	private static final long serialVersionUID = 1L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (isEmpty(name)) {
			throw new IllegalArgumentException("Name can't be empty");
		}
		this.name = name;
	}

}