package org.wdbuilder.jaxbhtml.element;

import java.awt.Dimension;
import org.junit.Test;

public class ImgTest extends ElementTest {

    @Test
    public void noMap() {

        assertElement(toDOM(createImg("image.jpg", "the title 1", new Dimension(34, 15))),
            "img",
            new String[] { "src", "image.jpg" },
            new String[] { "width", "34" },
            new String[] { "height", "15" },
            new String[] { "border", "0" },
            new String[] { "title", "the title 1" },
            new String[] { "alt", "the title 1" }
        );
    }

    @Test
    public void useMap() {
        Img img = createImg("-1.png", "the title 2", new Dimension(450, 700));
        img.setUseMap("sampleMap");
        assertElement(toDOM(img), 
                "img",
                new String[] { "src", "-1.png" },
                new String[] { "width", "450" },
                new String[] { "height", "700" },
                new String[] { "border", "0" },
                new String[] { "title", "the title 2" },
                new String[] { "alt", "the title 2" },
                new String[] { "usemap", "sampleMap" }
            );
    }   

    private static Img createImg(String src, String title, Dimension size) {
        Img result = new Img();
        result.setTitle(title);
        result.setSrc(src);
        result.setSize(size);
        return result;
    }
}
