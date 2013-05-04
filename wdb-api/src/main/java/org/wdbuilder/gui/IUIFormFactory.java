package org.wdbuilder.gui;

import org.wdbuilder.domain.Block;

public interface IUIFormFactory {

	public TwoColumnForm getCreateHTML(String diagramKey);

	public TwoColumnForm getEditHTML(String diagramKey, Block block);

	public TwoColumnForm getViewHTML(String diagramKey, Block block);
	
	public String getEditSubmitCall(String diagramKey, String blockKey );
	
	public String getCreateSubmitCall(String diagramKey);
	
	public String getCreateBlockTitle();
	
	public String getEditBlockTitle();
}
