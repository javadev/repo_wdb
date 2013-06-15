package org.wdbuilder.service;

import org.wdbuilder.domain.Block;

public interface BlockService {
	
	void setLocation(String blockKey, int offsetX, int offsetY);

	void delete(String blockKey);

	String persist(Block block);

	void update(String blockKey, Block block);	
}
