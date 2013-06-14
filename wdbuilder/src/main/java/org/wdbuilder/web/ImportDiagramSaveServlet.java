package org.wdbuilder.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.serialize.html.DiagramImage;
import org.wdbuilder.utility.DiagramHelper;
import org.wdbuilder.web.base.DiagramServiceServlet;
import org.wdbuilder.web.base.ServletInput;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, location = "c:/temp", maxRequestSize = 1024 * 1024 * 50)
@SuppressWarnings("serial")
@WebServlet("/import-diagram-save")
public class ImportDiagramSaveServlet extends DiagramServiceServlet {
	private DiagramHelper diagramHelper = null;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		Part part = input.getRequest().getPart(
				BlockParameter.DiagramKey.getName());
		InputStream fileStream = part.getInputStream();

		ZipInputStream zipStream = new ZipInputStream(fileStream);
		// TODO: for the single entry for a while (2013/06/12)
		zipStream.getNextEntry();

		JAXBContext jaxbContext = JAXBContext
				.newInstance(getClassesForMarshaling());

		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		Object obj = unmarshaller.unmarshal(zipStream);

		zipStream.close();

		if (!Diagram.class.isInstance(obj)) {
			// TODO: more sophisticated data:
			throw new IllegalArgumentException("Invalid file");
		}
		Diagram diagram = Diagram.class.cast(obj);
		serviceFacade.getDiagramService().importDiagram(diagram);
		diagramHelper = new DiagramHelper(diagram);

		// Set the current diagram (TODO silly method):
		input.getState().setDiagram(diagram);

		new DiagramImage(diagramHelper,
				serviceFacade.getBlockPluginRepository())
				.printCanvasFrame(input);
	}

	@Override
	protected String getContentType() {
		return CONTENT_TYPE_XML;
	}

	// TODO: prepare the single functionality with Export (2013/06/12)
	private Class<?>[] getClassesForMarshaling() {
		List<Class<?>> list = new ArrayList<Class<?>>(4);
		list.addAll(serviceFacade.getBlockPluginRepository().getEntityClasses());
		list.addAll(serviceFacade.getLinkPluginRepository().getEntityClasses());
		list.add(Diagram.class);

		Class<?>[] result = new Class<?>[list.size()];
		return list.toArray(result);

	}
}
