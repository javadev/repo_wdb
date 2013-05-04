package org.wdbuilder.domain;

import javax.xml.bind.annotation.XmlAttribute;

import org.wdbuilder.domain.helper.Point;

import static org.apache.commons.lang.StringUtils.isEmpty;

public class Link extends Entity {
	private static final long serialVersionUID = 1L;

	private String beginKey;
	private String endKey;
	private LinkSocket beginSocket;
	private LinkSocket endSocket;
	
	private Point pivot;

	@XmlAttribute
	public String getBeginKey() {
		return beginKey;
	}

	public void setBeginKey(String beginKey) {
		if( isEmpty(beginKey) ) {
			throw new IllegalArgumentException("Begin key can't be empty" );
		}
		this.beginKey = beginKey;
	}

	@XmlAttribute
	public String getEndKey() {
		return endKey;
	}

	public void setEndKey(String endKey) {
		if( isEmpty(endKey) ) {
			throw new IllegalArgumentException("End key can't be empty" );
		}
		this.endKey = endKey;
	}

	public LinkSocket getBeginSocket() {
		return beginSocket;
	}

	public void setBeginSocket(LinkSocket beginSocket) {
		if( null==beginSocket ) {
			throw new IllegalArgumentException("Begin socket can't be null");
		}
		this.beginSocket = beginSocket;
	}

	public LinkSocket getEndSocket() {
		return endSocket;
	}

	public void setEndSocket(LinkSocket endSocket) {
		if( null==endSocket ) {
			throw new IllegalArgumentException("End socket can't be null");
		}
		this.endSocket = endSocket;
	}

	public Point getPivot() {
	    return pivot;
    }

	public void setPivot(Point pivot) {
		if( null==pivot ) {
			throw new IllegalArgumentException("Pivot can't be null");
		}
	    this.pivot = pivot;
    }

}
