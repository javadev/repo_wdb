package org.wdbuilder.plugin;

import org.wdbuilder.domain.DiagramEntity;

public interface IRenderer {
	void draw(DiagramEntity entity, IRenderContext renderCtx);
}
