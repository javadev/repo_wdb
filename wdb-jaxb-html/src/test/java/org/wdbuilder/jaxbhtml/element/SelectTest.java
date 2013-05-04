package org.wdbuilder.jaxbhtml.element;

import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

// TODO: check the text here (and think about some procedure of checking nested elements)
public class SelectTest extends ElementTest {

	@Test
	public void select() {
		assertElement(toDOM(new Select()), "select");
		
		final String[][] OPTIONS_DATA = new String[][] {
				new String[] { "display 1", "value 1", "false", "" },
				new String[] { "display 2", "value 2", "true", "true" },
				new String[] { "display 3", "value 3", "false", "" } };

		Element rootElem = toDOM(createSelect(OPTIONS_DATA));
		assertElement(rootElem, "select");
		Node node = rootElem.getFirstChild();
		int counter = 0;
		while (null != node) {
			if (Node.ELEMENT_NODE == node.getNodeType()) {
				Element elem = Element.class.cast(node);
				assertElementAndText(elem, "option",
					OPTIONS_DATA[counter][0],
					new String[] { "value", OPTIONS_DATA[counter][1] },
					new String[] { "selected", OPTIONS_DATA[counter][3] });
				counter++;
			}
			node = node.getNextSibling();
		}
	}

	private static Select createSelect(String[][] opts) {
		Select result = new Select();
		for (String[] opt : opts) {
			result.add(createOption(opt));
		}
		return result;
	}

	private static Option createOption(String[] str) {
		Option option = new Option(str[0]);
		option.setValue(str[1]);
		option.setSelected(Boolean.valueOf(str[2]));
		return option;
	}

}
