package org.wdbuilder.jaxbhtml.element;

import org.junit.Test;
import org.w3c.dom.Element;

public class TableTest extends ElementTest {

	private static final CellData[][] CELL_DATA = new CellData[][] {
			new CellData[] {
					new CellData("1.1", Td.HAlign.left, Td.VAlign.top),
					new CellData("1.2", Td.HAlign.right, Td.VAlign.top) },
			new CellData[] {
					new CellData("2.1", Td.HAlign.left, Td.VAlign.bottom),
					new CellData("2.2", Td.HAlign.right, Td.VAlign.bottom) } };

	@Test
	public void table() {

		final Table table = createTable(CELL_DATA);

		final IChildElementChecker rowChecker = new IChildElementChecker() {
			@Override
			public void assertElement(final int row, Element elem) {
				assertElementAndChildren(elem, "tr", CELL_DATA[row].length,
						new ColumnChecker(row));
			}
		};

		assertElementAndChildren(toDOM(table), "table", CELL_DATA.length,
				rowChecker);

	}

	private static Table createTable(CellData[][] data) {
		Table table = new Table();
		for (CellData[] item : data) {
			table.add(createTr(item));
		}
		return table;
	}

	private static Tr createTr(CellData[] data) {
		Tr tr = new Tr();
		for (CellData item : data) {
			tr.add(createTd(item));
		}

		return tr;
	}

	private static Td createTd(CellData data) {
		Td td = new Td();
		td.setText(data.str);
		if (null != data.hAlign) {
			data.hAlign.set(td);
		}
		if (null != data.vAlign) {
			data.vAlign.set(td);
		}
		return td;
	}

	private static class CellData {
		private final String str;
		private final Td.HAlign hAlign;
		private final Td.VAlign vAlign;

		private CellData(String str, Td.HAlign hAlign, Td.VAlign vAlign) {
			this.str = str;
			this.hAlign = hAlign;
			this.vAlign = vAlign;
		}

		private CellData(String str, Td.HAlign hAlign) {
			this(str, hAlign, null);
		}

		private CellData(String str) {
			this(str, null, null);
		}
	}

	private static class ColumnChecker implements IChildElementChecker {

		private final int row;

		private ColumnChecker(int row) {
			this.row = row;
		}

		@Override
		public void assertElement(int col, Element elem) {
			final String text = String.valueOf(row + 1) + "."
					+ String.valueOf(col + 1);

			assertElementAndChildren(
					elem,
					"td",
					1,
					new IChildElementChecker() {
						@Override
						public void assertElement(int index, Element elem) {
							assertElementAndText(elem, "span", text);
						}
					},
					new String[] { "valign",
							String.valueOf(CELL_DATA[row][col].vAlign) },
					new String[] { "align",
							String.valueOf(CELL_DATA[row][col].hAlign) });
		}
	}

}
