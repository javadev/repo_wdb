package org.wdbuilder.web;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.web.base.DiagramHelperServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/exported/*")
public class ExportDiagramServlet extends DiagramHelperServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4DiagramHelper(ServletInput input) throws Exception {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(getClassesForMarshaling());
		Marshaller marshaller = jaxbContext.createMarshaller();

		ZipOutputStream zipStream = new ZipOutputStream(input.getResponse().getOutputStream());
		zipStream.putNextEntry(new ZipEntry("diagram.xml"));

		marshaller.marshal(getDiagram(input), zipStream);

		zipStream.closeEntry();

		zipStream.close();
	}

	@Override
	protected String getContentType() {
		return "application/zip";
	}
	
	private Class<?>[] getClassesForMarshaling() {
		List<Class<?>> list = new ArrayList<Class<?>>(4);
		list.addAll( serviceFacade.getBlockPluginRepository().getEntityClasses() );
		list.addAll( serviceFacade.getLinkPluginRepository().getEntityClasses() );		
		list.add(Diagram.class);
		
		Class<?>[] result = new Class<?>[list.size()];
		return list.toArray(result);
		
	}

}
