package org.wdbuilder.plugin;

import org.wdbuilder.domain.SizedEntity;

public interface IRenderer {
	void draw(SizedEntity entity, IRenderContext renderCtx);
}
