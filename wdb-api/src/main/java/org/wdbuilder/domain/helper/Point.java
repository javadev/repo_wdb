package org.wdbuilder.domain.helper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;


public class Point implements Serializable, AWTAware<java.awt.Point> {
	private static final long serialVersionUID = 1L;

	private int x;
	private int y;

	public Point() {

	}

	@Override
	public java.awt.Point toAWT() {
		return new java.awt.Point(x, y);
	}

	public Point(int x, int y) {
		setX(x);
		setY(y);
	}

	@XmlAttribute
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	@XmlAttribute
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	// Helper methods:
	public Point addX(int n) {
		return new Point(x + n, y);
	}

	public Point addY(int n) {
		return new Point(x, y + n);
	}
}
