package org.wdbuilder.domain;

import java.awt.Color;


public enum DiagramBackground implements ISimpleBackgroundProvider {
    White(0xffffff, "White"), Sand(0xffffd8, "Sand"), Green(0xa8ffa8, "Green"), Azure(0xccf0ff, "Azure"), LightGrey(
            0xf0f0f0, "Light Grey"), Lemon(0xffff66, "Lemon"), Aqua(0x33ffff, "Aqua");
    private final Color color;
    private final String displayName;

    private DiagramBackground(int color, String displayName) {
        this.color = new Color(color);
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Color getPrimaryBackgroundColor() {
        return color;
    }
}
