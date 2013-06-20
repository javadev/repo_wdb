package org.wdbuilder.service;

import org.wdbuilder.domain.Diagram;

class StaticDiagramRelatedService {
  protected final DiagramHelper diagramHelper;
	protected final IServiceFacade serviceFacade;

	StaticDiagramRelatedService(Diagram diagram, IServiceFacade serviceFacade) {
		this.diagramHelper = new DiagramHelper(diagram);
		this.serviceFacade = serviceFacade;
	}
}
