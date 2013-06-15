package org.wdbuilder.service;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.utility.DiagramHelper;

class StaticDiagramRelatedService {
	protected final DiagramHelper diagramHelper;
	protected final IServiceFacade serviceFacade;

	StaticDiagramRelatedService(Diagram diagram, IServiceFacade serviceFacade) {
		this.diagramHelper = new DiagramHelper(diagram);
		this.serviceFacade = serviceFacade;
	}
}
