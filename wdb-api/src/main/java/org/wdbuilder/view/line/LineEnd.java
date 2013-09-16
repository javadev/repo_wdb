package org.wdbuilder.view.line;

import org.wdbuilder.view.ILineEndRendererContext;
import org.wdbuilder.view.ILineRenderer;
import org.wdbuilder.view.line.end.ArrowLineEndRenderer;
import org.wdbuilder.view.line.end.DefaultLineEndRenderer;
import org.wdbuilder.view.line.end.ILineEnd;

public enum LineEnd implements ILineEnd {
  SIMPLE("simple") {
        @Override
        public ILineRenderer<ILineEndRendererContext> getRenderer() {
            return new DefaultLineEndRenderer();
        }       
    },
    SOLID_ARROW("solid arrow") {
        @Override
        public ILineRenderer<ILineEndRendererContext> getRenderer() {
            return new ArrowLineEndRenderer.Solid();
        }       
    },  
    TRANSPARENT_ARROW("transparent arrow") {
        @Override
        public ILineRenderer<ILineEndRendererContext> getRenderer() {
            return new ArrowLineEndRenderer.Transparent();
        }       
    };
    private final String displayName;
    
    private LineEnd(String displayName) {
        this.displayName = displayName;
    }
    @Override
    public String getDisplayName() {
        return displayName;
    }
}
