package org.wdbuilder.jaxbhtml.element;

import org.junit.Test;
import org.w3c.dom.Element;
import org.wdbuilder.jaxbhtml.element.ElementTest.IChildElementChecker;

public class SimpleHtmlContainerTest extends ElementTest {

    private static final class IChildElementChecker1 implements IChildElementChecker {
        private final IChildElementChecker noBrChecker;
        private final IChildElementChecker firstTextSectionChecker;

        private IChildElementChecker1(IChildElementChecker noBrChecker, IChildElementChecker firstTextSectionChecker) {
            this.noBrChecker = noBrChecker;
            this.firstTextSectionChecker = firstTextSectionChecker;
        }

        @Override
        public void assertElement(int index, Element elem) {
            switch (index) {
            case 0:
                firstTextSectionChecker.assertElement(0, elem);
                break;
            case 1:
                assertElementAndChildren(elem, "nobr", 1,
                        noBrChecker);
                break;
            default:
                break;
            }
        }
    }

    @Test
    public void div() {
        Div div = new Div("the-class");
        assertElement(toDOM(div), "div", new String[] { "class", "the-class" });
        div.setText("sample internal text & < >");

        final IChildElementChecker firstTextSectionChecker = new IChildElementChecker() {
            @Override
            public void assertElement(int index, Element elem) {
                assertElementAndText(elem, "span", "sample internal text & < >");
            }
        };

        final IChildElementChecker noBrChecker = new IChildElementChecker() {
            @Override
            public void assertElement(int index, Element elem) {
                assertElementAndText(elem, "span", "non breakable");
            }
        };

        assertElementAndChildren(toDOM(div), "div", 1, firstTextSectionChecker,
                new String[] { "class", "the-class" });
        NoBr noBr = new NoBr();
        noBr.setText("non breakable");
        div.add(noBr);
        assertElementAndChildren(toDOM(div), "div", 2,
                new IChildElementChecker1(noBrChecker, firstTextSectionChecker), new String[] { "class", "the-class" });
    }

}
