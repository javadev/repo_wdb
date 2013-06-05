package org.wdbuilder.jaxbhtml;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.wdbuilder.jaxbhtml.element.*;

public class HtmlWriter {

	private final PrintWriter writer;
	private final JAXBContext jaxbContext;

	public HtmlWriter(PrintWriter writer) throws JAXBException {
		this.writer = writer;
		this.jaxbContext = JAXBContext.newInstance(Img.class, Area.class,
				Map.class, Td.class, Tr.class, Table.class, Div.class,
				Ul.class, Ol.class, Li.class, Span.class, A.class, Input.class,
				Form.class, Select.class, Option.class, NoBr.class, I.class,
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
