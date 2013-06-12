package org.wdbuilder.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.utility.DiagramHelper;
import org.wdbuilder.web.base.CanvasFrameWriter;
import org.wdbuilder.web.base.DiagramServiceServlet;
import org.wdbuilder.web.base.ServletInput;

@SuppressWarnings("serial")
@WebServlet("/import-diagram-save")
public class ImportDiagramSaveServlet extends DiagramServiceServlet {

	private DiagramHelper diagramHelper = null;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {

		FileItemFactory fileItemFactory = new DiskFileItemFactory();
		/*
		ServletContext servletContext = this.getServletContext();
		File repository = File.class.cast(servletContext
				.getAttribute("javx.servlet.context.tempdir"));
		*/

		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		FileItemIterator it = uploadHandler.getItemIterator(input.getRequest());
		if( !it.hasNext() ) {
			throw new IllegalArgumentException( "No file!" );	
		}
		FileItemStream fileItemStream = it.next();
		InputStream fileStream = fileItemStream.openStream();
		
		ZipInputStream zipStream = new ZipInputStream(fileStream);
		// TODO: for the single entry for a while (2013/06/12)
		zipStream.getNextEntry();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(getClassesForMarshaling());
		
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		
		Object obj = unmarshaller.unmarshal(zipStream);
		
		zipStream.closeEntry();
		zipStream.close();
		
		if( !Diagram.class.isInstance(obj) ) {
			// TODO: more sophisticated data:
			throw new IllegalArgumentException( "Invalid file" );
		}
		Diagram diagram = Diagram.class.cast(obj);
		
		
		
		
		

		// TODO: extract the content from file and unmarshall it from ZIP/XML:

		/*
		 * new CanvasFrameWriter(diagramHelper,
		 * serviceFacade.getBlockPluginRepository()) .printCanvasFrame(input);
		 */
	}

	@Override
	protected String getContentType() {
		return CONTENT_TYPE_XML;
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
