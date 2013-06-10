package org.wdbuilder.jaxbhtml;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.Area;
import org.wdbuilder.jaxbhtml.element.B;
import org.wdbuilder.jaxbhtml.element.Button;
import org.wdbuilder.jaxbhtml.element.Div;
import org.wdbuilder.jaxbhtml.element.Form;
import org.wdbuilder.jaxbhtml.element.I;
import org.wdbuilder.jaxbhtml.element.Img;
import org.wdbuilder.jaxbhtml.element.Input;
import org.wdbuilder.jaxbhtml.element.Label;
import org.wdbuilder.jaxbhtml.element.Li;
import org.wdbuilder.jaxbhtml.element.Map;
import org.wdbuilder.jaxbhtml.element.NoBr;
import org.wdbuilder.jaxbhtml.element.Ol;
import org.wdbuilder.jaxbhtml.element.Span;
import org.wdbuilder.jaxbhtml.element.Table;
import org.wdbuilder.jaxbhtml.element.Td;
import org.wdbuilder.jaxbhtml.element.Tr;
import org.wdbuilder.jaxbhtml.element.Ul;

public class HtmlWriter {

	private final PrintWriter writer;
	private final JAXBContext jaxbContext;

	public HtmlWriter(PrintWriter writer) throws JAXBException {
		this.writer = writer;
		this.jaxbContext = JAXBContext.newInstance(Img.class, Area.class,
				Map.class, Td.class, Tr.class, Table.class, Div.class,
				Ul.class, Ol.class, Li.class, Span.class, A.class, Input.class,
				Form.class, NoBr.class, I.class,
				B.class, Label.class,
				Button.class);
	}

	public void write(IHtml obj) throws JAXBException, IOException {
		final StringWriter stringWriter = new StringWriter(2048);
		this.jaxbContext.createMarshaller().marshal(obj, stringWriter);
		String outStr = stringWriter.toString();
		stringWriter.close();

		// Remove XML-related prefix from string:
		outStr = outStr.substring(55);

		this.writer.print(outStr);
	}
}
