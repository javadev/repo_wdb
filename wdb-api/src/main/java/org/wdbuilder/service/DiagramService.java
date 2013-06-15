package org.wdbuilder.service;

import java.util.Collection;

import org.wdbuilder.domain.Diagram;
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
	
	BlockService getBlockService( String diagramKey );
	
	LinkService getLinkService( String diagramKey );
	
	/*

	// Block related:
	void updateBlockPosition(String diagramKey, String blockKey, int offsetX,
			int offsetY);

	void deleteBlock(String diagramKey, String blockKey);

	String persistBlock(String diagramKey, Block block);

	void updateBlock(String diagramKey, String blockKey, Block block);

	// Link related:
	void persistLink(String diagramKey, String beginBlockKey,
			String beginSocketDirection, int beginSocketIndex,
			String endBlockKey, String endSocketDirection, int endSocketIndex);

	void moveLinkPivot(String diagramKey, String linkKey, int x, int y);

	void updateLink(String diagramKey, String linkKey, Link link);

	void deleteLink(String diagramKey, String linkKey);
	
	*/

}
