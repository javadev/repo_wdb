package org.wdbuilder.jaxbhtml.element;

import org.junit.Test;

public class ATest extends ElementTest {

	@Test
	public void href() {
		A a = createA();
		a.setHref("test-href");
		assertElement(toDOM(a), "a", 
			new String[] {"onmouseover", "test-onmouseover"},
			new String[] {"onmouseout", "test-onmouseout"},
			new String[] {"id", "test-id"},
			new String[] {"class", "test-class"}
		);
	}
	
	@Test
	public void onClick() {
		A a = createA();
		a.setOnClick("test-onclick");
		assertElement(toDOM(a), "a", 
			new String[] {"onmouseover", "test-onmouseover"},
			new String[] {"onmouseout", "test-onmouseout"},
			new String[] {"id", "test-id"},
			new String[] {"class", "test-class"},
			new String[] {"onclick", "test-onclick"}
		);
	}

	private static A createA() {
		A a = new A("test-class");
		a.setId("test-id");
		a.setOnClick("test-onclick");
		a.setOnMouseOut("test-onmouseout");
		a.setOnMouseOver("test-onmouseover");
		return a;
	}

}
