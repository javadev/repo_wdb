package org.wdbuilder.plugin.defaultlink.line.end;

import org.wdbuilder.view.ILineEndRenderer;
import org.wdbuilder.view.line.end.ILineEnd;

public enum LineEnd implements ILineEnd {
  SIMPLE( "simple" ) {
		@Override
		public ILineEndRenderer getRenderer() {
			return new DefaultLineEndRenderer();
		}		
	},
	SOLID_ARROW( "solid arrow" ) {
		@Override
		public ILineEndRenderer getRenderer() {
			return new ArrowLineEndRenderer();
		}		
	}, 	
	;
	private final String displayName;
	
	private LineEnd( String displayName ) {
		this.displayName = displayName;
	}
	@Override
	public String getDisplayName() {
		return displayName;
	}
}