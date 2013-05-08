package org.wdbuilder.domain;

import java.awt.Color;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.view.line.LineStyle;

public class Link extends Entity {
	private static final long serialVersionUID = 1L;

	private List<LinkSocket> sockets;

	private Point pivot;
	
	private LineColor lineColor = LineColor.Black;
	
	private LineStyle lineStyle = LineStyle.SOLID;

	public Point getPivot() {
		return pivot;
	}

	public void setPivot(Point pivot) {
		if (null == pivot) {
			throw new IllegalArgumentException("Pivot can't be null");
		}
		this.pivot = pivot;
	}

	public List<LinkSocket> getSockets() {
		return sockets;
	}

	public void setSockets(List<LinkSocket> sockets) {
		this.sockets = sockets;
	}

	@XmlAttribute
	public LineColor getLineColor() {
		return lineColor;
	}

	public void setLineColor(LineColor lineColor) {
		this.lineColor = lineColor;
	}

	@XmlAttribute
	public LineStyle getLineStyle() {
		return lineStyle;
	}

	public void setLineStyle(LineStyle lineStyle) {
		this.lineStyle = lineStyle;
	}

	public static enum LineColor implements IForegroundProvider,
			DisplayNameAware {
		Red(0xff0000, "Red"), Green(0x00cc00, "Green"), Blue(0x0000cc, "Blue"), Black(
				0x000000, "Black"), Gray(0x999999, "Gray");
		private final String displayName;
		private final Color foregroundColor;

		private LineColor(int foregroundColor, String displayName) {
			this.foregroundColor = new Color(foregroundColor);
			this.displayName = displayName;
		}

		@Override
		public Color getForegroundColor() {
			return foregroundColor;
		}

		@Override
		public String getDisplayName() {
			return displayName;
		}
	}

}
