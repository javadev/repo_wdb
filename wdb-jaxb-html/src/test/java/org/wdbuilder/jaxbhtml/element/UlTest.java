package org.wdbuilder.jaxbhtml.element;

import org.junit.Test;
import org.w3c.dom.Element;
import org.wdbuilder.jaxbhtml.IHtml;

public class UlTest extends ElementTest {

    @Test
    public void ul() {
        assertElement(toDOM(new Ul()), "ul");
        assertElementAndChildren(toDOM(createUl(new Span())), "ul", 1,
                createCheckerForLI(new IChildElementChecker() {
                    @Override
                    public void assertElement(int index, Element elem) {
                        assertElementAndText(elem, "span", "");
                    }
                }));
        final IHtml[] links = new IHtml[3];
        for (int i = 0; i < links.length; i++) {
            links[i] = createLink("text-" + i, "onClick-" + i);
        }

        assertElementAndChildren(toDOM(createUl(links)), "ul", links.length,
                new IChildElementChecker() {
                    @Override
                    public void assertElement(final int indexLI, Element elem) {
                        createCheckerForLI(createElementAndTextChecker("a",
                                "text-" + indexLI,
                                new String[] { "href", "#" }, new String[] {
                                        "onclick", "onClick-" + indexLI }));
                    }
                });
    }

    private static IChildElementChecker createCheckerForLI(
            final IChildElementChecker nested) {
        return new IChildElementChecker() {
            @Override
            public void assertElement(int index, Element elem) {
                assertElementAndChildren(elem, "li", 1, nested);
            }
        };
    }

    private static Ul createUl(IHtml... children) {
        Ul result = new Ul();
        for (IHtml child : children) {
            Li li = new Li();
            li.add(child);
            result.add(li);
        }
        return result;
    }

    private static A createLink(String text, String onClick) {
        A a = new A();
        a.setText(text);
        a.setOnClick(onClick);
        return a;
    }

}
