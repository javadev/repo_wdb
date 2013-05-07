package org.wdbuilder.domain;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAttribute;

import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.service.DiagramService;
import org.wdbuilder.view.line.end.ILineEnd;
import org.wdbuilder.view.line.end.LineEnd;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * Predefined places for connection with links
 * 
 * @author o.pavloschuk
 * 
 */
public class LinkSocket {

	private static final Dimension AREA_DIM = DiagramService.LINE_AREA;
	private static final int M = DiagramService.LINE_OFFSET;

	private static final Set<Direction> DIRECTION_HORIZONTAL = EnumSet.of(
			Direction.LEFT, Direction.RIGHT);
	private static final Set<Direction> DIRECTION_VERTICAL = EnumSet.of(
			Direction.TOP, Direction.BOTTOM);

	private Direction direction;
	private int index;
	private String blockKey;
	// TODO (2013/05/07) quick and dirty NPE fix
	private LineEnd lineEnd = LineEnd.SIMPLE;

	public LinkSocket() {
	}

	public LinkSocket(String blockKey, Direction direction, int index) {
		setBlockKey(blockKey);
		setDirection(direction);
		setIndex(index);
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setBlockKey(String blockKey) {
		this.blockKey = blockKey;
	}	

	public void setLineEnd(LineEnd lineEnd) {
		this.lineEnd = lineEnd;
	}	

	public final Point getLocation(Block b) {
		return this.direction.getLocation(b, this.index);
	}

	@XmlAttribute
	public final Direction getDirection() {
		return this.direction;
	}

	@XmlAttribute
	public int getIndex() {
		return this.index;
	}

	@XmlAttribute
	public LineEnd getLineEnd() {
		return lineEnd;
	}
	
	@XmlAttribute
	public String getBlockKey() {
		return blockKey;
	}

	public final boolean isHorizontal() {
		return getDirection().horizontal;
	}

	public Point getOffset(Point p) {
		return getDirection().getOffset(p);
	}

	public Point getOffset(Block b) {
		return getOffset(getLocation(b));
	}

	public Rectangle getArea(Block block) {
		return getDirection().getArea(getLocation(block));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((blockKey == null) ? 0 : blockKey.hashCode());
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkSocket other = (LinkSocket) obj;
		if (blockKey == null) {
			if (other.blockKey != null)
				return false;
		} else if (!blockKey.equals(other.blockKey))
			return false;
		if (direction != other.direction)
			return false;
		if (index != other.index)
			return false;
		return true;
	}

	// TODO (2013/05/05) extract better method
	public static Collection<LinkSocket> getAvailable(
			final Collection<LinkSocket> forbiden, Block b) {
		String blockKey = b.getKey();
		final List<LinkSocket> values = new ArrayList<LinkSocket>(4);

		final int maxY = b.getMaxLinkSocketNumY();
		addLinkSocketsForDirections(blockKey, DIRECTION_HORIZONTAL, maxY,
				values);

		final int maxX = b.getMaxLinkSocketNumX();
		addLinkSocketsForDirections(blockKey, DIRECTION_VERTICAL, maxX, values);

		final Predicate<LinkSocket> predicate = new Predicate<LinkSocket>() {
			@Override
			public boolean apply(@Nullable LinkSocket linkSocket) {
				return !forbiden.contains(linkSocket);
			}

		};
		return Collections2.filter(values, predicate);
	}

	private static void addLinkSocketsForDirections(String blockKey,
			final Iterable<Direction> dirs, final int max,
			final List<LinkSocket> values) {
		for (final Direction dir : dirs) {
			for (int n = -max; n <= max; n++) {
				values.add(new LinkSocket(blockKey, dir, n));
			}
		}
	}

	protected static Point getRightBottomLocation(Block b) {
		return new Point(b.getLocation().getX() + b.getSize().getWidth() / 2, b
				.getLocation().getY() + b.getSize().getHeight() / 2);
	}

	protected static Point getLeftTopLocation(Block b) {
		return new Point(b.getLocation().getX() - b.getSize().getWidth() / 2, b
				.getLocation().getY() - b.getSize().getHeight() / 2);
	}

	public enum Direction {

		LEFT(true) {
			@Override
			Point getOffset(Point p) {
				return new Point(p.getX() - M, p.getY());
			}

			@Override
			Rectangle getArea(Point p) {
				return new Rectangle(new Point(p.getX(), p.getY()
						- AREA_DIM.getHeight() / 2).toAWT(), AREA_DIM.toAWT());
			}

			@Override
			public Point getLocation(Block b, int index) {
				return new Point(b.getLocation().getX()
						- b.getSize().getWidth() / 2, b.getLocation().getY()
						+ index * M * 2);
			}

		},
		TOP(false) {
			@Override
			protected Point getOffset(Point p) {
				return new Point(p.getX(), p.getY() - M);
			}

			@Override
			Rectangle getArea(Point p) {
				return new Rectangle(new Point(p.getX() - AREA_DIM.getWidth()
						/ 2, p.getY()).toAWT(), AREA_DIM.toAWT());
			}

			@Override
			public Point getLocation(Block b, int index) {
				return new Point(b.getLocation().getX() + index * M * 2, b
						.getLocation().getY() - b.getSize().getHeight() / 2);
			}

		},
		RIGHT(true) {

			@Override
			Point getOffset(Point p) {
				return new Point(p.getX() + M, p.getY());
			}

			@Override
			Rectangle getArea(Point p) {
				return new Rectangle(new Point(p.getX() - AREA_DIM.getWidth(),
						p.getY() - AREA_DIM.getHeight() / 2).toAWT(),
						AREA_DIM.toAWT());
			}

			@Override
			public Point getLocation(Block b, int index) {
				return new Point(b.getLocation().getX()
						+ b.getSize().getWidth() / 2, b.getLocation().getY()
						+ index * M * 2);
			}

		},
		BOTTOM(false) {
			@Override
			Point getOffset(Point p) {
				return new Point(p.getX(), p.getY() + M);
			}

			@Override
			Rectangle getArea(Point p) {
				return new Rectangle(new Point(p.getX() - AREA_DIM.getWidth()
						/ 2, p.getY() - AREA_DIM.getHeight()).toAWT(),
						AREA_DIM.toAWT());
			}

			@Override
			public Point getLocation(Block b, int index) {
				return new Point(b.getLocation().getX() + index * M * 2, b
						.getLocation().getY() + b.getSize().getHeight() / 2);
			}

		};
		private final boolean horizontal;

		abstract Point getOffset(Point p);

		abstract Rectangle getArea(Point p);

		public abstract Point getLocation(Block b, int index);

		private Direction(boolean horizontal) {
			this.horizontal = horizontal;
		}

		public boolean isHorizontal() {
			return horizontal;
		}

		protected Point getLeftTop(Block b) {
			return getLeftTopLocation(b).addX(-M).addY(-M);
		}

		protected Point getRightBottom(Block b) {
			return getRightBottomLocation(b).addX(M).addY(M);
		}
	}
}
