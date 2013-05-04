package org.wdbuilder.gui;

import org.wdbuilder.domain.Block;

public abstract class UIExistingBlockFormFactory extends UIBlockFormFactory {
	
	protected final Block block; 

	public UIExistingBlockFormFactory(String diagramKey, Block block) {
		super(diagramKey);
		this.block = block;
	}
}
