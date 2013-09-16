package org.wdbuilder.jaxbhtml.element;

import java.awt.Dimension;
import java.awt.Point;
import org.junit.Test;

public class AreaTest extends ElementTest {

    @Test
    public void circle() {
        Area area = new Area.Circle(new Point(70, 145), 14);
        area.setTitle("the title!");
        area.setOnMouseDown("on mouse down");

        assertElement(toDOM(area), "area", 
            new String[] { "shape", "circle" },
            new String[] { "href", "#" }, 
            new String[] { "coords", "70,145,14" },
            new String[] { "alt", "the title!" }, 
            new String[] { "title", "the title!" },
            new String[] { "onmousedown", "on mouse down" }
        );
    }

    
    @Test
    public void rect() {
        Area area = new Area.Rect(new Point(50, 10), new Dimension(112, 234));
        area.setTitle("the title-2");
        area.setOnClick("on click");
        
        assertElement(toDOM(area), "area", 
                new String[] { "shape", "rect" },
                new String[] { "coords", "50,10,162,244" },
                new String[] { "onclick", "on click" }, 
                new String[] { "alt", "the title-2" }, 
                new String[] { "title", "the title-2" }
        );
    }
}
