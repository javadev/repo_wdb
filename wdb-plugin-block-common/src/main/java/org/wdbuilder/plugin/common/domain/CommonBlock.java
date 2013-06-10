package org.wdbuilder.plugin.common.domain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.DisplayNameAware;
import org.wdbuilder.domain.IForegroundProvider;
import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.service.DiagramService;

public class CommonBlock extends Block {
  private static final long serialVersionUID = 1L;

	private Shape shape;

	private Background background;

	@XmlAttribute
	public Background getBackground() {
		return background;
	}

	public void setBackground(Background background) {
		if (null == background) {
			throw new IllegalArgumentException("Background can't be null");
		}
		this.background = background;
	}

	@XmlAttribute
	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		if (null == shape) {
			throw new IllegalArgumentException("Shape can't be null");
		}
		this.shape = shape;
	}

	@Override
	public int getMaxLinkSocketNumX() {
		return shape.getMaxLinkSocketNumX(getSize());
	}

	@Override
	public int getMaxLinkSocketNumY() {
		return shape.getMaxLinkSocketNumY(getSize());
	}

	public static enum Background implements IGradientBackgroundProvider,
			IForegroundProvider {
		Grey(0x999999, 0xffffff, 0x000000, "Grey"), DarkGreen(0x004000,
				0x008000, 0xffffff, "Dark Green"), Green(0x009900, 0x99ffcc,
				0x000000, "Green"), NavyBlue(0x003366, 0x003399, 0xffffff,
				"Navy Blue"), Blue(0x0066cc, 0x00ccff, 0x000000, "Blue"), Brown(
				0x333300, 0x666600, 0xffffff, "Brown"), Olive(0x7e9900,
				0xa0cc33, 0xffffff, "Olive"), Brick(0x993333, 0xcc6600,
				0xffffff, "Brick")

		;
		private final String displayName;
		private final Color primaryBackgroundColor;
		private final Color secondaryBackgroundColor;
		private final Color foregroundColor;

		private Background(int primaryBackgroundColor,
				int secondaryBackgoundColor, int foregroundColor,
				String displayName) {
			this.primaryBackgroundColor = new Color(primaryBackgroundColor);
			this.secondaryBackgroundColor = new Color(secondaryBackgoundColor);
			this.foregroundColor = new Color(foregroundColor);
			this.displayName = displayName;
		}

		@Override
		public Color getForegroundColor() {
			return foregroundColor;
		}

		@Override
		public Color getPrimaryBackgroundColor() {
			return primaryBackgroundColor;
		}

		@Override
		public String getDisplayName() {
			return displayName;
		}

		@Override
		public Color getSecondaryBackgroundColor() {
			return secondaryBackgroundColor;
		}
	}

	public static enum Shape implements DisplayNameAware {
		Rectangle("rectangle") {
			@Override
			public void fill(Graphics2D gr, Rectangle r) {
				gr.fillRect(r.x, r.y, r.width, r.height);
			}

			@Override
			public int getMaxLinkSocketNumX(Dimension size) {
				return getMaxLinkSocketNumXBySize(size);
			}

			@Override
			public int getMaxLinkSocketNumY(Dimension size) {
				return getMaxLinkSocketNumYBySize(size);
			}

		},
		RoundedRectangle("rounded rectangle") {
			private static final int R = 10;

			@Override
			public void fill(Graphics2D gr, Rectangle r) {
				gr.fillRoundRect(r.x, r.y, r.width, r.height, R, R);
			}

			@Override
			public int getMaxLinkSocketNumX(Dimension size) {
				return getMaxLinkSocketNumXBySize(size);
			}

			@Override
			public int getMaxLinkSocketNumY(Dimension size) {
				return getMaxLinkSocketNumYBySize(size);
			}
		},
		Oval("oval") {
			@Override
			public void fill(Graphics2D gr, Rectangle r) {
				gr.fillOval(r.x, r.y, r.width, r.height);
			}

			@Override
			public int getMaxLinkSocketNumX(Dimension size) {
				return 0;
			}

			@Override
			public int getMaxLinkSocketNumY(Dimension size) {
				return 0;
			}
		},
		Romb("romb") {

			private int[][] calulateCoords(Rectangle r) {
				return new int[][] {
						{ r.x, r.x + r.width / 2, r.x + r.width,
								r.x + r.width / 2 },
						{ r.y + r.height / 2, r.y, r.y + r.height / 2,
								r.y + r.height } };
			}

			@Override
			public void fill(Graphics2D gr, Rectangle rect) {
				int[][] coords = calulateCoords(rect);
				gr.fillPolygon(coords[0], coords[1], coords[0].length);
			}

			@Override
			public int getMaxLinkSocketNumX(Dimension size) {
				return 0;
			}

			@Override
			public int getMaxLinkSocketNumY(Dimension size) {
				return 0;
			}
		};

		abstract public void fill(Graphics2D gr, Rectangle rect);

		public abstract int getMaxLinkSocketNumX(Dimension size);

		public abstract int getMaxLinkSocketNumY(Dimension size);

		private final String displayName;

		private Shape(String displayName) {
			this.displayName = displayName;
		}

		public String getDisplayName() {
			return displayName;
		}

		protected static int getMaxLinkSocketNumYBySize(Dimension size) {
			final int n = 2 * DiagramService.LINE_OFFSET;
			return (size.getHeight() - n) / (2 * n);
		}

		protected static int getMaxLinkSocketNumXBySize(Dimension size) {
			final int n = 2 * DiagramService.LINE_OFFSET;
			return (size.getWidth() - n) / (2 * n);
		}

		public final Shape[] getCompatible() {
			final List<Shape> result = new ArrayList<Shape>(4);
			result.add(this);
			switch (this) {
			case Rectangle:
				result.add(RoundedRectangle);
				break;
			case RoundedRectangle:
				result.add(Rectangle);
				break;
			case Oval:
				result.add(Romb);
				break;
			case Romb:
				result.add(Oval);
				break;
			}
			Shape[] array = new Shape[result.size()];
			return result.toArray(array);
		}

	}
}
