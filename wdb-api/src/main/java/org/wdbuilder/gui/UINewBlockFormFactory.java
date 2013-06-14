package org.wdbuilder.gui;

public abstract class UINewBlockFormFactory extends UIEntityFormFactory {
	
	protected final Class<?> blockClass; 

	public UINewBlockFormFactory(String diagramKey, Class<?> blockClass) {
		super(diagramKey);
		this.blockClass = blockClass;
	}
	

	@Override
	public String getSubmitCall() {
		return "submitCreateBlock()";
	}	
}
