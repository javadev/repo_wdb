package org.wdbuilder.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

import static org.apache.commons.lang.StringUtils.isEmpty;

public class Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String key;

	@XmlAttribute
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		if( isEmpty(key) ) {
			throw new IllegalArgumentException( "Key can't be empty" );
		}
		this.key = key;
	}

}
