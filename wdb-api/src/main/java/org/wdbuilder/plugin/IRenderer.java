package org.wdbuilder.plugin;

import org.wdbuilder.domain.Entity;

public interface IRenderer<E extends Entity, C> {
	void draw(E entity, C renderCtx);
}
