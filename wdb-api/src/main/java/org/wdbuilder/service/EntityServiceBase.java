package org.wdbuilder.service;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Entity;

public interface EntityServiceBase<T extends Entity> {
  
	void setPosition(String key, int x, int y);

	void delete(String key);

	void update(String key, T entity);	
	
	String persist(Block block);
}
