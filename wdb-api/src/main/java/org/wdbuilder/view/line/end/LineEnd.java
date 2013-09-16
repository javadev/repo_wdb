package org.wdbuilder.view.line.end;

import org.wdbuilder.view.ILineEnd;
import org.wdbuilder.view.ILineEndRendererContext;
import org.wdbuilder.view.ILineRenderer;

public enum LineEnd implements ILineEnd<ILineEndRendererContext> {
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
    },
    MANDATORY_1_1("mandatory 1..1") {
        @Override
        public ILineRenderer<ILineEndRendererContext> getRenderer() {
            return new MandatoryLineEndRenderer.One();
        }
    },
    MANDATORY_1_N("mandatory 1..n") {
        @Override
        public ILineRenderer<ILineEndRendererContext> getRenderer() {
            return new MandatoryLineEndRenderer.Many();
        }
    },
    OPTIONAL_1_1("optional 1..1") {
        @Override
        public ILineRenderer<ILineEndRendererContext> getRenderer() {
            return new OptionalLineEndRenderer.One();
        }
    },
    OPTIONAL_1_N("optional 1..n") {
        @Override
        public ILineRenderer<ILineEndRendererContext> getRenderer() {
            return new OptionalLineEndRenderer.Many();
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
