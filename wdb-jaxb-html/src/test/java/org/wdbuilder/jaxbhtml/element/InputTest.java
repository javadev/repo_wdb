package org.wdbuilder.jaxbhtml.element;

import org.junit.Test;

public class InputTest extends ElementTest {

    @Test
    public void text() {
        Input input = new Input.Text();
        input.setName("textName");
        input.setValue("textValue");
        assertInput(input, "text", "textName", "textValue");
    }

    @Test
    public void hidden() {
        Input input = new Input.Hidden();
        input.setName("hiddenName");
        input.setValue("hiddenValue");
        assertInput(input, "hidden", "hiddenName", "hiddenValue");
    }

    private static void assertInput(Input input, String type, String name,
            String value) {
        assertElement(toDOM(input), "input", new String[] { "type", type },
                new String[] { "name", name }, new String[] { "value", value });
    }

}
