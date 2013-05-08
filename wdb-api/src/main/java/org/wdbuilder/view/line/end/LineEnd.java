package org.wdbuilder.view.line.end;

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
			return new ArrowLineEndRenderer.Solid();
		}		
	}, 	
	TRANSPARENT_ARROW( "transparent arrow" ) {
		@Override
		public ILineEndRenderer getRenderer() {
			return new ArrowLineEndRenderer.Transparent();
		}		
	}, 	
	MANDATORY_1_1( "mandatory 1..1" ) {
		@Override
		public ILineEndRenderer getRenderer() {
			return new Mandatory1to1LineEndRenderer();
		}		
	},
	MANDATORY_1_N( "mandatory 1..n" ) {
		@Override
		public ILineEndRenderer getRenderer() {
			return new Mandatory1toNLineEndRenderer();
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
