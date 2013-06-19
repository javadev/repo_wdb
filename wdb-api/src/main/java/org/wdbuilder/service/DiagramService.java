package org.wdbuilder.service;

import java.util.Collection;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.helper.Dimension;

public interface DiagramService {

	public static final int LINE_OFFSET = 10;

	public static final Dimension LINE_AREA = new Dimension(6, 6);

	public static final Dimension RESIZE_AREA = new Dimension(11, 11);

	Collection<Diagram> retrieveList();

	Diagram get(String diagramKey);

	void setSize(String diagramKey, int width, int height);

	String persist(String name, String backgroundKey);
	
	void upload( Diagram diagram );

	void update(String diagramKey, String name, String backgroundKey);

	void delete(String diagramKey);
	
	EntityServiceBase<Block> getBlockService( String diagramKey );
	
	EntityServiceBase<Link> getLinkService( String diagramKey );

}
