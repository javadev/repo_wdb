package org.wdbuilder.jaxbhtml;

import org.junit.Test;
import org.wdbuilder.jaxbhtml.HtmlContainer;
import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.Div;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HtmlContainerTest {

	@Test
	public void contextSize() {
		final HtmlContainer obj = new HtmlContainer();
		assertTrue(obj.getContent().isEmpty());
		obj.add(new Div());
		assertEquals(1, obj.getContent().size());
		obj.add(new Div());
		assertEquals(2, obj.getContent().size());
		obj.add(new A());
		assertEquals(3, obj.getContent().size());
	}

}
