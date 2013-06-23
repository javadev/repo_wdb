package org.wdbuilder.service;

import java.util.Collection;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Entity;

abstract class StaticDiagramRelatedService<T extends Entity> {
	protected final Diagram diagram;
	protected final IServiceFacade serviceFacade;

	protected abstract void validate(T entity);

	StaticDiagramRelatedService(Diagram diagram, IServiceFacade serviceFacade) {
		this.diagram = diagram;
		this.serviceFacade = serviceFacade;
	}

	protected void removeByKey(String key, Collection<T> seq) {
		for (T item : seq) {
			if (key.equals(item.getKey())) {
				seq.remove(item);
				return;
			}
		}
	}
}
