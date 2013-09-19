package org.wdbuilder.jaxbhtml.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.wdbuilder.jaxbhtml.HtmlWriter;
import org.wdbuilder.jaxbhtml.IHtml;
import org.xml.sax.InputSource;

public class ElementTest {

    @Test
    public void probeToDOM() {
        assertElement(toDOM(new Div()), "div");
    }

    protected static Element toDOM(IHtml htmlObj) {
        try {
            StringWriter out = new StringWriter(1024);
            HtmlWriter writer = new HtmlWriter(new PrintWriter(out));
            writer.write(htmlObj);
            out.close();

            InputSource source = new InputSource(new StringReader(
                    out.toString()));

            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = docBuilder.parse(source);
            return doc.getDocumentElement();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected static void assertElement(Element elem, String expTag,
            String[]... expAttrs) {
        assertNotNull(elem);
        assertEquals(expTag, elem.getTagName());
        for (String[] expAttr : expAttrs) {
            String actualValue = elem.getAttribute(expAttr[0]);
            assertEquals(expAttr[1], actualValue);
        }
    }

    protected static void assertElementAndText(Element elem, String expTag,
            String expText, String[]... expAttrs) {
        assertElement(elem, expTag, expAttrs);
        assertEquals(expText, getText(elem));
    }

    protected static void assertElementAndChildren(Element root, String expTag,
            int expectedChildrenCount, IChildElementChecker childChecker,
            String[]... expAttrs) {
        assertElement(root, expTag, expAttrs);
        int counter = 0;
        Node node = root.getFirstChild();
        while (null != node) {
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element elem = Element.class.cast(node);
                childChecker.assertElement(counter, elem);
                counter++;
            }
            node = node.getNextSibling();
        }
        assertEquals(expectedChildrenCount, counter);
    }

    private static final class CreateIChildElementChecker implements IChildElementChecker {
      private final String expTag;
      private final String expText;
      private final String[][] expAttrs;

      private CreateIChildElementChecker(String expTag, String expText, String[][] expAttrs) {
        this.expTag = expTag;
        this.expText = expText;
        this.expAttrs = expAttrs;
      }

      @Override
      public void assertElement(int index, Element elem) {
          assertElementAndChildren(elem, expTag, 1,
                  new IChildElementChecker() {
                      @Override
                      public void assertElement(int index, Element elem) {
                          assertElementAndText(elem, "span", expText);
                      }
                  }, expAttrs);
      }
    }

    protected interface IChildElementChecker {
        void assertElement(int index, Element elem);
    }

    protected static IChildElementChecker createElementAndTextChecker(
            final String expTag, final String expText,
            final String[]... expAttrs) {
        return new CreateIChildElementChecker(expTag, expText, expAttrs);
    }

    private static String getText(Element elem) {
        Node node = elem.getFirstChild();
        while (null != node) {
            if (Node.TEXT_NODE == node.getNodeType()) {
                Text text = Text.class.cast(node);
                return text.getTextContent();
            }
            node = node.getNextSibling();
        }
        return "";
    }

}
