package org.wdbuilder.service;

import java.util.Collection;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Entity;

class StaticDiagramRelatedService {
	protected final Diagram diagram;
	protected final IServiceFacade serviceFacade;

	StaticDiagramRelatedService(Diagram diagram, IServiceFacade serviceFacade) {
		this.diagram = diagram;
		this.serviceFacade = serviceFacade;
	}
	
	protected <T extends Entity> T findByKey( String key, Collection<T> seq ) {
		for( T item : seq ) {
			if( key.equals(item.getKey() ) ) {
				return item;
			}
		}
		return null;
	}
	
	protected <T extends Entity> void removeByKey( String key, Collection<T> seq ) {
		for( T item : seq ) {
			if( key.equals(item.getKey() ) ) {
				seq.remove(item);
				return;
			}
		}
	}	
}
