package org.wdbuilder.service;

import org.wdbuilder.domain.Link;

public interface LinkService {
	void persist(String beginBlockKey, String beginSocketDirection,
			int beginSocketIndex, String endBlockKey,
			String endSocketDirection, int endSocketIndex);

	void setPivot(String linkKey, int x, int y);

	void update(String linkKey, Link link);

	void delete(String linkKey);
}
